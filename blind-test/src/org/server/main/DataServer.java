package org.server.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class DataServer implements Runnable {

	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private String login = "zero";
	private Thread t3, t4;
	
	
	public DataServer (Socket s, String log){
		
		socket = s;
		login = log;
	}
	public void run() {
		
		try {
		in = new ObjectInputStream (socket.getInputStream());
		out = new ObjectOutputStream (socket.getOutputStream());
		
		t3 = new Thread(new Receive (in,login));
		t3.start();
		t4 = new Thread(Send.getInstance());
		t4.start();
		
		} catch (IOException e) {
			System.err.println(login +"s'est déconnecté ");
		}
}
}