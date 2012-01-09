package org.client.ui;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.client.ui.listeners.RetourMenuListener;


public class ReglesPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ReglesPanel () {
		initPanel ();
	}
	
	@Override
	protected void initPanel () {
		setLayout(getLayout());
		
		//Présentation
		JLabel txtPstation = new JLabel ("*** Règles ***");
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		this.add(txtPstation, getContraintes());
		
		//Bouton Retour
		JButton boutonRetour = new JButton ("< Retour");
		boutonRetour.addMouseListener(new RetourMenuListener ());
		getContraintes().gridy = 1;
		this.add(boutonRetour, getContraintes());
		
	}
}
