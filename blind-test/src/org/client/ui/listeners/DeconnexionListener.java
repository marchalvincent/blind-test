package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;

/**
 * deconnexion
 * @author francois
 *
 */
public class DeconnexionListener extends AbstractBoutonListener {
	
	public DeconnexionListener(BoutonGris bouton) {
		super(bouton);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().getPartieWindow().dispose();
		Fenetre.instance().changePage(new ConnexionPanel().initPanel());
	}
}
