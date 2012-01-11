package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.main.ThreadConnexion;
import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;

/**
 * renvoit sur la page d'accueil
 * @author francois
 *
 */
public class ConnexionListener extends AbstractBoutonListener {
	
	private ConnexionPanel panel;
	
	public ConnexionListener(BoutonGris bouton, ConnexionPanel panel) {
		super(bouton);
		// TODO Auto-generated constructor stub
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked (e);
		final String locLogin = panel.getLogin();
		ThreadConnexion tc = new ThreadConnexion(locLogin, panel.getPassword());
		if (tc.call()) {
			Fenetre.instance().changePage(new AccueilPanel (locLogin).initPanel());
		}
	}
}
