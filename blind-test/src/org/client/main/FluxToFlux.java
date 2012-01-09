package org.client.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class FluxToFlux extends Thread {

	private BufferedReader bRead;
	private BufferedWriter bWrite;
	
	public FluxToFlux(BufferedReader br, BufferedWriter bw) {
		bRead = br;
		bWrite = bw;
	}
	
	public void run() {
		while(true) {
			try {
				bWrite.write(bRead.readLine() + "\n");
				bWrite.flush();
			} catch(java.net.SocketException e) {
				System.out.println("connexion perdue. Deconnexion.");
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
