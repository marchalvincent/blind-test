package org.client.ui.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public final class NoneDisplayImage implements DisplayImage {

	protected NoneDisplayImage() {
		super();
	}
	
	@Override
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, 
			final int parWidth, final int parHeight) {
		
		parGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
		parGraphics.setColor(Color.WHITE);
	}

}
