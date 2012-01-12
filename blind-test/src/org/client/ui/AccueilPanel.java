package org.client.ui;

import java.awt.GridBagConstraints;

import javax.swing.JLabel;

import org.client.ui.listeners.CreerPartieListener;
import org.client.ui.listeners.DeconnexionListener;
import org.client.ui.listeners.ProposListener;
import org.client.ui.listeners.QuitterListener;
import org.client.ui.listeners.ReglesListener;
import org.client.ui.listeners.RejoindreListener;
import org.client.ui.listeners.StatListener;

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
	public AccueilPanel initPanel () {
				
		//Bouton Quitter
		BoutonGris boutonQuitter = new BoutonGris ("Quitter");
		boutonQuitter.addMouseListener(new QuitterListener (boutonQuitter));
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.3;
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
		getContraintes().weighty = 0.3;
		getContraintes().weightx = 0.5;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_END;
		getMarges().set(30, 0, 0, 15);
		this.add(boutonDeconnexion, getContraintes());
		
		//Liste des parties
//		DefaultListModel<String> liste = new DefaultListModel<String>();
//		JList listParties = new JList (liste);
//		liste.addElement("Un");
//		liste.addElement("Deux");
//		liste.addElement("Trois");
//		getContraintes().gridx = 0;
//		getContraintes().gridy = 0;
//		getContraintes().gridwidth = 1;
//		getContraintes().gridheight = 3;
//		getContraintes().weightx = 0.2;
//		getContraintes().weighty = 1.0;
//		getContraintes().fill = GridBagConstraints.BOTH;
//		listParties.setOpaque(false);
//		listParties.setAutoscrolls(true);
//		listParties.setPreferredSize(new Dimension(50, 600));
//		listParties.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//		this.add(listParties, getContraintes());
		
		//Bouton Refresh Liste Parties
//		BoutonGris boutonRefresh = new BoutonGris ("Rafraichir");
//		boutonRefresh.addMouseListener(new RefreshListener (_login, boutonRefresh));
//		getContraintes().gridx = 0;
//		getContraintes().gridy = 3;
//		getContraintes().weighty = 0.01;
//		getContraintes().anchor = GridBagConstraints.PAGE_END;
//		getContraintes().fill = GridBagConstraints.NONE;
//		this.add(boutonRefresh, getContraintes());
		
		//TODO : refactorer jouer en creer une partie
		//Bouton Jouer -> Creer une partie
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
//		BoutonGris boutonRejoindrePartie = new BoutonGris ("Rejoindre une partie");
//		boutonRejoindrePartie.addMouseListener(new RejoindrePartieListener (this, _login, boutonRejoindrePartie));
//		getContraintes().gridx = 0;
//		getContraintes().gridy = 2;
//		getContraintes().gridheight = 1;
//		getContraintes().weighty = 0.1;
//		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
//		getContraintes().weightx = 0.8;
//		getContraintes().anchor = GridBagConstraints.PAGE_END;
//		this.add(boutonRejoindrePartie, getContraintes());

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
