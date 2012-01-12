package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;

public class RejoindreMenuListener extends AbstractBoutonListener {

	private String _login;
	
	public RejoindreMenuListener(String parLogin, BoutonGris bouton) {
		super(bouton);
		_login = parLogin;
	}

	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Fenetre.instance().chargeListParties(_login);
	}
}
