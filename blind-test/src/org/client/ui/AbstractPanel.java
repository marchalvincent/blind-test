package org.client.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.image.RenderedImage;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.commons.entity.BanqueFacade;

/**
 * panel de base avec ses :
 * layout
 * contraintes
 * et marges
 * + ajout de log au depart
 * @author francois
 *
 */
public abstract class AbstractPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints contraintes = new GridBagConstraints();
	private Insets marges = new Insets(0, 0, 0, 0);
	private JScrollPane scrollLog = new JScrollPane (Fenetre.instance().getLogClient().getInstance(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	protected AbstractPanel () {
		this.setLayout(layout);
		initPanel ();
		contraintes.gridx = 0;
		contraintes.gridy = 15;
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		contraintes.anchor = GridBagConstraints.PAGE_END;
		contraintes.fill = GridBagConstraints.BOTH;
		scrollLog.setOpaque(false);
		scrollLog.getViewport().setOpaque(false);
		scrollLog.setBorder(BorderFactory.createEmptyBorder());
		scrollLog.setPreferredSize(new Dimension(800, 50));
		this.add(scrollLog, contraintes);
	}
	
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
			g.drawImage((Image) image, 0, 0, Fenetre.instance().getWidth(), Fenetre.instance().getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
