package org.server.partie;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.entity.Banque;
import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.AnswerMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.message.ErrorMessage;
import org.commons.message.IMessage;
import org.commons.message.InfoMessage;
import org.server.concurrent.ReadWriterUtil;

public final class ThreadPartie implements Runnable {

	final private Partie _partie;
	final private Socket _socket;
	final private User _user;
	private boolean _isDisconnected;

	public ThreadPartie(final User parUser, final Socket parSocket, final Partie parPartie) {
		_partie = parPartie;
		_socket = parSocket;
		_user = parUser;
		_isDisconnected = false;
	}

	@Override
	public final void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		while(_partie.isFinished() == false) { 
			while (_partie.isReboot() == true) {
				final DisplayMessage locMessage = (DisplayMessage) EnumMessage.DISPLAY.createMessage();
				Banque locBanque = null;
				if(_partie.hasUser(_user)) {
					locBanque = _partie.next();
				} else {
					_partie.addUser(_user, _socket);
					locInfoProvider.appendMessage(Level.INFO, String.format("Le joueur %s a rejoint la partie %s.", _user, _partie));
					locBanque = _partie.getCurrentAnswer();
					if(locBanque == null) {
						locBanque = _partie.next();
					}
				}
				locInfoProvider.appendMessage(Level.INFO, String.format("Le joueur %s a reçu l'image %s.", _user, locBanque));
				locMessage.setFileName(locBanque.getConstName());
				try {
					ReadWriterUtil.write(_socket, locMessage);
				} catch (IOException e) {
				}
				while (true) {
					IMessage locResponseMessage = null;
					try {
						locResponseMessage = ReadWriterUtil.read(_socket);
					} catch (ClassNotFoundException e) {
					} catch (IOException e) {
					}
					if(locResponseMessage == null) {
						locInfoProvider.appendMessage(Level.INFO, String.format("Le joueur %s s'est déconnecté de la partie %s", _user.getConstName(), _partie.getConstName()));
						_partie.removeUser(_user);
						_isDisconnected = true;
						break;
					}
					if (locResponseMessage instanceof InfoMessage) {
						_partie.incrementAck();
						if (_partie.canDisplayNewImage() == true) {
							_partie.rebootAck();
						}
						break;
					}
					final AnswerMessage locAnswerMessage = (AnswerMessage) locResponseMessage;
					final String locAnswer = locAnswerMessage.getAnswer();
					if (_partie.isValidAnswer(locAnswer)) {
						_partie.updateStats(_user);
						_partie.notifyWinner(locInfoProvider, _user.getConstName());
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
				// On vérifie s'il a été déco ou s'il y a eu un vainqueur
				if(_isDisconnected == true) {
					break;
				}
			}
			if(_isDisconnected == true) {
				break;
			}
		}
	}	
}
