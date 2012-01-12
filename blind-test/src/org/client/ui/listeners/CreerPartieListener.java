package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import org.client.main.PartieClientLauncher;
import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.client.ui.JouerPanel;
import org.commons.util.StringUtil;

public class CreerPartieListener extends AbstractBoutonListener {
	
	private String _login;
	private AccueilPanel _panel;
	private String _nomPartie;
	private PartieClientLauncher partieClientLauncher;
	private String[] listPossibl = {"10", "20", "30", "40", "Maximum"};
	private String nbImges;
	
	public CreerPartieListener(AccueilPanel panel, String Login, BoutonGris bouton) {
		super(bouton);
		// TODO Auto-generated constructor stub
		_login = Login;
		_panel = panel;
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		_nomPartie = JOptionPane.showInputDialog(_panel, "Entrez le nom de la partie : ", "Créer une partie", JOptionPane.QUESTION_MESSAGE);
		if (_nomPartie == null) {
			return;
		}
		if (StringUtil.isEmpty(_login)) {
			throw new NullPointerException("login null !!");
		}
		if (StringUtil.isNotEmpty(_nomPartie)) {
			nbImges = JOptionPane.showInputDialog(_panel, "Sélectionnez un nombre d'images\n(0 pour le maximum)", "Créer une partie", JOptionPane.QUESTION_MESSAGE);
			if (nbImges == null) {
				return;
			}
			else {
				Integer locResultat = StringUtil.toInteger(nbImges);
				if(locResultat == null || locResultat < 0) {
					locResultat = Integer.valueOf(10);
				}
				else if (locResultat == 0) {
					locResultat = Integer.valueOf(999);
				}
				JouerPanel jp = new JouerPanel (_login).initPanel();
				Fenetre.instance().changePage(jp);
				partieClientLauncher = new PartieClientLauncher(jp, _login, _nomPartie, locResultat);
				partieClientLauncher.startPartieClient();
			}
		}
		else {
			JOptionPane.showMessageDialog(_panel, "Vous n'avez pas donné de nom à votre partie !", "Attention !", JOptionPane.WARNING_MESSAGE);
		}
	}
}
