package org.client.main;

import java.io.IOException;
import java.net.Socket;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.message.ConnexionMessage;
import org.commons.message.EnumMessage;


public class Connexion implements Runnable {

	final private String _login;
	final private String _password;
	private transient Socket _socket;
	
	public Connexion(final String parHost, final String parPassword) {
		_login = parHost;
		_password = parPassword;
	}
	
	final public Socket getSocket() {
		return _socket;
	}

	@Override
	public final void run() {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		try {
			_socket = new Socket(locConfiguration.getHostName(), locConfiguration.getPort().intValue());
			final ConnexionMessage locMessage = (ConnexionMessage) EnumMessage.CONNEXION.creatMessage();
			locMessage.setLogin(_login);
			locMessage.setPassword(_password);
			//TODO : Ecrire dans la socket
			
		} catch (IOException e) {
		}
		
	}
}
