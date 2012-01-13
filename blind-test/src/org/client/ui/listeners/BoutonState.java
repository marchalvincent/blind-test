package org.client.ui.listeners;

import java.awt.Color;

/**
 * decrit l'etat du bouton :
 * on pour souris au dessus
 * off de base
 * click pour bouton clické
 * @author francois
 *
 */
public enum BoutonState {
	Off(new Color (131, 131, 131)),
	On(new Color (189, 189, 189)),
	Click(new Color (82, 82, 82));
	
	private final Color _color;
	
	private BoutonState(final Color parColor) {
		_color = parColor;
	}
	
	final public Color getColor() {
		return _color;
	}
}
