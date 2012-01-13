package org.client.ui.display;

import org.commons.util.IWithId;

public abstract class DisplayImage implements IWithId {

	private final Integer _id;
	
	protected DisplayImage(final EnumDisplayImage parDisplay) {
		_id = parDisplay.getId();
	}
	
	public final Integer getId() {
		return _id;
	}

}
