package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProviderManager;
import org.commons.message.EnumMessage;
import org.commons.message.InscriptionDefaultMessage;
import org.server.concurrent.ReadWriterUtil;


public final class Inscription implements Callable {

	private final String login;
	private final String password;
	private final String nom;
	
	public Inscription (final String parLogin, final String parPassword, final String parNom) {
		login = parLogin;
		password = parPassword;
		nom = parNom;
	}

	@Override
	public final Object call() {
		InscriptionDefaultMessage inscription = (InscriptionDefaultMessage) EnumMessage.INSCRIPTION.creatMessage();
		inscription.setLogin(login);
		inscription.setNom(nom);
		inscription.setPassword(password);
		Configuration info = ConfigurationManager.getConfiguration();
		try {
			Socket socket = new Socket (info.getHostName(),info.getPort());
			ReadWriterUtil.write(socket, inscription) ;
		} catch (ClassNotFoundException e) {
			InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Inscription - class not found");
			return false;
		} catch (IOException e) {
			InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Inscription - server not found");
			return false;
		}
		return true; 
	}
}
