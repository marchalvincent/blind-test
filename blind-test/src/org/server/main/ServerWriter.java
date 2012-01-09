package org.server.main;

import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ServerWriter implements Runnable {
	
	static private final Runnable INSTANCE = new ServerWriter ();
	
	static public final Runnable getInstance() {
		return INSTANCE;
	}
	
	private final BlockingQueue<EncapsulatorMessage> _taskList;
	
	private ServerWriter () {
		_taskList = new ArrayBlockingQueue<EncapsulatorMessage>(100);
	}

	
	public void run() {
		  while(_taskList.isEmpty() == false){
			  final EncapsulatorMessage locEncapsulator = _taskList.poll();
		  }
	}
}