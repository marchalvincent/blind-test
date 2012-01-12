package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JList;
import javax.swing.JOptionPane;

import org.client.main.PartieClientLauncher;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;

public class RejoindreListener extends AbstractBoutonListener {
	
	private JList _list;
	private String _login;
	private PartieClientLauncher partieClientLauncher;
	
	public RejoindreListener(String login, JList list, BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
		_login = login;
		_list = list;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Object nomPartie = _list.getSelectedValue();
		if (nomPartie == null) {
			JOptionPane.showMessageDialog(null, "Vous n'avez pas sélectionné de partie !", "Attention !", JOptionPane.WARNING_MESSAGE);
			return;
		}
		JouerPanel jp = new JouerPanel ().initPanel();
		Fenetre.instance().changePage(jp);
		partieClientLauncher = new PartieClientLauncher(jp, _login, nomPartie.toString());
		partieClientLauncher.startPartieClient();
	}
}
