package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.ProposFinalPanel;
	/**
	 * affiche les infos du jeu
	 * 
	 *
	 */
	public class ProposListener extends AbstractBoutonListener {

		final private String _login;
		
		public ProposListener(final String parLogin, BoutonGris bouton) {
			super(bouton);
			
			_login = parLogin;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);
			Fenetre.instance().changePage(new ProposFinalPanel (_login).initPanel());
		}
	}
