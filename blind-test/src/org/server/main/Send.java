package org.server.main;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Send implements Runnable {
	
	static private final Runnable INSTANCE = new Send ();
	
	static public final Runnable getInstance() {
		return INSTANCE;
	}
	
	private final BlockingQueue<EncapsulatorMessage> _taskList;
	
	private Send () {
		_taskList = new ArrayBlockingQueue<EncapsulatorMessage>(100);
	}

	
	public void run() {
		  while(_taskList.isEmpty() == false){
			  final EncapsulatorMessage locEncapsulator = _taskList.poll();
			  final Socket locSocket;
		  }
	}
}