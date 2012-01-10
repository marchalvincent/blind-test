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
	
	public JouerListener(BoutonGris bouton) {
		super(bouton);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		JouerPanel jp = new JouerPanel ();
		Fenetre.instance().changePage(jp);
		partieClientLauncher = new PartieClientLauncher(jp, "a");
		partieClientLauncher.startPartieClient();
	}
}
