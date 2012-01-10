package org.client.ui;

import org.client.ui.listeners.RetourMenuListener;

public class JouerPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public JouerPanel () {
		super ();
	}
	
	@Override
	protected void initPanel() {
		// TODO Auto-generated method stub
		
		BoutonGris boutonRetour = new BoutonGris("< Retour");
		boutonRetour.addMouseListener(new RetourMenuListener(boutonRetour));
		this.add(boutonRetour);
	}

}
