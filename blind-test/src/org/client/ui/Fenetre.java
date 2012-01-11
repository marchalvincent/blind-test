package org.client.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.commons.logger.InfoProviderManager;
import org.commons.logger.UiInfoProvider;

/**
 * Classe contenant un singleton de la fenetre de
 * l'application.
 * @author francois
 *
 */
public class Fenetre {

	static private final Fenetre INSTANCE = new Fenetre();
	private JFrame fenetre;
	private LogClient logClient;
	
	static public final Fenetre instance() {
		return INSTANCE;
	}
	
	private Fenetre () {
		createLogClient ();
		fenetre = new JFrame ("Blind Test");
	}
	
	public void initFenetre () {
		fenetre.setSize(800, 700);
		fenetre.setMinimumSize(new Dimension (800, 700));
		fenetre.setContentPane(new ConnexionPanel());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
	}
	
	public void changePage (JPanel nouveau) {
		fenetre.setContentPane(nouveau);
		fenetre.validate();
	}
	
	private void createLogClient () {
		logClient = new LogClient ();
		UiInfoProvider infoProvider = new UiInfoProvider ("log/blind_test.log", logClient);
		InfoProviderManager.setUiInfoProvider(infoProvider);
	}
	
	public LogClient getLogClient () {
		return logClient;
	}
	
	public int getHeight () {
		return fenetre.getHeight();
	}
	
	public int getWidth () {
		return fenetre.getWidth();
	}
}
