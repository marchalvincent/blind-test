package org.server.main;

import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

public class Send implements Runnable {

	private PrintWriter out;
	private String message = null;
	private Scanner sc = null;
	
	public Send (PrintWriter out) {
		this.out = out;
	}

	
	public void run() {
		
		  sc = new Scanner(message);
		  
		  while(true){
			  InfoProviderManager.getFileProvider().appendMessage(Level.INFO, message);
				message = sc.nextLine();
				out.println(message);
			    out.flush();
			  }
	}
}