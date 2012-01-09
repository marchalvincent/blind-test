package org.client.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Classe contenant un singleton de la fenetre de
 * l'application.
 * @author francois
 *
 */
public class Fenetre extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JFrame fenetre;
	
	public Fenetre () {
		fenetre = new JFrame ("Blind Test");
		initFenetre ();
	}
	
	protected void initFenetre () {
		fenetre.setSize(800, 600);
		fenetre.setContentPane(new ConnexionPanel());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
	}
	
	public static void changePage (JPanel nouveau) {
		fenetre.setContentPane(nouveau);
		fenetre.validate();
	}
}
