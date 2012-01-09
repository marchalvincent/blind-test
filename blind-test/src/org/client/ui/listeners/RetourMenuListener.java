package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.client.ui.AccueilPanel;
import org.client.ui.Fenetre;

/**
 * retour au menu d'acceuil
 * @author francois
 *
 */
public class RetourMenuListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Fenetre.changePage(new AccueilPanel ());
	}

	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
