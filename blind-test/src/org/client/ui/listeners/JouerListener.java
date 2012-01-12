package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.main.PartieClientLauncher;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;

/**
 * Affiche le jeu
 * @author francois
 *
 */
public class JouerListener extends AbstractBoutonListener {
	
	private PartieClientLauncher partieClientLauncher;
	private final String _login;
	
	public JouerListener(final String parLogin, BoutonGris bouton) {
		super(bouton);
		
		_login = parLogin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		JouerPanel jp = new JouerPanel (_login).initPanel();
		Fenetre.instance().changePage(jp);
		partieClientLauncher = new PartieClientLauncher(jp, _login, "");
		partieClientLauncher.startPartieClient();
	}
}
