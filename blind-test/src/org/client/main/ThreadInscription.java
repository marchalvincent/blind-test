package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.InscriptionDefaultMessage;
import org.commons.util.IWithSupport;
import org.commons.util.SystemUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;


public final class ThreadInscription implements Callable<Boolean> {

	private final String login;
	private final String password;
	private final String nom;
	
	public ThreadInscription (final String parLogin, final String parPassword, final String parNom) {
		login = parLogin;
		password = parPassword;
		nom = parNom;
	}

	@Override
	public final Boolean call() {
		//on construit l'objet message
		InscriptionDefaultMessage inscription = (InscriptionDefaultMessage) EnumMessage.INSCRIPTION.creatMessage();
		inscription.setLogin(login);
		inscription.setNom(nom);
		inscription.setPassword(password);
		
		Configuration config = ConfigurationManager.getConfiguration();
		final InfoProvider fileProvider = InfoProviderManager.getFileProvider();
		try {
			//on envoie l'objet avec la socket
			Socket socket = new Socket (config.getHostName(), config.getPort());
			ReadWriterUtil.write(socket, inscription);
			
			//on écoute la réponse
			IMessage messageRetour = ReadWriterUtil.read(socket);
			//on ferme la socket
			SystemUtil.close(socket);
			
			if(messageRetour instanceof IWithSupport) {
				IWithSupport locSupport = (IWithSupport) messageRetour;
				fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
			}
			
			EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
			return EnumMessage.isError(mess);
			
		} catch (ClassNotFoundException e) {
			fileProvider.appendMessage(Level.SEVERE, "Inscription - class not found");
		} catch (IOException e) {
			fileProvider.appendMessage(Level.SEVERE, "Inscription - socket error");
		}
		return false;
	}
}
