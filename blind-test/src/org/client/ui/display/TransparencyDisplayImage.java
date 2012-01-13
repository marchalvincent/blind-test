package org.client.ui.display;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.DisplayConfigurationType;

public final class TransparencyDisplayImage implements DisplayImage {

	final private Configuration _configuration;
	
	protected TransparencyDisplayImage() {
		super();
		
		_configuration = ConfigurationManager.getConfiguration();
	}

	@Override
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, 
			final int parWidth, final int parHeight, final int parCurrentRepeat) {
		final Graphics2D par2DGraphics = (Graphics2D) parGraphics;
		float locRepeat = getRepeat();
		float locCurrentRepeat = parCurrentRepeat;
		par2DGraphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (locCurrentRepeat / locRepeat)));
		par2DGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
		try {
			Thread.sleep(getTimeRepeat());
		} catch (InterruptedException e) {
		}
	}

	@Override
	public final int getRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.TRANSPARENCY);
		return (locType == null) ? 1 : locType.getRepeat().intValue();
	}

	@Override
	public final long getTimeRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.TRANSPARENCY);
		return (locType == null) ? 0L : locType.getTime().longValue();
	}
}
