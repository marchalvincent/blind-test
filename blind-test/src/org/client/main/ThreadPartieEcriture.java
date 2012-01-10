package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;

import org.client.ui.JouerPanel;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.AnswerMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.util.IWithSupport;
import org.commons.util.StringUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;

public class ThreadPartieEcriture implements Runnable {

	private String login = null;
	private JouerPanel fenetre = null;
	private String currentImage = null;
	private String answer = null;
	private Boolean isClicked = false;
	private Socket socket = null;
	private ArrayBlockingQueue<IMessage> _currentMessages;
	

	public ThreadPartieEcriture(Socket socket, JouerPanel fenetre, String login) {
		super();
		this.login = login;
		this.fenetre = fenetre;
		this.socket = socket;
		_currentMessages = new ArrayBlockingQueue<IMessage>(20);
	}
	
	public final void add(final IMessage parMessage) {
		_currentMessages.add(parMessage);
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
		StringBuilder sb = new StringBuilder();
		
		while(!_currentMessages.isEmpty()) {
			IMessage messageRetour = _currentMessages.poll();

			//on affiche dans la console du client
			if(messageRetour instanceof IWithSupport) {
				IWithSupport locSupport = (IWithSupport) messageRetour;
				fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
			}
			
			EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
			if (EnumMessage.isDisplay(mess)) {
				DisplayMessage dm = (DisplayMessage) messageRetour;
				name = dm.getFileName();
				
				if (!StringUtil.equals(name, currentImage)) {
					currentImage = name;
					sb.delete(0, (sb.length() - 1));
					sb.append(config.getImageDirectory());
					sb.append(name);
					String fileName = sb.toString();
					//fenetre.newTest(fileName);
				}
			}
			
			while(!isClicked) {
				//Si le client a cliqué on construit le AnswerMessage
				if (false == _currentMessages.isEmpty()) {
					break;
				}
			}
			
			if (isClicked) {
				//ON construit le AnswerMessage
				AnswerMessage answerMessage = (AnswerMessage) EnumMessage.ANSWER.createMessage();
				answerMessage.setLogin(this.login);
				answerMessage.setAnswer(this.answer);
				try {
					ReadWriterUtil.write(this.socket, answerMessage);
				} catch (IOException e) {
					fileProvider.appendMessage(Level.SEVERE, "Inscription - erreur de connexion au serveur");
				}
				
			}
		}
		
	}

}
