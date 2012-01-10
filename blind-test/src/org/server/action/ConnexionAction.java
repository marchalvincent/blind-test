package org.server.action;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.cache.Caches;
import org.commons.downloader.Downloader;
import org.commons.downloader.ServerDownloader;
import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.ConnexionMessage;
import org.commons.message.EnumMessage;
import org.commons.message.ErrorMessage;
import org.commons.message.IMessage;
import org.commons.message.InfoMessage;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;
import org.server.persistence.Manager;
import org.server.persistence.Managers;

public final class ConnexionAction extends AbstractAction {

	protected ConnexionAction(final Socket parSocket, final IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public final void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final IMessage locMessage = getMessage();
		if(locMessage instanceof ConnexionMessage == false) {
			locInfoProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + locMessage);
			SystemUtil.close(locSocket);
			return;
		}
		final ConnexionMessage locConnexionMessage = (ConnexionMessage) getMessage();
		final String locLogin = locConnexionMessage.getLogin();
		final Manager<User> locUserManager = Managers.createUserManager();
		final User locUser = locUserManager.find(locLogin);
		final Socket locSocket = getSocket();
		if(locUser == null) {
			final String locResponseMessage = String.format("Le compte de l'utilisateur %s n'existe pas. Veuillez créer un compte avant de tenter de vous connecter.", locLogin);
			locInfoProvider.appendMessage(Level.WARNING, locResponseMessage);
			final ErrorMessage locErrorMessage = (ErrorMessage) EnumMessage.ERROR.createMessage();
			locErrorMessage.setMessage(locResponseMessage);
			try {
				ReadWriterUtil.write(locSocket, locErrorMessage);
			} catch (IOException e) {
				locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", locSocket.getInetAddress().getHostAddress()), e);
			} finally {
				SystemUtil.close(locSocket);
			}
			return;
		}
		final String locPassword = locConnexionMessage.getPassword();
		if(StringUtil.equals(locPassword, locUser.getPassword())) {
			computeConnexion(locUser, locInfoProvider, locSocket);
			return;
		}
		final String locResponseMessage = String.format("Le login ou le mot de passe est incorrect. Impossible de se connecter");
		locInfoProvider.appendMessage(Level.INFO, locResponseMessage);
		final ErrorMessage locErrorMessage = (ErrorMessage) EnumMessage.ERROR.createMessage();
		locErrorMessage.setMessage(locResponseMessage);
		try {
			ReadWriterUtil.write(locSocket, locErrorMessage);
		} catch (IOException e) {
			locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", locSocket.getInetAddress().getHostAddress()), e);
		} finally {
			SystemUtil.close(locSocket);
		}
	}
	
	private final void computeConnexion(final User parUser, final InfoProvider parInfoProvider, final Socket parSocket) {
		final String locLogin = parUser.getConstName();
		Caches.user().put(locLogin, parUser);
		final String locResponseMessage = String.format("L'utilisateur %s s'est connecté.", locLogin);
		parInfoProvider.appendMessage(Level.INFO, locResponseMessage);
		final InfoMessage locInfoMessage = (InfoMessage) EnumMessage.INFO.createMessage();
		locInfoMessage.setMessage(locResponseMessage);
		try {
			ReadWriterUtil.write(parSocket, locInfoMessage);
		} catch (IOException e) {
			parInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", parSocket.getInetAddress().getHostAddress()), e);
		} 
		final Downloader locDownloader = new ServerDownloader(parSocket, parInfoProvider);
		locDownloader.download();
	}
}
