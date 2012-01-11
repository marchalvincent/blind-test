package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.ReglesPanel;

/**
 * affiche les regles du jeu
 * @author francois
 *
 */
public class ReglesListener extends AbstractBoutonListener {

	final private String _login;
	
	public ReglesListener(final String parLogin, BoutonGris bouton) {
		super(bouton);
		
		_login = parLogin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().changePage(new ReglesPanel (_login).initPanel());
	}
}
