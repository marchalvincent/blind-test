package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.client.ui.Fenetre;
import org.client.ui.ReglesPanel;

/**
 * affiche les regles du jeu
 * @author francois
 *
 */
public class ReglesListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		Fenetre.changePage(new ReglesPanel ());
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
