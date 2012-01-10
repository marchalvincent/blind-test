package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;

/**
 * quitte l'appli
 * @author francois
 *
 */
public class QuitterListener extends AbstractBoutonListener {

	public QuitterListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked (e);
		System.exit (1);
	}
}
