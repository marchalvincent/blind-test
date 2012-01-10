package org.server.main;

import java.net.Socket;

import org.commons.message.IMessage;

public final class EncapsulatorMessage {

	private IMessage _message;
	private Socket _socket;
	
	public EncapsulatorMessage() {
		this(null, null);
	}
	
	public EncapsulatorMessage(final Socket parSocket, final IMessage parMessage) {
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
