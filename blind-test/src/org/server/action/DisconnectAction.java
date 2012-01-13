package org.server.action;

import java.net.Socket;
import java.util.logging.Level;

import org.commons.cache.AbstractCache;
import org.commons.cache.Caches;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.DisconnectMessage;
import org.commons.message.IMessage;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.server.partie.Partie;

public class DisconnectAction extends AbstractAction {

	protected DisconnectAction(Socket parSocket, IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final IMessage locMessage = getMessage();
		
		if(locMessage instanceof DisconnectMessage == false) {
			locInfoProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + locMessage);
			SystemUtil.close(getSocket());
			return;
		}
		
		DisconnectMessage locDiscoMess = (DisconnectMessage) locMessage;

		//On supprime le user
		Caches.user().remove(locDiscoMess.getLogin());
		
		//On supprime la partie
		final String partie = locDiscoMess.getPartie();
		if (StringUtil.isNotEmpty(partie)) {
			final AbstractCache<String, Partie> locPartiesCache = Caches.parties();
			final Partie locPartie = locPartiesCache.get(partie);
			if(locPartie.isEmpty()) {
				locPartiesCache.remove(partie);
			}
		}
		
		SystemUtil.close(getSocket());
	}

}
