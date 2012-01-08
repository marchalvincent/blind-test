package org.server.persistence;

import org.commons.util.IWithName;

public enum EnumDatabase implements IWithName {

	URL("url"),
	DRIVER("driver"),
	LOGIN("login"),
	PASSWORD("password");
	
	final private String _name;
	
	private EnumDatabase(final String parName) {
		_name = parName;
	}
	
	@Override
	final public String getConstName() {
		return _name;
	}
	
	@Override
	final public String toString() {
		return getConstName();
	}
}
