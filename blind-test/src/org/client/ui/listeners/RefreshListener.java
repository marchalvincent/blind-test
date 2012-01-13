package org.client.ui.listeners;

import org.client.ui.BoutonGris;

public class RefreshListener extends AbstractBoutonListener {
	
	@SuppressWarnings("unused")
	private String login;
	
	public RefreshListener(String login, BoutonGris bouton) {
		super(bouton);
		this.login = login;
	}
}
