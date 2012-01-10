package org.client.ui;

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;

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
				
		//Bouton Quitter
		JButton boutonQuitter = new BoutonGris ("Quitter");
		boutonQuitter.addMouseListener(new QuitterListener ());
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.6;
		getContraintes().weightx = 0.5;
		getContraintes().gridwidth = GridBagConstraints.RELATIVE;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_START;
		getContraintes().insets = getMarges();
		getMarges().set(30, 15, 0, 0);
		this.add(boutonQuitter, getContraintes());
		
		//Bouton Deconnexion
		JButton boutonDeconnexion = new BoutonGris ("Déconnexion");
		boutonDeconnexion.addMouseListener(new DeconnexionListener ());
		getContraintes().gridx = 1;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.6;
		getContraintes().weightx = 0.5;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_END;
		getMarges().set(30, 0, 0, 15);
		this.add(boutonDeconnexion, getContraintes());
		
		//Bouton Jouer
		JButton boutonJouer = new BoutonGris ("Jouer !");
		boutonJouer.addMouseListener(new JouerListener ());
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().weighty = 0.1;
		getContraintes().weightx = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.PAGE_END;
		this.add(boutonJouer, getContraintes());
		
		//Bouton Règles
		JButton boutonRegles = new BoutonGris ("Règles");
		boutonRegles.addMouseListener(new ReglesListener ());
		getContraintes().gridx = 0;
		getContraintes().gridy = 2;
		getContraintes().weighty = 0.1;
		getContraintes().weightx = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.PAGE_START;
		this.add(boutonRegles, getContraintes());

		//Un espace pour ne pas que les composants ne soient tout en bas
		JLabel espace = new JLabel (" ");
		getContraintes().gridx = 0;
		getContraintes().gridy = 3;
		getContraintes().weighty = 0.4;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		this.add(espace, getContraintes());
	}
}
