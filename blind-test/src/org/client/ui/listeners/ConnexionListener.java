package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;

/**
 * renvoit sur la page d'accueil
 * @author francois
 *
 */
public class ConnexionListener extends AbstractBoutonListener {
	
	public ConnexionListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked (e);
		Fenetre.instance().changePage(new AccueilPanel ());
	}
}
