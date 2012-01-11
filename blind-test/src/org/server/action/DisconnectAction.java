package org.server.action;

import java.net.Socket;
import java.util.logging.Level;

import org.commons.cache.Caches;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.DisconnectMessage;
import org.commons.message.IMessage;
import org.commons.message.StatMessage;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;

public class DisconnectAction extends AbstractAction {

	protected DisconnectAction(Socket parSocket, IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final IMessage locMessage = getMessage();
		
		if(locMessage instanceof StatMessage == false) {
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
			Caches.parties().remove(partie);
		}
		
		SystemUtil.close(getSocket());
	}

}
