package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Observable;
import java.util.Observer;

import org.client.ui.BoutonGris;

public abstract class AbstractBoutonListener extends Observable implements MouseListener {
	
	private BoutonGris bouton;
	protected Observer _observer;
	
	public AbstractBoutonListener (BoutonGris bouton) {
		this(bouton, null);
	}
	
	public AbstractBoutonListener (BoutonGris parButton, final Observer parObserver) {
		bouton = parButton;
		_observer = parObserver;
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
