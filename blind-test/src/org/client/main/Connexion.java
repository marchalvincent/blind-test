package org.client.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

public class Connexion implements Runnable {

	private Socket socket = null;
	public static Thread t2;
	public static String login = null, pass = null;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private Scanner sc = null;
	private boolean connect = false;
	
	public Connexion(Socket s){
		
		socket = s;
	}
	
	public void run() {
		
		try {
			
		out = new PrintWriter(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));	
		sc = new Scanner(System.in);
	
		
		while(!connect ){
		
			InfoProviderManager.getFileProvider().appendMessage(Level.INFO, in.readLine());
			login = sc.nextLine();
			out.println(login);
			out.flush();
		
			InfoProviderManager.getFileProvider().appendMessage(Level.INFO, in.readLine());
			pass = sc.nextLine();
			out.println(pass);
			out.flush();
		
		if(in.readLine().equals("connecting")){
			
			InfoProviderManager.getFileProvider().appendMessage(Level.INFO, "connected "); 
			connect = true;
		  }
		
		else {
			System.err.println("Incorrect login or pass"); 
		  }
		
	}
			
			t2 = new Thread(new DataClient(socket));
			t2.start();
		
		} catch (IOException e) {
			
			System.err.println("Server not responding ");
		}
	}
}
