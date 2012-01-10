package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.client.ui.AccueilPanel;
import org.client.ui.Fenetre;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.AnswerMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.PlayMessage;
import org.commons.util.IWithSupport;
import org.commons.util.SystemUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;

public class ThreadPartieEcriture implements Runnable {

	private String login = null;
	private Fenetre fenetre = null;
	private Boolean connected = true;
	
	public ThreadPartieEcriture(Fenetre fenetre, String login) {
		super();
		this.login = login;
		this.fenetre = fenetre;
	}
	
	@Override
	public void run() {
		//on construit le message
		PlayMessage message = (PlayMessage) EnumMessage.PLAY.createMessage();
		message.setLogin(login);
		
		Configuration config = ConfigurationManager.getConfiguration();
		final InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		Socket locSocket = null;
		
		// on envoie le message pour demander a jouer
		try {
			locSocket = new Socket(config.getHostName(), config.getPort());
			ReadWriterUtil.write(locSocket, message);
		}
		catch (IOException locException) {
			fileProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", config.getHostName(), config.getPort()), locException);
			SystemUtil.close(locSocket);
			connected = false;
		}
		
		IMessage messageRetour = null;
		
		//la boucle du jeu, infinie tant que le user n'a pas quitté
		while(connected) {
			// on écoute la réponse
			try {
				messageRetour = ReadWriterUtil.read(locSocket);
			} catch (ClassNotFoundException e) {
				SystemUtil.close(locSocket);
				connected = false;
			} catch (IOException e) {
				fileProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", config.getHostName(), config.getPort()), e);
				SystemUtil.close(locSocket);
				connected = false;
			}
			
			//On affiche un message dans la console du client
			if (messageRetour instanceof IWithSupport) {
				IWithSupport locSupport = (IWithSupport) messageRetour;
				fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
			}
			
			//On teste le type du message de retour
			final EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
			if (EnumMessage.isError(mess)) {
				SystemUtil.close(locSocket);
				connected = false;
			}
			else if (EnumMessage.isDisplay(mess)) {
				DisplayMessage answer = (DisplayMessage) messageRetour;
				StringBuilder name = new StringBuilder();
				name.append(config.getImageDirectory());
				name.append(answer.getFileName());
				String fileName = name.toString();
				//TODO faire le traitement qui affiche l'image sur l'UI
				
				//read
				
				
				//send answerMessage
				
			}
		}
		
		fenetre.changePage(new AccueilPanel());
		
	}

	public Boolean getConnected() {
		return connected;
	}

	public void setConnected(Boolean connected) {
		this.connected = connected;
	}
	
	

}
