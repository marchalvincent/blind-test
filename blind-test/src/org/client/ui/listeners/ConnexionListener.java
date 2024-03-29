package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import org.client.main.ClientManager;
import org.client.main.ThreadConnexion;
import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;
import org.commons.logger.InfoProviderManager;
import org.commons.security.Encryptor;
import org.commons.security.MD5Encryptor;
import org.commons.util.StringUtil;

/**
 * renvoit sur la page d'accueil
 *
 */
public class ConnexionListener extends AbstractBoutonListener {

	private ConnexionPanel panel;

	public ConnexionListener(BoutonGris bouton, ConnexionPanel panel) {
		super(bouton);
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked (e);
		final String locLogin = panel.getLogin();
		ClientManager.setLogin(locLogin);

		String locPassword = panel.getPassword();
		if (StringUtil.isNotEmpty(locPassword) && StringUtil.isNotEmpty(locLogin)) {
			final Encryptor encript = new MD5Encryptor(InfoProviderManager.getUiInfoProvider());
			locPassword = encript.encrypt(locPassword);
			ThreadConnexion tc = new ThreadConnexion(locLogin, locPassword);
			if (tc.call()) {
				Fenetre.instance().changePage(new AccueilPanel (locLogin).initPanel());
			}
		}
		else {
			JOptionPane.showMessageDialog(panel, "Veuillez remplir tous les champs !", "Attention !", JOptionPane.WARNING_MESSAGE);
		}
	}
}
