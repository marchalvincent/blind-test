package org.client.main;

import java.io.PrintWriter;
import java.util.Scanner;

public class Send implements Runnable {

	private PrintWriter out;
	private String login = null, message = null;
	private Scanner sc = null;
	
	public Send (PrintWriter out) {
		this.out = out;
		
	}

	
	public void run() {
		
		  sc = new Scanner(System.in);
		  
		  while(true){
			    System.out.println(login + " : ");
				message = sc.nextLine();
				out.println(message);
			    out.flush();
			  }
	}
}