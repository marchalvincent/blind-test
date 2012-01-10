package org.server.action;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.EnumMessage;
import org.commons.message.ErrorMessage;
import org.commons.message.IMessage;
import org.commons.message.InfoMessage;
import org.commons.message.InscriptionMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;
import org.server.persistence.Manager;
import org.server.persistence.Managers;

public final class InscriptionAction extends AbstractAction {

	protected InscriptionAction (final Socket parSocket, final IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public final void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final IMessage locMessage = getMessage();
		if(locMessage instanceof InscriptionMessage == false) {
			locInfoProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + locMessage);
			SystemUtil.close(getSocket());
			return;
		}
		final InscriptionMessage locInscriptionMessage = (InscriptionMessage) locMessage;
		final Manager<User> locUserManager = Managers.createUserManager();
		final String locLogin = locInscriptionMessage.getLogin();
		User locUser = locUserManager.find(locLogin);
		// Le compte existe déjà
		final Socket locSocket = getSocket();
		if(locUser != null) {
			final String locResultat = String.format("Le compte %s existe déjà. Impossible de créer un nouveau compte avec ce login", locLogin);
			locInfoProvider.appendMessage(Level.INFO, locResultat);
			final ErrorMessage locDefaultMessage = (ErrorMessage) EnumMessage.ERROR.createMessage();
			locDefaultMessage.setMessage(locResultat);
			try {
				ReadWriterUtil.write(locSocket, locDefaultMessage);
			} catch (IOException e) {
				locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", locSocket.getInetAddress().getHostAddress()), e);
			} finally {
				SystemUtil.close(locSocket);
			}
			return;
		}
		locUser = new User();
		locUser.setLogin(locLogin);
		locUser.setName(locInscriptionMessage.getNom());
		locUser.setPassword(locInscriptionMessage.getPassword());
		locUser = locUserManager.add(locUser);
		final String locAnswerMessage = String.format("L'utilisateur %s a été créé.", locLogin);
		locInfoProvider.appendMessage(Level.INFO, locAnswerMessage);
		final InfoMessage locResponseMessage = (InfoMessage) EnumMessage.INFO.createMessage();
		locResponseMessage.setMessage(locAnswerMessage);
		try {
			ReadWriterUtil.write(locSocket, locResponseMessage);
		} catch (IOException e) {
			locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", locSocket.getInetAddress().getHostAddress()), e);
		} finally {
			SystemUtil.close(locSocket);
		}
	}	
}
