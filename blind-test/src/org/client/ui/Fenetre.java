package org.client.ui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Classe contenant un singleton de la fenetre de
 * l'application.
 * @author francois
 *
 */
public final class Fenetre extends JFrame {

	static private final Fenetre INSTANCE = new Fenetre();
	
	static public final Fenetre instance() {
		return INSTANCE;
	}
	
	private static final long serialVersionUID = 1L;
	private JFrame fenetre;
	
	public Fenetre () {
		fenetre = new JFrame ("Blind Test");
		initFenetre ();
	}
	
	protected void initFenetre () {
		fenetre.setSize(800, 600);
		fenetre.setMinimumSize(new Dimension (500, 400));
		fenetre.setContentPane(new ConnexionPanel());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
	}
	
	public void changePage (JPanel nouveau) {
		fenetre.setContentPane(nouveau);
		fenetre.validate();
	}
}
