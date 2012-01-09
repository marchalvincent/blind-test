package org.server.main;

import java.io.PrintWriter;
import java.util.Scanner;

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
			    System.out.println(message);
				message = sc.nextLine();
				out.println(message);
			    out.flush();
			  }
	}
}