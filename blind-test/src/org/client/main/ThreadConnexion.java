package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.ConnexionMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.util.IWithSupport;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;


public final class ThreadConnexion implements Runnable {

	private final String _login;
	private final String _password;
	private boolean connect = false;
	
	public ThreadConnexion(final String parLogin, final String parPassword) {
		_login = parLogin;
		_password = parPassword;
	}

	@Override
	public final void run() {
		//on créé le message
		ConnexionMessage message = (ConnexionMessage) EnumMessage.CONNEXION.creatMessage();
		message.setLogin(_login);
		message.setPassword(_password);
		
		Configuration config = ConfigurationManager.getConfiguration();
		final InfoProvider fileProvider = InfoProviderManager.getFileProvider();
		try {
			//on envoie le message
			Socket socket = new Socket(config.getHostName(), config.getPort());
			ReadWriterUtil.write(socket, message);
			
			//on écoute la réponse
			IMessage messageRetour = ReadWriterUtil.read(socket);
			
			if(messageRetour instanceof IWithSupport) {
				IWithSupport locSupport = (IWithSupport) messageRetour;
				fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
			}
			EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
			if (!EnumMessage.isInfo(mess))
				connect = true;
			
			if (connect) {
				//TODO ajouter le Thread
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
