package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.main.PartieClientLauncher;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;

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
