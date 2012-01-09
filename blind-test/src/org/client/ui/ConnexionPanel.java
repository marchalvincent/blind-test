package org.client.ui;

import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.client.ui.listeners.ConnexionListener;

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
		getMarges().set(0, 15, 15, 15);
		initPanel ();
	}
	
	@Override
	protected void initPanel () {
		this.setLayout(getLayout());
		
		//Image de Présentation
		
		
		//Label Login
		JLabel txtLogin = new JLabel ("Login");
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().weightx = (double) 0.5;
		getContraintes().anchor = GridBagConstraints.LINE_END;
		getContraintes().insets = getMarges();
		this.add(txtLogin, getContraintes());
		
		//Champs Login
		JTextField champsLogin = new JTextField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 1;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.LINE_START;
		this.add(champsLogin, getContraintes());
		
		//Label Mot de passe
		JLabel txtMdp = new JLabel ("Mot de passe");
		getContraintes().gridx = 0;
		getContraintes().gridy = 2;
		getContraintes().gridwidth = 1;
		getContraintes().anchor = GridBagConstraints.LINE_END;
		this.add(txtMdp, getContraintes());
		
		//Champs Mot de passe
		JPasswordField champsMdp = new JPasswordField (15);
		getContraintes().gridx = 1;
		getContraintes().gridy = 2;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.LINE_START;
		this.add(champsMdp, getContraintes());
		
		//Bouton Connexion
		JButton boutConn = new JButton ("Connexion");
		boutConn.addMouseListener(new ConnexionListener ());
		getContraintes().gridx = 0;
		getContraintes().gridy = 3;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		getContraintes().anchor = GridBagConstraints.CENTER;
		this.add(boutConn, getContraintes());
	}
}
