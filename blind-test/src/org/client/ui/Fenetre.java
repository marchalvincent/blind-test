package org.client.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Timer;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.client.ui.parties.PartieTimer;
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
	private JFrame fenetreLog;
	private JFrame fenetreParties;
	private PartiesPanel _partiesPanel;
	private LogClient logClient;
	private PartieTimer _partieTimer;
	
	static public final Fenetre instance() {
		return INSTANCE;
	}
	
	private Fenetre () {
		createLogClient ();
		fenetre = new JFrame ("Blind Test");
		fenetreLog = new JFrame ("Historique");
	}
	
	public void initFenetre () {
		fenetre.setSize(800, 700);
		fenetre.setMinimumSize(new Dimension (800, 700));
		fenetre.setContentPane(new ConnexionPanel().initPanel());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		fenetreLog.add(logClient.getScrollPane());
		fenetreLog.getContentPane().setBackground(Color.BLACK);
		fenetreLog.setSize(500, 300);
		fenetreLog.setLocation(400, 100);
		fenetreLog.setVisible(true);
		fenetre.toFront();
	}
	
	public void changePage (JPanel nouveau) {
		fenetre.setContentPane(nouveau);
		fenetre.validate();
		fenetreLog.validate();
	}
	
	public void chargeListParties (final String parLogin) {
		fenetreParties = new JFrame ("Parties");
		_partiesPanel = new PartiesPanel(parLogin, Arrays.<String>asList("")).initPanel();
		fenetreParties.setContentPane(_partiesPanel);
		fenetreParties.setSize(300, 300);
		fenetreParties.setVisible(true);
		pt = new PartieTimer(parLogin, fenetreParties, _partiesPanel);
	}
	
	public JFrame getPartieWindow () {
		return fenetreParties;
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
