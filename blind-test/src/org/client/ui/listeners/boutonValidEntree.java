package org.client.ui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 * 
 * @author francois
 *
 */
public class boutonValidEntree implements KeyListener {
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			//TODO : envoyer reponse jeu
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
