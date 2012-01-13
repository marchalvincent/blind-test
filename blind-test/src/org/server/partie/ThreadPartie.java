package org.server.partie;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

import org.client.ui.display.EnumDisplayImage;
import org.commons.entity.Banque;
import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.AnswerMessage;
import org.commons.message.DisconnectMessage;
import org.commons.message.DisplayMessage;
import org.commons.message.EndGameMessage;
import org.commons.message.EnumMessage;
import org.commons.message.ErrorMessage;
import org.commons.message.IMessage;
import org.commons.message.InfoMessage;
import org.server.concurrent.ReadWriterUtil;

public final class ThreadPartie implements Runnable {

	static private final ReentrantReadWriteLock LOCK = new ReentrantReadWriteLock();

	final private Partie _partie;
	final private Socket _socket;
	final private User _user;
	private boolean _isDisconnect;
	private int _wrongAnswer;

	public ThreadPartie(final User parUser, final Socket parSocket, final Partie parPartie) {
		_partie = parPartie;
		_socket = parSocket;
		_user = parUser;
		_isDisconnect = false;
		_wrongAnswer = 0;
	}

	@Override
	public final void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		end:while(_partie.isFinished() == false) { 
			while (_partie.isReboot() == true) {
				final DisplayMessage locMessage = (DisplayMessage) EnumMessage.DISPLAY.createMessage();
				Banque locBanque = null;
				_wrongAnswer = 0;
				LOCK.writeLock().lock();
				try {
					if(_partie.hasUser(_user)) {
						if(_partie.hasChangedImage() == false) {
							locBanque = _partie.next();
						} else {
							locBanque = _partie.getCurrentAnswer();
						}
					} else {
						_partie.addUser(_user, _socket);
						locInfoProvider.appendMessage(Level.INFO, String.format("Le joueur %s a rejoint la partie %s.", _user, _partie));
						locBanque = _partie.getCurrentAnswer();
						if(locBanque == null) {
							locBanque = _partie.next();
						}
					}
				}finally {
					LOCK.writeLock().unlock();
				}
				locMessage.setFileName(locBanque.getConstName());
				final EnumDisplayImage locDisplay = EnumDisplayImage.randomDisplay();
				locMessage.setType(locDisplay.getId());
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
					if(locResponseMessage == null || locResponseMessage instanceof DisconnectMessage) {
						locInfoProvider.appendMessage(Level.INFO, String.format("Le joueur %s s'est déconnecté de la partie %s", _user.getConstName(), _partie.getConstName()));
						_partie.removeUser(_user);
						_isDisconnect = true;
						break end;
					}
					if (locResponseMessage instanceof InfoMessage) {
						LOCK.writeLock().lock();
						try {
							_partie.incrementAck();
							if (_partie.canDisplayNewImage() == true) {
								_partie.rebootAck();
							}
							if(_partie.isFinished()) {
								break end;
							}
							break;
						} finally {
							LOCK.writeLock().unlock();
						}
					}
					final AnswerMessage locAnswerMessage = (AnswerMessage) locResponseMessage;
					final String locAnswer = locAnswerMessage.getAnswer();
					LOCK.writeLock().lock();
					try {
						if (_partie.hasWinner() == false && _partie.isValidAnswer(locAnswer)) {
							_partie.notifyWinner(locInfoProvider, _user);
							if(_partie.isFinished()) {
								break end;
							}
						} else {
							if(_partie.hasWinner()) {
								continue;
							}
							final ErrorMessage locErrorMessage = (ErrorMessage) EnumMessage.ERROR.createMessage();
							final String locValue = "La réponse est incorrect.";
							++_wrongAnswer;
							if(_wrongAnswer >= 3) {
								_partie.notifyWinner(locInfoProvider, _user, false);
								if(_partie.isFinished()) {
									break end;
								}
							}
							locErrorMessage.setMessage(locValue);
							try {
								ReadWriterUtil.write(_socket, locErrorMessage);
							} catch (IOException e) {
							}
						}
					}finally {
						LOCK.writeLock().unlock();
					}
				}
			}
		}
		if(_isDisconnect == false) {
			final EndGameMessage locMessage = (EndGameMessage) EnumMessage.END_GAME.createMessage();
			locMessage.setMessage("La partie est finie");
			locMessage.putAll(_partie.getCurrentScore());
			try {
				ReadWriterUtil.write(_socket, locMessage);
			} catch (IOException e) {
			} 
		}
		_partie.removeUser(_user);
		if(_partie.isEmpty()) {
			_partie.close();
		}
	}	
}
