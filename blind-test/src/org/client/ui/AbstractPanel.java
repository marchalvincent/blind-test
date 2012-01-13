package org.client.ui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.logging.Level;

import javax.swing.JPanel;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.entity.BanqueFacade;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;

/**
 * panel de base avec ses :
 * layout
 * contraintes
 * et marges
 * + ajout de log au depart
 *
 */
public abstract class AbstractPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints contraintes = new GridBagConstraints();
	private Insets marges = new Insets(0, 0, 0, 0);

	static private final Image BACKGROUND_IMAGE = getImage();

	protected AbstractPanel () {
		this.setLayout(layout);
	}

	public abstract AbstractPanel initPanel ();

	@Override
	public GridBagLayout getLayout () {
		return layout;
	}

	protected GridBagConstraints getContraintes () {
		return contraintes;
	}

	protected Insets getMarges () {
		return marges;
	}

	@Override
	public void paintComponent (final Graphics parGraphics) {
		super.paintComponents(parGraphics);
		if(BACKGROUND_IMAGE == null) return;
		
		final Fenetre locFenetre = Fenetre.instance();
		parGraphics.drawImage(BACKGROUND_IMAGE, 0, 0, locFenetre.getWidth(), locFenetre.getHeight(), null);
	}

	static private final Image getImage() {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final String locBackgroundImage = locConfiguration.getBackgroundImage();
		if(StringUtil.isEmpty(locBackgroundImage)) {
			return null;
		}
		try {
			return (Image) BanqueFacade.instance().readImage(locBackgroundImage);
		} catch (IOException e) {
			final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
			locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible de charger l'image %s. Fermeture de l'application."), e);
			SystemUtil.exit();
			// Dummy return vu qu'on kill l'appli
			return null;
		}
	}
}
