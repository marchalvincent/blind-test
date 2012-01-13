package org.client.ui;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import org.client.ui.listeners.CreerPartieListener;
import org.client.ui.listeners.DeconnexionListener;
import org.client.ui.listeners.ProposListener;
import org.client.ui.listeners.ReglesListener;
import org.client.ui.listeners.RejoindreMenuListener;
import org.client.ui.listeners.StatListener;

/**
 * Classe représentant le panel de l'accueil du jeu.
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
	public AccueilPanel initPanel () {
		
		//Bouton Deconnexion
		BoutonGris boutonDeconnexion = new BoutonGris ("Déconnexion");
		boutonDeconnexion.addMouseListener(new DeconnexionListener (boutonDeconnexion, _login));
		getContraintes().gridx = 1;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.3;
		getContraintes().weightx = 0.5;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_END;
		getContraintes().insets = getMarges();
		getMarges().set(30, 0, 0, 15);
		this.add(boutonDeconnexion, getContraintes());

		BoutonGris boutonCreerPartie = new BoutonGris ("Créer une partie");
		boutonCreerPartie.addMouseListener(new CreerPartieListener (this, _login, boutonCreerPartie));
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().gridheight = 1;
		getContraintes().weighty = 0.1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().weightx = 0.8;
		getContraintes().anchor = GridBagConstraints.PAGE_END;
		this.add(boutonCreerPartie, getContraintes());
		
		//Bouton Jouer -> Rejoindre une partie
		BoutonGris boutonRejoindrePartie = new BoutonGris ("Rejoindre une partie");
		boutonRejoindrePartie.addMouseListener(new RejoindreMenuListener (_login, boutonRejoindrePartie));
		getContraintes().gridx = 0;
		getContraintes().gridy = 2;
		getContraintes().gridheight = 1;
		getContraintes().weighty = 0.1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().weightx = 0.8;
		getContraintes().anchor = GridBagConstraints.PAGE_END;
		this.add(boutonRejoindrePartie, getContraintes());

		//Bouton Stats
		BoutonGris boutonStat = new BoutonGris ("Statistiques");
		boutonStat.addMouseListener(new StatListener (_login, this, boutonStat));
		getContraintes().gridx = 0;
		getContraintes().gridy = 3;
		getContraintes().anchor = GridBagConstraints.CENTER;
		this.add(boutonStat, getContraintes());
		
		//Bouton Règles
		BoutonGris boutonRegles = new BoutonGris ("Règles");
		boutonRegles.addMouseListener(new ReglesListener (_login, boutonRegles));
		getContraintes().gridx = 0;
		getContraintes().gridy = 4;
		getContraintes().weighty = 0.1;
		getContraintes().anchor = GridBagConstraints.PAGE_START;
		this.add(boutonRegles, getContraintes());
		
		//Bouton A Propos
		BoutonGris boutonPropos = new BoutonGris ("A Propos");
		boutonPropos.addMouseListener(new ProposListener (_login, boutonPropos));
		getContraintes().gridx = 0;
		getContraintes().gridy = 5;
		getContraintes().weighty = 0.1;
		getContraintes().anchor = GridBagConstraints.PAGE_START;
		this.add(boutonPropos, getContraintes());
		
		//Un espace pour ne pas que les composants ne soient tout en bas
		JLabel espace = new JLabel (" ");
		getContraintes().gridx = 0;
		getContraintes().gridy = 6;
		getContraintes().weighty = 0.4;
		getContraintes().gridwidth = 1;
		this.add(espace, getContraintes());
		
		return this;
	}
}
