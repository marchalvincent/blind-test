package org.client.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.client.ui.listeners.ConnexionListener;
import org.client.ui.listeners.InscriptionListener;
import org.client.ui.listeners.QuitterListener;

/**
 * Classe representant le panel de connexion
 * @author francois
 *
 */
public class ConnexionPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ConnexionPanel () {
		super ();
	}
	
	@Override
	protected void initPanel () {
		getMarges().set(0, 15, 15, 15);
		
		//Label Login
		JLabel txtLogin = new JLabel ("Login");
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().weighty = 0.1;
		getContraintes().weightx = (double) 0.5;
		getContraintes().anchor = GridBagConstraints.LAST_LINE_END;
		getContraintes().insets = getMarges();
		txtLogin.setForeground(Color.WHITE);
		this.add(txtLogin, getContraintes());
		
		//Champs Login
		JTextField champsLogin = new JTextField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 1;
		getContraintes().weighty = 0.1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.LAST_LINE_START;
		this.add(champsLogin, getContraintes());
		
		//Label Mot de passe
		JLabel txtMdp = new JLabel ("Mot de passe");
		getContraintes().gridx = 0;
		getContraintes().gridy = 2;
		getContraintes().weighty = 0.1;
		getContraintes().gridwidth = 1;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_END;
		txtMdp.setForeground(Color.WHITE);
		this.add(txtMdp, getContraintes());
		
		//Champs Mot de passe
		JPasswordField champsMdp = new JPasswordField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 2;
		getContraintes().weighty = 0.1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(champsMdp, getContraintes());
		
		//Bouton Inscription
		BoutonGris boutonInscription = new BoutonGris ("S'inscrire");
		boutonInscription.addMouseListener(new InscriptionListener (boutonInscription));
		getContraintes().gridx = 0;
		getContraintes().gridy = 3;
		getContraintes().weighty = 0.01;
		getContraintes().gridwidth = 1;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_END;
		this.add(boutonInscription, getContraintes());
		
		//Bouton Connexion
		BoutonGris boutonConnexion = new BoutonGris ("Connexion");
		boutonConnexion.addMouseListener(new ConnexionListener (boutonConnexion));
		getContraintes().gridx = 1;
		getContraintes().gridy = 3;
		getContraintes().weighty = 0.01;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_START;
		this.add(boutonConnexion, getContraintes());
		
		//Bouton Quitter
		BoutonGris boutonQuitter = new BoutonGris ("Quitter");
		boutonQuitter.addMouseListener(new QuitterListener (boutonQuitter));
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.6;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.FIRST_LINE_START;
		getMarges().top = 30;
		this.add(boutonQuitter, getContraintes());
		
		//Un espace pour ne pas que les composants ne soient tout en bas
		JLabel espace = new JLabel (" ");
		getContraintes().gridx = 0;
		getContraintes().gridy = 4;
		getContraintes().weighty = 0.4;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		this.add(espace, getContraintes());
	}
}
