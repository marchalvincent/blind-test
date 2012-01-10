package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.logging.Level;

import org.client.ui.AccueilPanel;
import org.client.ui.Fenetre;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.DisplayMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.PlayMessage;
import org.commons.util.IWithSupport;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.commons.util.WithUtilities;
import org.server.concurrent.ReadWriterUtil;

public class ThreadPartieEcriture implements Runnable {

	private String login = null;
	private Fenetre fenetre = null;
	private String currentImage = null;
	private Boolean isClicked = false;
	private ArrayBlockingQueue<IMessage> abq;
	
	
	
	public ThreadPartieEcriture(Fenetre fenetre, String login) {
		super();
		this.login = login;
		this.fenetre = fenetre;
		abq = new ArrayBlockingQueue<IMessage>(20);
	}
	
	public void setCurrentImage(String currentImage) {
		this.currentImage = currentImage;
	}

	public void setIsClicked(Boolean isClicked) {
		this.isClicked = isClicked;
	}

	@Override
	public void run() {
		
		Configuration config = ConfigurationManager.getConfiguration();
		InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		String name;
		StringBuilder sb = new StringBuilder();
		
		while(!abq.isEmpty()) {
			IMessage messageRetour = abq.poll();

			//on affiche dans la console du client
			if(messageRetour instanceof IWithSupport) {
				IWithSupport locSupport = (IWithSupport) messageRetour;
				fileProvider.appendMessage(Level.INFO, locSupport.getSupport());
			}
			
			EnumMessage mess = WithUtilities.getById(EnumMessage.values(), messageRetour.getId());
			if (EnumMessage.isDisplay(mess)) {
				DisplayMessage dm = (DisplayMessage) messageRetour;
				name = dm.getFileName();
				
				if (!StringUtil.equals(name, currentImage)) {
					currentImage = name;
					sb.delete(0, (sb.length() - 1));
					sb.append(config.getImageDirectory());
					sb.append(name);
					String fileName = sb.toString();
					
					//
					
				}
			}
		}
		
	}

}
