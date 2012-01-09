package org.client.ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

public class BoutonGris extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BoutonGris (String texte) {
		super (texte);
		setBorderPainted(false);
		this.setForeground(Color.WHITE);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
		
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2d.drawString(getText(), getWidth()/2 - getWidth()/3, getHeight()/2 + getHeight()/5);
	}
}
