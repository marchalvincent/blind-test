package org.client.ui.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.DisplayConfigurationType;

public final class NoneDisplayImage implements DisplayImage {

	final private Configuration _configuration;
	
	protected NoneDisplayImage() {
		super();
		
		_configuration = ConfigurationManager.getConfiguration();
	}
	
	@Override
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, 
			final int parWidth, final int parHeight, final int parCurrentRepeat) {
		
		parGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
		parGraphics.setColor(Color.WHITE);
	}

	@Override
	public final int getRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.NONE);
		return (locType == null) ? 1 : locType.getRepeat().intValue();
	}

	@Override
	public final long getTimeRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.NONE);
		return (locType == null) ? 0L : locType.getTime().longValue();
	}
}
