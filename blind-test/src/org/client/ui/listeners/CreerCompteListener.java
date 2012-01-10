package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;

/**
 * creation d'un compte lors de l'inscription
 * @author francois
 *
 */
public class CreerCompteListener extends AbstractBoutonListener {
	
	public CreerCompteListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().changePage(new ConnexionPanel ());
	}
}
