package org.server.action;

import java.net.Socket;

import org.commons.message.IMessage;

public class DisconnectAction extends AbstractAction {

	protected DisconnectAction(Socket parSocket, IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public void run() {

	}

}
