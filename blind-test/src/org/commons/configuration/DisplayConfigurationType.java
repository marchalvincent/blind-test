package org.commons.configuration;

import org.commons.util.IWithName;
import org.commons.util.StringUtil;

public final class DisplayConfigurationType implements IWithName {

	static private final String SEPARATOR = ",";
	
	private boolean _isEnabled;
	private String _name;
	private Integer _repeat;
	private Long _time;
	
	public DisplayConfigurationType(final String parName, final String parValue) {
		_name = parName;
		init(parValue);
	}
	
	private final void init(final String parValue) {
		final String[] locSplitted = parValue.split(SEPARATOR);
		_isEnabled = Boolean.parseBoolean(locSplitted[0].trim());
		_repeat = StringUtil.toInteger(locSplitted[1].trim());
		_time = Long.parseLong(locSplitted[2].trim());
	}
	
	public final String getConstName() {
		return _name;
	}

	public final boolean isEnabled() {
		return _isEnabled;
	}

	public final void setIsEnabled(boolean _isEnabled) {
		this._isEnabled = _isEnabled;
	}

	public final Integer getRepeat() {
		return _repeat;
	}

	public final void setRepeat(Integer _repeat) {
		this._repeat = _repeat;
	}

	public final Long getTime() {
		return _time;
	}

	public final void setTime(Long _time) {
		this._time = _time;
	}	
}
