package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * quitte l'appli
 * @author francois
 *
 */
public class QuitterListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {
		System.exit (1);
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
