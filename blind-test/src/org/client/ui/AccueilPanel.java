package org.client.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Classe représentant le panel de l'accueil du jeu.
 * @author francois
 *
 */
public class AccueilPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccueilPanel () {
		initPanel ();
	}
	
	protected void initPanel () {
		JButton test = new JButton ("coucou");
		this.add(test);
	}
}
