package org.client.ui.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Observable;
import java.util.Observer;

import org.client.ui.JouerPanel;


public class BoutonEntree extends Observable implements KeyListener {
	
	protected Observer _observer;
	public JouerPanel _panel;
	
	public BoutonEntree (JouerPanel parPanel, final Observer parObserver) {
		_observer = parObserver;
		_panel = parPanel;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			this.setChanged();
			this.notifyObservers(_panel.getAnswer());
			_panel.clearText();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
