package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Callable;
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

public final class ThreadConnexion implements Callable <Boolean> {

	private final String _login;
	private final String _password;

	public ThreadConnexion(final String parLogin, final String parPassword) {
		super();
		_login = parLogin;
		_password = parPassword;
	}

	@Override
	public final Boolean call() {
		// on créé le message
		ConnexionMessage message = (ConnexionMessage) EnumMessage.CONNEXION.createMessage();
		message.setLogin(_login);
		message.setPassword(_password);
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final InfoProvider locFileProvider = InfoProviderManager.getUiInfoProvider();
		Socket locSocket = null;
		try {
			// on envoie le message
			locSocket = new Socket(locConfiguration.getHostName(), locConfiguration.getPort());
			ReadWriterUtil.write(locSocket, message);
			locFileProvider.appendMessage(Level.INFO, "Tentative de connexion...");
		} catch (IOException locException) {
			locFileProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", locConfiguration.getHostName(), locConfiguration.getPort()), locException);
			SystemUtil.close(locSocket);
			return false;
		}
		// on écoute la réponse
		IMessage messageRetour = null;
		try {
			messageRetour = ReadWriterUtil.read(locSocket);
		} catch (ClassNotFoundException e) {
			SystemUtil.close(locSocket);
			return false;
		} catch (IOException e) {
			locFileProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", locConfiguration.getHostName(), locConfiguration.getPort()), e);
			SystemUtil.close(locSocket);
			return false;
		}
		
		if (messageRetour instanceof IWithSupport) {
			IWithSupport locSupport = (IWithSupport) messageRetour;
			locFileProvider.appendMessage(Level.INFO, locSupport.getSupport());
		}
		final EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
		if (EnumMessage.isError(mess)) {
			// Va falloir qu'il se reconnecte. Le thread se fait kill, on en
			// recréera pour une nouvelle connexion.
			SystemUtil.close(locSocket);
			return false;
		}
		final Downloader locDownloader = new ClientDownloader(locSocket, locFileProvider);
		locDownloader.download();
		SystemUtil.close(locSocket);
		return true;
	}
}
