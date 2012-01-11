package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.InscriptionPanel;

/**
 * lien vers la page d'inscription
 * @author francois
 *
 */
public class InscriptionListener extends AbstractBoutonListener {
	
	public InscriptionListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().changePage(new InscriptionPanel().initPanel());
	}
}
