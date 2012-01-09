package org.server.main;

import java.net.Socket;

import org.commons.message.IMessage;

public final class MessageEncapsulator {

	private IMessage _message;
	private Socket _socket;
	
	public MessageEncapsulator() {
		this(null, null);
	}
	
	public MessageEncapsulator(final Socket parSocket, final IMessage parMessage) {
		_message = parMessage;
		_socket = parSocket;
	}
	public final IMessage getMessage() {
		return _message;
	}
	public final void setMessage(IMessage _message) {
		this._message = _message;
	}
	public final Socket getSocket() {
		return _socket;
	}
	public final void setSocket(Socket _socket) {
		this._socket = _socket;
	}
	
	
	
}
