package org.server.action;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.IMessage;
import org.commons.message.StatMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;
import org.server.persistence.Manager;
import org.server.persistence.Managers;

public class StatAction extends AbstractAction {

	protected StatAction(Socket parSocket, IMessage parMessage) {
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
		
		final StatMessage locStatMessage = (StatMessage) locMessage;
		final Manager<User> locUserManager = Managers.createUserManager();
		final String locLogin = locStatMessage.getLogin();
		User locUser = locUserManager.find(locLogin);
		final Socket locSocket = getSocket();

		locStatMessage.setDefaite(locUser.getDefaite());
		locStatMessage.setVictoire(locUser.getVictoire());
		
		try {
			ReadWriterUtil.write(locSocket,locStatMessage);
		} catch (IOException e) {
			locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", locSocket.getInetAddress().getHostAddress()), e);
		} finally {
			SystemUtil.close(locSocket);
		}
	}

}
