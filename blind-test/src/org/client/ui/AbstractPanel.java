package org.client.ui;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.swing.JPanel;

import org.commons.entity.BanqueFacade;

public abstract class AbstractPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints contraintes = new GridBagConstraints();
	private Insets marges = new Insets(0, 0, 0, 0);
	
	protected abstract void initPanel ();
	
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
	public void paintComponent (Graphics g) {
		try {
			RenderedImage image = BanqueFacade.instance().readImage("image/fond.jpeg");
			g.drawImage((Image) image, 0, 0, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
