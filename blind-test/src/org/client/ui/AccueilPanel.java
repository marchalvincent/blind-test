package org.client.ui;

import javax.swing.JButton;

/**
 * Classe représentant le panel de l'accueil du jeu.
 * @author francois
 *
 */
public class AccueilPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AccueilPanel () {
		initPanel ();
	}
	
	@Override
	protected void initPanel () {
		JButton test = new JButton ("coucou");
		this.add(test);
	}
}
