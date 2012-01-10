package org.server.partie;

import java.io.IOException;
import java.net.Socket;

import org.commons.entity.Banque;
import org.commons.message.AnswerMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.util.StringUtil;
import org.server.concurrent.ReadWriterUtil;

public final class ThreadPartie implements Runnable {

	final private Partie _partie;
	final private Socket _socket;
	final private String _userName;
	
	public ThreadPartie(final String parUser, final Socket parSocket, final Partie parPartie) {
		_partie = parPartie;
		_socket = parSocket;
		_userName = parUser;
	}
	
	@Override
	public final void run() {
		while(_partie.isFinished() == false) {
			final DisplayMessage locMessage = (DisplayMessage) EnumMessage.DISPLAY.createMessage();
			final Banque locBanque = _partie.next();
			locMessage.setFileName(locBanque.getConstName());
			try {
				ReadWriterUtil.write(_socket, locMessage);
			} catch (IOException e) {
			}
			final String locBanqueAnswer = locBanque.getAnswer();
			while(_partie.hasWinner() == false) {
				AnswerMessage locAnswerMessage = null;
				try {
					locAnswerMessage = (AnswerMessage) ReadWriterUtil.read(_socket);
				} catch (ClassNotFoundException e) {
				} catch (IOException e) {
				}
				final String locAnswer = locAnswerMessage.getAnswer();
				if(StringUtil.equals(locBanqueAnswer, locAnswer)) {
					//TODO : On envoie un Info à tous
					_partie.notifyWinner(_userName);
				} else {
					//TODO : On lui envoie une erreur
					
				}
			}
		}
	}	
}
