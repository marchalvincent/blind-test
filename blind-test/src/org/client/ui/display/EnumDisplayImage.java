package org.client.ui.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.commons.configuration.EnumConfiguration;
import org.commons.util.IWithId;
import org.commons.util.IWithName;

public enum EnumDisplayImage implements IWithId, IWithName, DisplayImage {

	NONE(Integer.valueOf(0), EnumConfiguration.DISPLAY_NONE.getConstName(), new NoneDisplayImage()),
	TRANSPARENCY(Integer.valueOf(1), EnumConfiguration.DISPLAY_TRANSPARENCY.getConstName(), new TransparencyDisplayImage());
	
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
	
	static public final EnumDisplayImage randomDisplay() {
		final EnumDisplayImage[] locArray = values();
		final int locSize = (int) (Math.random() * (locArray.length));
		return locArray[locSize];
	}
	
}
