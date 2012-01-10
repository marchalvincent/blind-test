package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;

public class RetourConnexionListener extends AbstractBoutonListener {

	public RetourConnexionListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().changePage(new ConnexionPanel());
	}
}
