package org.server.action;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.cache.AbstractCache;
import org.commons.cache.Caches;
import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.EnumMessage;
import org.commons.message.ErrorMessage;
import org.commons.message.IMessage;
import org.commons.message.PlayMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;
import org.server.partie.Partie;
import org.server.partie.ThreadPartie;

public class PlayAction extends AbstractAction {

	protected PlayAction(final Socket parSocket, final IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public final void run() {
		final IMessage locMessage = getMessage();
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final Socket locSocket = getSocket();
		if(locMessage instanceof PlayMessage == false) {
			locInfoProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + locMessage);
			SystemUtil.close(locSocket);
			return;
		}
		final PlayMessage locPlayMessage = (PlayMessage) locMessage;
		final AbstractCache<String, User> locCacheUser = Caches.user();
		final String locLogin = locPlayMessage.getLogin();
		final User locUser = locCacheUser.get(locLogin);
		// Pas connecté : bug
		if(locUser == null) {
			final String locErrorMessage = String.format("Le joueur %s n'est pas connecté. Veuillez vous connecter.", locLogin);
			locInfoProvider.appendMessage(Level.SEVERE, locErrorMessage);
			final ErrorMessage locErrorRealMessage = (ErrorMessage) EnumMessage.ERROR.createMessage();
			locErrorRealMessage.setMessage(locErrorMessage);
			try {
				ReadWriterUtil.write(locSocket, locErrorRealMessage);
			} catch (IOException e) {	
				locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", locSocket.getInetAddress().getHostAddress()), e);
			} finally {
				SystemUtil.close(locSocket);
			}
			return;
		}
		final AbstractCache<String, Partie> locPartieCache = Caches.parties();
		final String locNomPartie = locPlayMessage.getNomPartie();
		Partie locPartie = locPartieCache.get(locNomPartie);
		if(locPartie == null) {
			locPartie = new Partie(locNomPartie);
			locPartie.updateImage();
			locPartieCache.put(locNomPartie, locPartie);
			locInfoProvider.appendMessage(Level.INFO, String.format("La partie \"%s\" a été créée", locPartie));
		}
		final Thread locThread = new Thread(new ThreadPartie(locUser, locSocket, locPartie));
		locThread.start();
	}
}
