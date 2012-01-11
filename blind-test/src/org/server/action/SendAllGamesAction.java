package org.server.action;

import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;

import org.commons.cache.Caches;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.IMessage;
import org.commons.message.ListGamesMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

public class SendAllGamesAction extends AbstractAction {

	protected SendAllGamesAction(Socket parSocket, IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final IMessage locMessage = getMessage();
		
		if(locMessage instanceof ListGamesMessage == false) {
			locInfoProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + locMessage);
			SystemUtil.close(getSocket());
			return;
		}
		
		ListGamesMessage listGames = (ListGamesMessage) locMessage;
		List<String> allParties = Caches.parties().keys();
		listGames.addAll(allParties);
		try {
			ReadWriterUtil.write(getSocket(), locMessage);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			SystemUtil.close(getSocket());
		}
	}

}
