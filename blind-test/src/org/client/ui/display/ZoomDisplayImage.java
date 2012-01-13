package org.client.ui.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.DisplayConfigurationType;

public final class ZoomDisplayImage implements DisplayImage {

	final private Configuration _configuration;
	private double scale = 0;
	protected ZoomDisplayImage() {
		super();
		_configuration = ConfigurationManager.getConfiguration();
	}

	@Override
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, 		
		final int parWidth, final int parHeight, final int parCurrentRepeat) {
		final Graphics2D par2DGraphics = (Graphics2D) parGraphics;
		float locRepeat = getRepeat();
		par2DGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		par2DGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		par2DGraphics.translate(0, 0);
		for (int i=parCurrentRepeat; i<locRepeat;i++) {
			scale += 0.01;
			par2DGraphics.scale(scale,scale);
		}
		if (scale > 1){
			scale = 1;
		}
		par2DGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
	}

	@Override
	public final int getRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.ZOOM);
		return (locType == null) ? 1 : locType.getRepeat().intValue();
	}

	@Override
	public final long getTimeRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.ZOOM);
		return (locType == null) ? 0L : locType.getTime().longValue();
	}

}