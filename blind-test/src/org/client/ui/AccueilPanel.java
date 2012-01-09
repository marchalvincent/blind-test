package org.client.ui;

import javax.swing.JButton;

import org.client.ui.listeners.DeconnexionListener;
import org.client.ui.listeners.JouerListener;
import org.client.ui.listeners.QuitterListener;
import org.client.ui.listeners.ReglesListener;

/**
 * Classe représentant le panel de l'accueil du jeu.
 * @author francois
 *
 */
public class AccueilPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	
	public AccueilPanel () {
		initPanel ();
	}
	
	@Override
	protected void initPanel () {
		setLayout(getLayout());
		
		//Bouton Deconnexion
		JButton boutonDeconnexion = new JButton ("Déconnexion");
		boutonDeconnexion.addMouseListener(new DeconnexionListener ());
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getMarges().set(0, 15, 15, 15);
		getContraintes().insets = getMarges();
		//getContraintes().anchor = GridBagConstraints.LINE_END;
		this.add(boutonDeconnexion, getContraintes());
		
		//Bouton Jouer
		JButton boutonJouer = new JButton ("Jouer !");
		boutonJouer.addMouseListener(new JouerListener ());;
		getContraintes().gridy = 1;
		//getContraintes().anchor = GridBagConstraints.REMAINDER;
		this.add(boutonJouer, getContraintes());
		
		//Bouton Règles
		JButton boutonRegles = new JButton ("Règles");
		boutonRegles.addMouseListener(new ReglesListener ());
		getContraintes().gridy = 2;
		this.add(boutonRegles, getContraintes());
		
		//Bouton Quitter
		JButton boutonQuitter = new JButton ("Quitter");
		boutonQuitter.addMouseListener(new QuitterListener ());
		getContraintes().gridy = 3;
		this.add(boutonQuitter, getContraintes());
	}
}
