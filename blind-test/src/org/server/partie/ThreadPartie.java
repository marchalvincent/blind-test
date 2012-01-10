package org.server.partie;

import java.io.IOException;
import java.net.Socket;

import org.commons.entity.Banque;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.AnswerMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.message.ErrorMessage;
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
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		while(_partie.isFinished() == false) {
			final DisplayMessage locMessage = (DisplayMessage) EnumMessage.DISPLAY.createMessage();
			final Banque locBanque = _partie.next();
			locMessage.setFileName(locBanque.getConstName());
			try {
				ReadWriterUtil.write(_socket, locMessage);
			} catch (IOException e) {
			}
			final String locBanqueAnswer = locBanque.getAnswer();
			while(true) {
				AnswerMessage locAnswerMessage = null;
				try {
					locAnswerMessage = (AnswerMessage) ReadWriterUtil.read(_socket);
				} catch (ClassNotFoundException e) {
				} catch (IOException e) {
				}
				final String locAnswer = locAnswerMessage.getAnswer();
				if(StringUtil.equals(locBanqueAnswer, locAnswer)) {
					_partie.updateStats(_userName);
					_partie.notifyWinner(locInfoProvider, _userName);
					while(_partie.canDisplayNewImage() == false) {
						if(_partie.isReboot() == false) {
							_partie.rebootAck();
						}
					}
					break;
				} else {
					final ErrorMessage locErrorMessage = (ErrorMessage) EnumMessage.ERROR.createMessage();
					final String locValue = "La réponse est incorrect.";
					locErrorMessage.setMessage(locValue);
					try {
						ReadWriterUtil.write(_socket, locErrorMessage);
					} catch (IOException e) {
					}
				}
			}
		}
	}	
}
