package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;

import org.client.ui.listeners.BoutonState;


public class BoutonGris extends JButton {

	private static final long serialVersionUID = 1L;
	private Color currentBackgroundColor = BoutonState.Off.getColor();
	static private Color foreground = new Color (255, 255, 255);

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

	public void setBackgroundColor (final BoutonState parEtat) {
		currentBackgroundColor = parEtat.getColor();
	}
}
