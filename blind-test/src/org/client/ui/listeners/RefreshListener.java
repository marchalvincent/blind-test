package org.client.ui.listeners;

import org.client.ui.BoutonGris;

public class RefreshListener extends AbstractBoutonListener {
	
	@SuppressWarnings("unused")
	private String login;
	
	public RefreshListener(String login, BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
		this.login = login;
	}
}
