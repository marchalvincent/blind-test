package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.main.PartieClientLauncher;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;

public class RejoindreListener extends AbstractBoutonListener {
	
	private String _nomPartie;
	private String _login;
	private PartieClientLauncher partieClientLauncher;
	
	public RejoindreListener(String login, String nomPartie, BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
		_login = login;
		_nomPartie = nomPartie;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		JouerPanel jp = new JouerPanel ().initPanel();
		Fenetre.instance().changePage(jp);
		partieClientLauncher = new PartieClientLauncher(jp, _login, _nomPartie);
		partieClientLauncher.startPartieClient();
	}
}
