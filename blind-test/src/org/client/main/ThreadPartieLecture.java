package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.InfoMessage;
import org.commons.message.WinnerMessage;
import org.commons.util.SystemUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;

public class ThreadPartieLecture implements Runnable {

	private Socket socket = null;
	private ThreadPartieEcriture tEcriture = null;
	
	public ThreadPartieLecture(Socket socket, ThreadPartieEcriture tEcriture) {
		super();
		this.socket = socket;
		this.tEcriture = tEcriture;
	}

	@Override
	public void run() {
		
		InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		
		while (true) {
			try {
				IMessage messageRetour = ReadWriterUtil.read(socket);
				EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
				
				if (EnumMessage.isDisplay(mess)) {
					tEcriture.addIMessage(messageRetour);
				}
				else if (EnumMessage.isError(mess)) {
					DisplayMessage display = (DisplayMessage) EnumMessage.DISPLAY.createMessage();
					display.setFileName(tEcriture.getCurrentImage());
					tEcriture.addIMessage(display);
				}
				else if (EnumMessage.isWinner(mess)) {
					InfoMessage info = (InfoMessage) EnumMessage.INFO.createMessage();
					info.setMessage("ACK - WINNER");
					ReadWriterUtil.write(socket, info);
					
					WinnerMessage win = (WinnerMessage) messageRetour;
					fileProvider.appendMessage(Level.INFO, "Bravo à l'utilisateur " + win.getLogin() + " qui a trouvé la bonne réponse !");
				}
				
				
			} catch (ClassNotFoundException e) {}
			catch (IOException e) {
				fileProvider.appendMessage(Level.SEVERE, "Inscription - erreur de connexion au serveur" , e);
				e.printStackTrace();
				SystemUtil.exit();
			}
		}
	}
	
	
}
