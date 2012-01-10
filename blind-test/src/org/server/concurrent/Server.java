package org.server.concurrent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.message.IMessage;
import org.commons.util.WithUtilities;
import org.server.action.AbstractAction;
import org.server.action.EnumAction;

/**
 * Class server
 * @author adour
 *
 */

public final class Server {

	private int port; 
	private ServerSocket  serverSocket;
	private BlindTestExecutor blindTestExecutor;
	private InfoProvider logger;

	public Server(InfoProvider logger){
		this.logger = logger;
		blindTestExecutor = new BlindTestExecutor(30);
		port = ConfigurationManager.getConfiguration().getPort();
	}


	public void start() throws IOException{
		serverSocket = new ServerSocket(port);
		handleConnexion();
	}


	private void handleConnexion(){
		while (true){
			Socket socket = null;
			IMessage message = null;
			try {
				socket = serverSocket.accept();
				message =  ReadWriterUtil.read(socket);
			}
			catch (IOException e) {
				logger.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", socket.getInetAddress().getHostAddress(), socket.getPort()),e);
				continue;
			} catch (ClassNotFoundException e) {
				continue;
			}
			EnumAction enumAction = WithUtilities.getById(EnumAction.values(), message.getId()); 
			AbstractAction action  = enumAction.createAction(socket, message);
			blindTestExecutor.submit(action);
		}
	}

}
