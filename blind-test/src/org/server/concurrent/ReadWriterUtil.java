package org.server.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.commons.message.IMessage;
import org.commons.util.SystemUtil;

public final class ReadWriterUtil {

	static public final IMessage read(final Socket parSocket) throws IOException, ClassNotFoundException {
		ObjectInputStream locStream = null;
		try {
			locStream = new ObjectInputStream(parSocket.getInputStream());
			return (IMessage) locStream.readObject();
		} finally {
			SystemUtil.close(locStream);
		}
	}
	
	static public final void write(final Socket parSocket, final IMessage parMessage) throws ClassNotFoundException, IOException {
		ObjectOutputStream locStream = null;
		try {
			locStream= new ObjectOutputStream (parSocket.getOutputStream());
			locStream.writeObject(parMessage);
		} finally {
			SystemUtil.close(locStream);
		}
	}
	private ReadWriterUtil() {}
}
