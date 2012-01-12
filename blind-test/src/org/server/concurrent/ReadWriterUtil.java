package org.server.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.commons.message.IMessage;

public final class ReadWriterUtil {

	static public final IMessage read(final Socket parSocket) throws IOException, ClassNotFoundException {
		ObjectInputStream locStream = new ObjectInputStream(parSocket.getInputStream());
		return (IMessage) locStream.readObject();
	}
	
	static public final void write(final Socket parSocket, final IMessage parMessage) throws IOException {
		final ObjectOutputStream locStream= new ObjectOutputStream (parSocket.getOutputStream());
		locStream.writeObject(parMessage);
		locStream.flush();
	}
	private ReadWriterUtil() {}
}
