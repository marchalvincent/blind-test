package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.client.ui.Fenetre;
import org.client.ui.InscriptionPanel;

/**
 * lien vers la page d'inscription
 * @author francois
 *
 */
public class InscriptionListener implements MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Fenetre.changePage(new InscriptionPanel ());
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
