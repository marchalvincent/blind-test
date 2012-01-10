package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import org.client.ui.BoutonGris;
/**
 * 
 * @author francois
 *
 */
public class ValidListener extends AbstractBoutonListener {

	public ValidListener(BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		//TODO : envoyer reponse jeu
	}

}
