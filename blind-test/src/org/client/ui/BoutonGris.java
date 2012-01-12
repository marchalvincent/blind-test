package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import org.client.ui.listeners.BoutonState;

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
	private Color backgroundOff = new Color (131, 131, 131);
	private Color backgroundOn = new Color (189, 189, 189);
	private Color backgroundClick = new Color (82, 82, 82);
	private Color currentBackgroundColor = backgroundOff;
	private Color foreground = new Color (255, 255, 255);
	
	public BoutonGris (String texte) {
		super (texte);
		setBorderPainted(false);
		this.setForeground(Color.WHITE);
		this.setIgnoreRepaint(true);
	}
	
	@Override
	public void paintComponent (Graphics g) {
		//TODO : Merde lors du repaint a cause de transparence
			Graphics2D g2d = (Graphics2D) g;
			
			g2d.setPaint (currentBackgroundColor);
			g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
			
			g2d.setPaint (foreground);
			g2d.drawString(getText(), getWidth()/2 - getWidth()/2 + (getWidth()/2 - getText().length())/3, getHeight()/2 + getHeight()/5);
			g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
	}
	
	public void setBackgroundColor (BoutonState etat) {
		switch (etat) {
		case Off :
			currentBackgroundColor = backgroundOff;
			break;
		case On :
			currentBackgroundColor = backgroundOn;
			break;
		case Click :
			currentBackgroundColor = backgroundClick;
			break;
		default :
			currentBackgroundColor = backgroundOff;
			break;
		}
	}
}
