package org.server.action;

import java.net.Socket;

import org.commons.message.IMessage;

public class FinishGameAction extends AbstractAction {

	protected FinishGameAction(Socket parSocket, IMessage parMessage) {
		super(parSocket, parMessage);
	}

	@Override
	public void run() {
		
	}

}
