package org.server.main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

	public class AcceptConnexion implements Runnable{

		private ServerSocket socketserver = null;
		private Socket socket = null;

		public Thread t1;
		public AcceptConnexion(ServerSocket ss){
		 socketserver = ss;
		}
		
		public void run() {
			
			try {
				while(true){
					
				socket = socketserver.accept();
				InfoProviderManager.getFileProvider().appendMessage(Level.INFO, "A client is connecting ");
				
				t1 = new Thread(new Authentication(socket));
				t1.start();
				
				}
			} catch (IOException e) {
				
				System.err.println("Server Error");
			}
			
		}
	}
