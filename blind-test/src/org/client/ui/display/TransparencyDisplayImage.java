package org.client.ui.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public final class TransparencyDisplayImage implements DisplayImage {

	protected TransparencyDisplayImage() {
		super();
	}

	@Override
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, final int parWidth, final int parHeight) {
		for(int i = 0 ; i <= 10 ; ++i) {
			final Graphics2D par2DGraphics = (Graphics2D) parGraphics;
			par2DGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_OVER, (float) (i / 10)));
			par2DGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
			try {
				Thread.sleep(220L);
			} catch (InterruptedException e) {
			}
		}
	}
}
