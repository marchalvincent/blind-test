package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.JouerPanel;
/**
 * 
 * @author francois
 *
 */
public class ValidListener extends AbstractBoutonListener {

	private JouerPanel _panel;
	
	public ValidListener(JouerPanel parPanel, BoutonGris bouton) {
		super(bouton);
		
		_panel = parPanel;
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		
		this.setChanged();
		this.notifyObservers(_panel.getAnswer());
	}
}
