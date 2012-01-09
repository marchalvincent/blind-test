package org.server.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

public class Receive implements Runnable {

	private BufferedReader in;
	private String message = null, login = null;
	
	public Receive (BufferedReader in, String login){
		
		this.in = in;
		this.login = login;
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
			InfoProviderManager.getFileProvider().appendMessage(Level.INFO, login+" : "+message);
			
		    } catch (IOException e) {
				
				InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Server Receive Error");
			}
		}
	}

}