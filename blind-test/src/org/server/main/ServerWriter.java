package org.server.main;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.server.concurrent.ReadWriterUtil;

public class ServerWriter implements Runnable {
	
	static private final Runnable INSTANCE = new ServerWriter ();
	
	static public final Runnable getInstance() {
		return INSTANCE;
	}
	
	private final BlockingQueue<EncapsulatorMessage> _taskList;
	
	private ServerWriter () {
		_taskList = new ArrayBlockingQueue<EncapsulatorMessage>(100);
	}

	public final void submit(final EncapsulatorMessage parMessage) {
		_taskList.add(parMessage);
	}
	
	public void run() {
		  while(_taskList.isEmpty() == false){
			  final EncapsulatorMessage locEncapsulator = _taskList.poll();
			  try {
				ReadWriterUtil.write(locEncapsulator.getSocket(), locEncapsulator.getMessage());
			} catch (IOException e) {
				
			}
		  }
	}
}