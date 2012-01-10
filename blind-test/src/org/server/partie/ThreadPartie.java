package org.server.partie;

import java.net.Socket;
import java.util.concurrent.Callable;

import org.commons.entity.Banque;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.server.concurrent.ReadWriterUtil;

public final class ThreadPartie implements Callable<String> {

	final private Partie _partie;
	final private Socket _socket;
	
	public ThreadPartie(final Socket parSocket, final Partie parPartie) {
		_partie = parPartie;
		_socket = parSocket;
	}
	
	@Override
	public final String call() {
		//TODO : A remplacer par partie.isFinish()
		while(true) {
			final Banque locBanque = _partie.next();
			final DisplayMessage locDisplayMessage = (DisplayMessage) EnumMessage.DISPLAY.createMessage();
			locDisplayMessage.setFileName(locBanque.getConstName());
			ReadWriterUtil.write(_socket, locDisplayMessage);
			
		}
	}	
}
