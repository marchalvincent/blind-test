package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

/**
 * classe pour bouton personnalisé
 * @author francois
 *
 */
public class BoutonGris extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Color background = new Color (255, 255, 255, 80);
	private Color foreground = new Color (255, 255, 255, 180);
	private boolean repeint = true;
	
	public BoutonGris (String texte) {
		super (texte);
		setBorderPainted(false);
		this.setForeground(Color.WHITE);
		this.setIgnoreRepaint(true);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		//TODO : Merde lors du repaint a cause de transparence
		if (repeint) {
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setPaint (background);
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
			
			g2d.setPaint (foreground);
			g2d.drawString(getText(), getWidth()/2 - getWidth()/3, getHeight()/2 + getHeight()/5);
			g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
		}
	}
}
