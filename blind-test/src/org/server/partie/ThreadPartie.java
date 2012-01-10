package org.server.partie;

import java.net.Socket;

public final class ThreadPartie implements Runnable {

	final private Partie _partie;
	final private Socket _socket;
	
	public ThreadPartie(final Socket parSocket, final Partie parPartie) {
		_partie = parPartie;
		_socket = parSocket;
	}
	
	@Override
	public final void run() {
		
	}	
}
