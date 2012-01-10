package org.client.main;

import java.net.Socket;

public class ThreadPartieLecture implements Runnable {

	private Socket socket = null;
	private ThreadPartieEcriture tEcriture = null;
	
	public ThreadPartieLecture(Socket socket, ThreadPartieEcriture tEcriture) {
		super();
		this.socket = socket;
		this.tEcriture = tEcriture;
	}

	@Override
	public void run() {
		
	}
	
	
}
