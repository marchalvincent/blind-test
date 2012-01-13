package org.client.ui.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.DisplayConfigurationType;
import org.commons.configuration.EnumConfiguration;
import org.commons.util.IWithId;
import org.commons.util.IWithName;

public enum EnumDisplayImage implements IWithId, IWithName, DisplayImage {

	NONE(Integer.valueOf(0), EnumConfiguration.DISPLAY_NONE.getConstName(), new NoneDisplayImage()),
	TRANSPARENCY(Integer.valueOf(1), EnumConfiguration.DISPLAY_TRANSPARENCY.getConstName(), new TransparencyDisplayImage()),
	BANDE(Integer.valueOf(2), EnumConfiguration.DISPLAY_BANDE.getConstName(), new BandeDisplayImage()),
	SCALE(Integer.valueOf(3), EnumConfiguration.DISPLAY_SCALE.getConstName(), new ScaleDisplayImage());
	
	final private Integer _id;
	final private String _name;
	final private DisplayImage _display;
	
	private EnumDisplayImage(final Integer parId, final String parName, final DisplayImage parDisplay) {
		_id = parId;
		_name = parName;
		_display = parDisplay;
	}
	
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, 
			final int parWidth, final int parHeight, final int parCurrentRepeat) {
		_display.displayImage(parGraphics, parImage, parWidth, parHeight, parCurrentRepeat);
	}
	
	public final long getTimeRepeat() {
		return _display.getTimeRepeat();
	}
	
	final public int getRepeat() {
		return _display.getRepeat();
	}
	
	@Override
	final public Integer getId() {
		return _id;
	}

	@Override
	public final String getConstName() {
		return _name;
	}
	
	public final boolean isEnabled() {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final DisplayConfigurationType locType = locConfiguration.getDisplayType(this);
		return (locType != null) ? locType.isEnabled() : false;
	}
	
	static public final EnumDisplayImage randomDisplay() {
		final List<EnumDisplayImage> locArray = getValues();
		if(locArray.isEmpty()) {
			return EnumDisplayImage.NONE;
		}
		final int locSize = (int) (Math.random() * (locArray.size()));
		return locArray.get(locSize);
	}
	
	static private final List<EnumDisplayImage> getValues() {
		final EnumDisplayImage[] locDisplayValues = values();
		final List<EnumDisplayImage> locList = new ArrayList<EnumDisplayImage>(locDisplayValues.length);
		for(final EnumDisplayImage locDisplay : locDisplayValues) {
			if(locDisplay.isEnabled()) {
				locList.add(locDisplay);
			}
		}
		return locList;
	}
	
}
