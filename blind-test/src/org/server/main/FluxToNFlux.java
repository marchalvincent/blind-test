package org.server.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.logging.Level;

import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;

public class FluxToNFlux extends Thread {
	
	private BufferedReader brSocket;
	private BufferedWriter bwSocket;
	private ServerMain serveur;

	public FluxToNFlux(BufferedReader brSock, BufferedWriter bw, ServerMain s) {
		brSocket = brSock;
		bwSocket = bw;
		serveur = s;
	}
	
	public void run() {
		while(true) {
			try {
				String messageRecu = brSocket.readLine();
				if (messageRecu != null) {
					sendToAll(messageRecu);
				}
			} catch (java.net.SocketException e) {
				serveur.getWriters().remove(bwSocket);
				final InfoProvider locProvider = InfoProviderManager.getFileProvider();
				locProvider.appendMessage(Level.INFO, "Un client a été déconnecté.");
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void sendToAll(String message) throws IOException {
		for(BufferedWriter write : serveur.getWriters()) {
			write.write(message + "\n");
			write.flush();
		}
	}
}
