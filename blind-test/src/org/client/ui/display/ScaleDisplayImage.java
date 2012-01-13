package org.client.ui.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.DisplayConfigurationType;

public class ScaleDisplayImage implements DisplayImage {
	
	final private Configuration _configuration;
	
	protected ScaleDisplayImage () {
		super();
		_configuration = ConfigurationManager.getConfiguration();
	}
	
	@Override
	public void displayImage(Graphics parGraphics, BufferedImage parImage, int parWidth, int parHeight, int parCurrentRepeat) {
		final Graphics2D par2DGraphics = (Graphics2D) parGraphics;
		float locRepeat = getRepeat();
		float locCurrentRepeat = parCurrentRepeat;
		par2DGraphics.drawImage(parImage, 0, 0, parWidth, parHeight, null);
		par2DGraphics.setPaint(Color.BLACK);
		par2DGraphics.fillRect(0, 0, (int)((parWidth / 2) - ((parWidth / locRepeat) * locCurrentRepeat)), parHeight);
		par2DGraphics.fillRect((int)((parWidth / 2) + ((parWidth / locRepeat) * locCurrentRepeat)), 0, parWidth, parHeight);
		par2DGraphics.fillRect(0, 0, parWidth, (int)((parHeight / 2) - ((parHeight / locRepeat) * locCurrentRepeat)));
		par2DGraphics.fillRect(0, (int)((parHeight / 2) + ((parHeight / locRepeat) * locCurrentRepeat)), parWidth, parHeight);
		try {
			Thread.sleep(getTimeRepeat());
		} catch (InterruptedException e) {
		}
	}

	@Override
	public int getRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.BANDE);
		return (locType == null) ? 1 : locType.getRepeat().intValue();
	}

	@Override
	public long getTimeRepeat() {
		final DisplayConfigurationType locType  = _configuration.getDisplayType(EnumDisplayImage.BANDE);
		return (locType == null) ? 0L : locType.getTime().longValue();
	}

}
