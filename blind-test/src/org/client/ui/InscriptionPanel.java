package org.client.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.client.ui.listeners.CreerCompteListener;
import org.client.ui.listeners.QuitterListener;

/**
 * panel d'inscription
 * @author francois
 *
 */
public class InscriptionPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField champsNom;
	private JTextField champsLogin;
	private JPasswordField champsMdp;
	
	public InscriptionPanel () {
		super ();
	}
	
	@Override
	protected void initPanel() {
		getMarges().set(0, 15, 15, 15);
		
		//Label Login
		JLabel txtLogin = new JLabel ("Login");
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().weighty = 0.01;
		getContraintes().weightx = (double) 0.5;
		getContraintes().anchor = GridBagConstraints.LINE_END;
		getContraintes().insets = getMarges();
		txtLogin.setForeground(Color.WHITE);
		this.add(txtLogin, getContraintes());
		
		//Champs Login
		champsLogin = new JTextField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.LINE_START;
		champsLogin.setMinimumSize(champsLogin.getPreferredSize());
		this.add(champsLogin, getContraintes());
		
		//Label Mot de passe
		JLabel txtMdp = new JLabel ("Mot de passe");
		getContraintes().gridx = 0;
		getContraintes().gridy = 2;
		getContraintes().gridwidth = 1;
		getContraintes().anchor = GridBagConstraints.LINE_END;
		txtMdp.setForeground(Color.WHITE);
		this.add(txtMdp, getContraintes());
		
		//Champs Mot de passe
		champsMdp = new JPasswordField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 2;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.LINE_START;
		champsMdp.setMinimumSize(champsMdp.getPreferredSize());
		this.add(champsMdp, getContraintes());
		
		//Label Nom
		JLabel txtNom = new JLabel ("Nom");
		getContraintes().gridx = 0;
		getContraintes().gridy = 3;
		getContraintes().gridwidth = 1;
		getContraintes().anchor = GridBagConstraints.LINE_END;
		txtNom.setForeground(Color.WHITE);
		this.add(txtNom, getContraintes());

		//Champs Nom
		champsNom = new JTextField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 3;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.LINE_START;
		champsNom.setMinimumSize(champsNom.getPreferredSize());
		this.add(champsNom, getContraintes());
		
		//Bouton Inscription
		BoutonGris boutonInscription = new BoutonGris ("Inscription");
		boutonInscription.addMouseListener(new CreerCompteListener (boutonInscription, this));
		getContraintes().gridx = 0;
		getContraintes().gridy = 4;
		getContraintes().weighty = 0.01;
		getContraintes().gridwidth = 1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.PAGE_START;
		this.add(boutonInscription, getContraintes());
		
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
	
	public String getLogin () {
		return champsLogin.getText();
	}
	
	public String getPassword () {
		return new String(champsMdp.getPassword());
	}
	
	public String getNom () {
		return champsNom.getText();
	}
}
