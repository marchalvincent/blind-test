package org.client.ui.display;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.commons.util.IWithId;
import org.commons.util.IWithName;

public enum EnumDisplayImage implements IWithId, IWithName {

	NONE(Integer.valueOf(0), "display_none", new NoneDisplayImage()),
	TRANSPARENCY(Integer.valueOf(1), "display_transparency", new TransparencyDisplayImage());
	
	final private Integer _id;
	final private String _name;
	final private DisplayImage _display;
	
	private EnumDisplayImage(final Integer parId, final String parName, final DisplayImage parDisplay) {
		_id = parId;
		_name = parName;
		_display = parDisplay;
	}
	
	public final void displayImage(final Graphics parGraphics, final BufferedImage parImage, 
			final int parWidth, final int parHeight) {
		_display.displayImage(parGraphics, parImage, parWidth, parHeight);
	}
	
	final public Integer getId() {
		return _id;
	}

	@Override
	public final String getConstName() {
		return _name;
	}
	
}
