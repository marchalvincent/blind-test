package org.client.ui;

import java.awt.GridBagConstraints;

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
	
	final private String _login;
	
	public AccueilPanel (final String parLogin) {
		super ();
		
		_login = parLogin;
	}
	
	@Override
	protected void initPanel () {
				
		//Bouton Quitter
		BoutonGris boutonQuitter = new BoutonGris ("Quitter");
		boutonQuitter.addMouseListener(new QuitterListener (boutonQuitter));
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
		BoutonGris boutonDeconnexion = new BoutonGris ("Déconnexion");
		boutonDeconnexion.addMouseListener(new DeconnexionListener (boutonDeconnexion));
		getContraintes().gridx = 1;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.6;
		getContraintes().weightx = 0.5;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_END;
		getMarges().set(30, 0, 0, 15);
		this.add(boutonDeconnexion, getContraintes());
		
		//Bouton Jouer
		BoutonGris boutonJouer = new BoutonGris ("Jouer !");
		boutonJouer.addMouseListener(new JouerListener (_login, boutonJouer));
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().weighty = 0.1;
		getContraintes().weightx = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.PAGE_END;
		this.add(boutonJouer, getContraintes());
		
		//Bouton Règles
		BoutonGris boutonRegles = new BoutonGris ("Règles");
		boutonRegles.addMouseListener(new ReglesListener (_login, boutonRegles));
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
