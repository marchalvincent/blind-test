package org.server.action;

import java.net.Socket;

import org.commons.message.IMessage;

public abstract class AbstractAction implements Runnable {

	final private IMessage _message;
	final private Socket _socket;
	
	protected AbstractAction (final Socket parSocket, final IMessage parMessage) {
		_message = parMessage;
		_socket = parSocket;
	}
	
	protected final Socket getSocket() {
		return _socket;
	}
	
	protected final IMessage getMessage() {
		return _message;
	}
	
}
