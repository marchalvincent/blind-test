package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.client.ui.JouerPanel;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;

public class PartieClient {

	private JouerPanel fenetre = null;
	private String login = null;
	
	public PartieClient(JouerPanel fenetre, String login) {
		this.fenetre = fenetre;
		this.login = login;
	}
	
	public void startPartieClient() {
		Configuration config = ConfigurationManager.getConfiguration();
		InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		
		Socket socket = null;
		try {
			
			socket = new Socket(config.getHostName(), config.getPort());
			
			ThreadPartieEcriture tEcriture = new ThreadPartieEcriture(socket, fenetre, login);
			ThreadPartieLecture tLecture = new ThreadPartieLecture(socket, tEcriture);
			
			tEcriture.run();
			tLecture.run();
			
		} catch (IOException e) {
			fileProvider.appendMessage(Level.SEVERE, "Inscription - erreur de connexion au serveur");
			SystemUtil.close(socket);
		}
		
		SystemUtil.close(socket);
	}
}
