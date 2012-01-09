package org.server.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

import org.commons.logger.InfoProviderManager;

public class Authentication implements Runnable {

	private Socket socket;
	private PrintWriter out = null;
	private BufferedReader in = null;
	private String login = "login", pass =  null;
	private boolean authentifier = false;
	public Thread t2;
	
	public Authentication(Socket s){
		 socket = s;
		}
	public void run() {
	
		try {
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream());
			
		while(!authentifier){
			
			out.println("Please type your login :");
			out.flush();
			login = in.readLine();
			
			
			out.println("Please type your password :");
			out.flush();
			pass = in.readLine();

			if(isValid(login, pass)){
				
				out.println("connecting");
				InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, login +" has just connected !!");
				out.flush();
				authentifier = true;	
			}
			else {
				out.println("error"); 
				out.flush();}
		 }
			t2 = new Thread(new DataServer(socket,login));
			t2.start();
			
		} catch (IOException e) {
			
			System.err.println(login+" is not responding !");
		}
	}
	
	private static boolean isValid(String login, String pass) {
		
		
		boolean connexion = false;
		try {
			Scanner sc = new Scanner(new File("auth_data.txt"));
			
			
			while(sc.hasNext()){
				if(sc.nextLine().equals(login+" "+pass)){
              	  connexion=true;
				  break;
				}
             }
			
		} catch (FileNotFoundException e) {	
			System.err.println("File not Found !");
		}
	return connexion;
		
	}

}