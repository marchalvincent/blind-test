package org.client.ui.listeners;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import org.client.main.ThreadInscription;
import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;
import org.client.ui.InscriptionPanel;
import org.commons.logger.InfoProviderManager;
import org.commons.security.Encryptor;
import org.commons.security.MD5Encryptor;
import org.commons.util.StringUtil;

/**
 * creation d'un compte lors de l'inscription
 *
 */
public class CreerCompteListener extends AbstractBoutonListener {

	InscriptionPanel panel;

	public CreerCompteListener(BoutonGris bouton, InscriptionPanel panel) {
		super(bouton);
		this.panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		String locLogin = panel.getLogin();
		String locPassword = panel.getPassword();
		String locNom = panel.getNom();
		if (StringUtil.isNotEmpty(locLogin) && StringUtil.isNotEmpty(locNom) && StringUtil.isNotEmpty(locPassword)) {
			final Encryptor encript = new MD5Encryptor(InfoProviderManager.getUiInfoProvider());
			locPassword = encript.encrypt(locPassword);
			ThreadInscription ti = new ThreadInscription(locLogin, locPassword, locNom);
			if (ti.call()) {
				Fenetre.instance().changePage(new ConnexionPanel ().initPanel());
			}
		}
		else {
			JOptionPane.showMessageDialog(panel, "Veuillez remplir tous les champs !", "Attention !", JOptionPane.WARNING_MESSAGE);
		}
	}
}
