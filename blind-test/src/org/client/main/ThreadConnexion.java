package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.downloader.ClientDownloader;
import org.commons.downloader.Downloader;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.ConnexionMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.util.IWithSupport;
import org.commons.util.SystemUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;

public final class ThreadConnexion implements Runnable {

	private final String _login;
	private final String _password;

	public ThreadConnexion(final String parLogin, final String parPassword) {
		_login = parLogin;
		_password = parPassword;
	}

	@Override
	public final void run() {
		// on créé le message
		ConnexionMessage message = (ConnexionMessage) EnumMessage.CONNEXION
				.createMessage();
		message.setLogin(_login);
		message.setPassword(_password);

		Configuration config = ConfigurationManager.getConfiguration();
		final InfoProvider fileProvider = InfoProviderManager.getFileProvider();
		Socket locSocket = null;
		try {
			// on envoie le message
			locSocket = new Socket(config.getHostName(), config.getPort());
			ReadWriterUtil.write(locSocket, message);
		} catch (IOException locException) {
			fileProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", config.getHostName(), config.getPort()), locException);
			SystemUtil.close(locSocket);
			return;
		}
		// on écoute la réponse
		IMessage messageRetour = null;
		try {
			messageRetour = ReadWriterUtil.read(locSocket);
		} catch (ClassNotFoundException e) {
			SystemUtil.close(locSocket);
		} catch (IOException e) {
			fileProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", config.getHostName(), config.getPort()), e);
			SystemUtil.close(locSocket);
			return;
		}
		
		if (messageRetour instanceof IWithSupport) {
			IWithSupport locSupport = (IWithSupport) messageRetour;
			fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
		}
		final EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
		if (EnumMessage.isError(mess)) {
			// Va falloir qu'il se reconnecte. Le thread se fait kill, on en
			// recréera pour une nouvelle connexion.
			SystemUtil.close(locSocket);
			return;
		}
		final Downloader locDownloader = new ClientDownloader(locSocket, fileProvider);
		locDownloader.download();
	}
}
