package org.client.ui.listeners;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;

public abstract class AbstractBoutonListener extends MouseAdapter {
	
	private BoutonGris bouton;
	
	public AbstractBoutonListener (BoutonGris bouton) {
		this.bouton = bouton;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		bouton.setBackgroundColor(BoutonState.Click);
	}

	@Override
	public  void mouseEntered(MouseEvent e) {
		bouton.setBackgroundColor(BoutonState.On);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		bouton.setBackgroundColor(BoutonState.Off);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		bouton.setBackgroundColor(BoutonState.Click);
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		bouton.setBackgroundColor(BoutonState.On);
	}
}
