package org.client.ui.listeners;

import org.client.ui.BoutonGris;

public class StatListener extends AbstractBoutonListener {
	
	private String _login;
	
	public StatListener(String parLogin, BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
		_login = parLogin;
	}

}
