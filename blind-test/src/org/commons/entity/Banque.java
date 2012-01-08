package org.commons.entity;

import java.util.concurrent.atomic.AtomicInteger;

import org.commons.util.IWithId;
import org.commons.util.IWithName;
import org.commons.util.StringUtil;

public final class Banque implements Comparable<Banque>, IWithName, IWithId {

	private Integer _id;
	private String _answer;
	private String _name;
	private String _directory;
	private AtomicInteger _version;
	
	public Banque() {
		this(Integer.valueOf(-1), "", "", "", new AtomicInteger(0));
	}
	
	public Banque (final Integer parId, final String parAnswer, final String parName, final String parDirectory, final AtomicInteger parVersion) {
		_id = parId;
		_answer = parAnswer;
		_name = parName;
		_directory = parDirectory;
		_version = parVersion;
	}
	
	@Override
	public final Integer getId() {
		return _id;
	}
	
	public final void setId(final Integer parId) {
		_id = parId;
	}
	
	public final String getDirectory() {
		return _directory;
	}
	
	public final void setDirectory(final String parDirectory) {
		_directory = parDirectory;
	}
	
	public final String getAnswer() {
		return _answer;
	}
	
	public final void setAnswer(final String parAnswer) {
		_answer = parAnswer;
	}
	
	public final int getVersion() {
		return _version.get();
	}
	
	public final int incrementAndGetVersion() {
		return _version.incrementAndGet();
	}
	
	public final void setVersion(final AtomicInteger parVersion) {
		_version = parVersion;
	}
	
	public final void setVersion(final Integer parVersion) {
		_version = new AtomicInteger(parVersion.intValue());
	}

	@Override
	public final String getConstName() {
		return _name;
	}
	
	public final void setName(final String parName) {
		_name = parName;
	}
	
	@Override
	public final int hashCode() {
		final int locPrime = 31;
		int locResult = 1;
		locResult = locPrime * locResult
				+ ((_name == null) ? 0 : _name.hashCode());
		locResult = locPrime * locResult
				+ ((_version == null) ? 0 : _version.intValue());
		return locResult;
	}

	@Override
	public final boolean equals(final Object parObject) {
		if (this == parObject)
			return true;
		if (parObject == null)
			return false;
		if (!(parObject instanceof Banque))
			return false;
		final Banque locOther = (Banque) parObject;
		if(false == StringUtil.equals(_name, locOther._name)) {
			return false;
		}
		return _version.intValue() == locOther._version.intValue();
	}

	@Override
	public final int compareTo(final Banque parBanque) {
		if(parBanque == null) return 1;
		
		final int locCompareTo = StringUtil.compareTo(_name, parBanque._name);
		return (locCompareTo != 0) ? locCompareTo : _version.intValue() - parBanque._version.intValue();
	}
	
	@Override
	public final String toString() {
		return _name;
	}
}
