package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.ReglesPanel;

/**
 * affiche les regles du jeu
 * @author francois
 *
 */
public class ReglesListener extends AbstractBoutonListener {

	public ReglesListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().changePage(new ReglesPanel ());
	}
}
