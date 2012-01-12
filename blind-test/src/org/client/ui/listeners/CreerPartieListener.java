package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import org.client.main.PartieClientLauncher;
import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;

public class CreerPartieListener extends AbstractBoutonListener {
	
	private String _login;
	private AccueilPanel _panel;
	private String _nomPartie;
	private PartieClientLauncher partieClientLauncher;
	
	public CreerPartieListener(AccueilPanel panel, String Login, BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
		_login = Login;
		_panel = panel;
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		_nomPartie = JOptionPane.showInputDialog(_panel, "Entrez le nom de la partie : ", "Créer une partie", JOptionPane.INFORMATION_MESSAGE);
		JouerPanel jp = new JouerPanel ().initPanel();
		Fenetre.instance().changePage(jp);
		partieClientLauncher = new PartieClientLauncher(jp, _login, _nomPartie);
		partieClientLauncher.startPartieClient();
	}
}
