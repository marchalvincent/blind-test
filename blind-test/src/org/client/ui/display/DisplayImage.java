package org.client.ui.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * L'interface de tous les objets capable d'afficher une image
 * @author pitton
 *
 */
public interface DisplayImage  {
	
	/**
	 * Affiche une image
	 * @param parGraphics {@link Graphics} le graphics
	 * @param parImage {@link BufferedImage} l'image à afficher
	 * @param parWidth {@code int} la largeur de l'image
	 * @param parHeight {@code int} la hauteur de l'image
	 */
	void displayImage(final Graphics parGraphics, final BufferedImage parImage, final int parWidth, final int parHeight);

}
