package org.server.partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.commons.util.IWithName;

public class Score implements Comparable<Score>, IWithName {

	private String _name;
	private int _value;
	
	public Score(final String parName, final int parValue) {
		_name = parName;
		_value = parValue;
	}
	
	public final int getValue() {
		return _value;
	}
	
	@Override
	public String getConstName() {
		return _name;
	}
	
	@Override
	public final int compareTo(final Score parScore) {
		return (parScore == null) ? 1 : _value - parScore._value;
	}
	
	// super crade, mais fallait que ça marche de suite...
	static public final List<Score> convert(final Map<String, Integer> parMap) {
		final List<Score> locList = new ArrayList<Score>(parMap.size());
		for(final Map.Entry<String, Integer> locEntry : parMap.entrySet()) {
			locList.add(new Score(locEntry.getKey(), locEntry.getValue()));
		}
		Collections.sort(locList);
		return locList;
	}
	
	public final String toString() {
		return _name + " : " + (_value);
	}
}
