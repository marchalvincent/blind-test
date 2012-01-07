package org.commons.configuration;

import java.nio.charset.Charset;
import java.util.logging.Level;

import org.commons.util.IWithName;

public enum EnumConfiguration implements IWithName{

	MIN_LEVEL("min_level", Level.WARNING),
	PORT("port", Integer.valueOf(9999)),
	HOSTNAME("hostname", "127.0.0.1"),
	LOGFILE("logfile", "conf/logging.properties"),
	CHARSET("charset", Charset.forName("utf-8"));
	
	final private String _name;
	final private Object _defaultValue;
	
	private EnumConfiguration(final String parConstName, final Object parDefaultValue) {
		_name = parConstName;
		_defaultValue = parDefaultValue;
	}
	
	@Override
	final public String getConstName() {
		return _name;
	}
	
	final public Object getDefaultValue() {
		return _defaultValue;
	}
	
	@Override
	final public String toString() {
		return getConstName();
	}
	
}
