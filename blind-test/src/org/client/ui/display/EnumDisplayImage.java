package org.client.ui.display;

import org.commons.util.IWithId;
import org.commons.util.IWithName;

public enum EnumDisplayImage implements IWithId, IWithName {

	NONE(Integer.valueOf(0), "display_none");
	
	private final Integer _id;
	final private String _name;
	
	private EnumDisplayImage(final Integer parId, final String parName) {
		_id = parId;
		_name = parName;
	}
	
	final public Integer getId() {
		return _id;
	}

	@Override
	public final String getConstName() {
		return _name;
	}
	
}
