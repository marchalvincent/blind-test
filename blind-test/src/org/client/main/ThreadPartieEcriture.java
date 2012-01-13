package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.client.ui.AccueilPanel;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;
import org.client.ui.display.EnumDisplayImage;
import org.client.ui.listeners.QuitterPartieListener;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.AnswerMessage;
import org.commons.message.DisconnectMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EndGameMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.PlayMessage;
import org.commons.util.IWithSupport;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;
import org.server.partie.Score;

public class ThreadPartieEcriture implements Runnable, Observer {

	private String login;
	private JouerPanel fenetre;
	private String currentImage;
	private String answer;
	private Boolean isClicked;
	private Socket socket;
	private ArrayBlockingQueue<IMessage> currentMessages;
	private String _nomPartie = "";
	private int nbImg;
	
	public ThreadPartieEcriture (Socket socket, JouerPanel fenetre, String login, String nomPartie) {
		super();
		this.login = login;
		this.fenetre = fenetre;
		this.socket = socket;
		this.currentMessages = new ArrayBlockingQueue<IMessage>(20);
		isClicked = Boolean.FALSE;
		_nomPartie = nomPartie;
		DisconnectMessage disconnectM = (DisconnectMessage) EnumMessage.DISCONNECT.createMessage();
		disconnectM.setLogin(login);
		disconnectM.setPartie(nomPartie);
		fenetre.getBoutonQuitter().addMouseListener(new QuitterPartieListener(login, socket, disconnectM, fenetre.getBoutonQuitter()));
	}
	
	public void setNbImg (int nbImg) {
		this.nbImg = nbImg;
	}

	public final void addIMessage(final IMessage parMessage) {
		currentMessages.add(parMessage);
	}

	public final void setAnswer(String answer) {
		this.answer = answer;
	}

	public final String getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(String currentImage) {
		this.currentImage = currentImage;
	}

	public void setIsClicked(Boolean isClicked) {
		this.isClicked = isClicked;
	}

	@Override
	public void run() {
		Configuration config = ConfigurationManager.getConfiguration();
		InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		String name = null;
		StringBuilder _stringBuilder = new StringBuilder();

		PlayMessage play = (PlayMessage) EnumMessage.PLAY.createMessage();
		play.setLogin(this.login);
		play.setNomPartie(_nomPartie);
		if (nbImg == 0) {
			nbImg = 10;
		}
		play.setSize(nbImg);
		try {
			ReadWriterUtil.write(socket, play);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		final Thread locThreadLecture = new Thread(new ThreadPartieLecture(socket, this));
		locThreadLecture.start();
		end : while (true) {
			while(!currentMessages.isEmpty()) {
				this.setIsClicked(Boolean.FALSE);
				IMessage messageRetour = currentMessages.poll();

				EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
				//on affiche dans la console du client
				if(messageRetour instanceof IWithSupport) {
					IWithSupport locSupport = (IWithSupport) messageRetour;
					fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
					if(EnumMessage.isWinner(mess)) {
						break;
					}
				}

				if (EnumMessage.isEndGame(mess)) {
					//Si c'est la fin de partie on quitte les deux boucles pour revenir a la page précédente
					final Map<String, Integer> locMap = ((EndGameMessage) messageRetour).toMap();
					final List<Score> locScoreList = Score.convert(locMap);
					final StringBuilder locBuilder = new StringBuilder();
					locBuilder.append("Classement : \n");
					int locCpt = 1;
					for(final Score locScore : locScoreList) {
						locBuilder.append("  - ").append(locCpt).append(". ").append(locScore).append("\n");
						++locCpt;
					}
					JOptionPane.showMessageDialog(null, locBuilder.toString(), "Classement", JOptionPane.INFORMATION_MESSAGE);
					break end;
				}
				else if (EnumMessage.isDisplay(mess)) {
					final DisplayMessage locDisplayMessage = (DisplayMessage) messageRetour;
					name = locDisplayMessage.getFileName();
					if (!StringUtil.equals(name, currentImage)) {
						currentImage = name;
						if(_stringBuilder.length() != 0) {
							_stringBuilder.delete(0, _stringBuilder.length());
						}
						_stringBuilder.append(config.getImageDirectory());
						_stringBuilder.append(name);
						final String fileName = _stringBuilder.toString();
						final EnumDisplayImage locDisplay = WithUtilities.getById(EnumDisplayImage.values(), locDisplayMessage.getType());
						fenetre.newTest(fileProvider, fileName, locDisplay);
					}
				}

				while(Boolean.FALSE == isClicked) {
					//Si le client a cliqué on construit le AnswerMessage
					if (false == currentMessages.isEmpty()) {
						break;
					}
				}
				if (isClicked.booleanValue()) {
					//ON construit le AnswerMessage
					AnswerMessage answerMessage = (AnswerMessage) EnumMessage.ANSWER.createMessage();
					answerMessage.setLogin(this.login);
					answerMessage.setAnswer(this.answer);
					try {
						ReadWriterUtil.write(this.socket, answerMessage);
					} catch (IOException e) {
						fileProvider.appendMessage(Level.SEVERE, "Impossible de lire dans la socket");
					}
				}
			}
		}
		
		//Si on est sortie des deux boucles on revient à la page d'accueil
		Fenetre.instance().changePage(new AccueilPanel(login).initPanel());
		SystemUtil.close(socket);
	}

	@Override
	public void update(final Observable parObservable, final Object parObject) {
		this.setAnswer(parObject.toString());
		this.setIsClicked(Boolean.TRUE);
	}
}
