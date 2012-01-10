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
		fenetre = new JFrame ("Blind Test");
		initFenetre ();
	}
	
	protected void initFenetre () {
		fenetre.setSize(800, 700);
		fenetre.setMinimumSize(new Dimension (500, 400));
		fenetre.setContentPane(new ConnexionPanel());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createLogClient ();
		fenetre.add(logClient.getInstance());
		fenetre.setVisible(true);
	}
	
	public void changePage (JPanel nouveau) {
		fenetre.setContentPane(nouveau);
		fenetre.add(logClient.getInstance());
		fenetre.validate();
	}
	
	private void createLogClient () {
		logClient = new LogClient ();
		UiInfoProvider infoProvider = new UiInfoProvider ("log/blind_test.log", logClient);
		InfoProviderManager.setUiInfoProvider(infoProvider);
	}
	
	public int getHeight () {
		return fenetre.getHeight();
	}
	
	public int getWidth () {
		return fenetre.getWidth();
	}
}
