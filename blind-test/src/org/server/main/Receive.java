package org.server.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

public class Receive implements Runnable {

	private ObjectInputStream in;
	private Object message = null, login = null;
	
	public Receive (ObjectInputStream in, String login){
		
		this.in = in;
		this.login = login;
	}
	
	public void run() {
		
		while(true){
	        try {
	        	
			message = in.readObject();
			InfoProviderManager.getFileProvider().appendMessage(Level.INFO, login+" : "+message);
			
		    } catch (IOException e) {
				
				InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Server Receive Error");
			} catch (ClassNotFoundException e) {
				InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Server Receive Error");
			}
		}
	}

}