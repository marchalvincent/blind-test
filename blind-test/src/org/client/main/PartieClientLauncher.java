package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.logging.Level;

import org.client.ui.JouerPanel;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;

public class PartieClientLauncher {

	private JouerPanel fenetre = null;
	private String login = null;
	private String _nomPartie = "";
	private int _nbImg;
	
	public PartieClientLauncher (JouerPanel fenetre, String login, String nomPartie) {
		this.fenetre = fenetre;
		this.login = login;
		_nomPartie = nomPartie;
	}
	
	public PartieClientLauncher (JouerPanel fenetre, String login, String nomPartie, int parNbImg) {
		this (fenetre, login, nomPartie);
		_nbImg = parNbImg;
	}
	
	public void startPartieClient() {
		Configuration config = ConfigurationManager.getConfiguration();
		InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		
		Socket socket = null;
		try {
			
			socket = new Socket(config.getHostName(), config.getPort());
			
			ThreadPartieEcriture tEcriture = new ThreadPartieEcriture(socket, fenetre, login, _nomPartie);
			tEcriture.setNbImg(_nbImg);
			for (Observable e : fenetre.getObservable()) {
				e.addObserver(tEcriture);
			}
			Thread threadEcriture = new Thread (tEcriture);
			threadEcriture.start();
			
		} catch (IOException e) {
			fileProvider.appendMessage(Level.SEVERE, "Erreur de connexion au serveur");
			SystemUtil.close(socket);
		}
	}
}
