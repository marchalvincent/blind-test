package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;

/**
 * retour au menu d'acceuil
 * @author francois
 *
 */
public class RetourMenuListener extends AbstractBoutonListener {

	final private String _login;
	
	public RetourMenuListener(final String parLogin, final BoutonGris bouton) {
		super(bouton);
		
		_login = parLogin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().changePage(new AccueilPanel (_login).initPanel());
	}

}
