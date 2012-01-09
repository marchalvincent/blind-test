package org.server.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.commons.message.IMessage;

public final class ReadWriterUtil {

	static public final IMessage read(final Socket parSocket) throws ClassNotFoundException, IOException {
		final ObjectInputStream locStream = new ObjectInputStream(parSocket.getInputStream());
		return (IMessage) locStream.readObject();
	}
	
	static public final void write(final Socket parSocket, final IMessage parMessage) throws ClassNotFoundException, IOException {
		final ObjectOutputStream writeStream = new ObjectOutputStream (parSocket.getOutputStream());
		writeStream.writeObject(parMessage);
		
	}
	
	private ReadWriterUtil() {}
}
