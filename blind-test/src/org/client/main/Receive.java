package org.client.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

public class Receive implements Runnable {

	private BufferedReader in;
	private String message = null;
	
	public Receive (BufferedReader in){
		
		this.in = in;
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readLine();
			InfoProviderManager.getFileProvider().appendMessage(Level.INFO, message);
			
		    } catch (IOException e) {
				
		    	InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Receive Error");
			}
		}
	}

}