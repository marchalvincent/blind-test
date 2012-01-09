package org.server.main;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Send implements Runnable {
	
	static private final Runnable INSTANCE = new Send ();
	
	private final BlockingQueue<MessageEncapsulator> _taskList;
	
	private Send () {
		_taskList = new ArrayBlockingQueue<MessageEncapsulator>(100);
	}

	
	public void run() {
		  while(_taskList.isEmpty() == false){
			  final MessageEncapsulator locEncapsulator = _taskList.poll();
			  final Socket locSocket;
		  }
	}
	
	public static Runnable getInstance() {
		
		return INSTANCE;
	}
}