package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.main.ThreadInscription;
import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;
import org.client.ui.InscriptionPanel;

/**
 * creation d'un compte lors de l'inscription
 * @author francois
 *
 */
public class CreerCompteListener extends AbstractBoutonListener {
	
	InscriptionPanel panel;
	
	public CreerCompteListener(BoutonGris bouton, InscriptionPanel panel) {
		super(bouton);
		// TODO Auto-generated constructor stub
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		ThreadInscription ti = new ThreadInscription(panel.getLogin(), panel.getPassword(), panel.getNom());
		if (ti.call()) {
			Fenetre.instance().changePage(new ConnexionPanel ());
		}
	}
}
