package org.commons.configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;
import org.commons.util.StringUtil;

public final class DefaultConfiguration implements Configuration {

	static private final String DEFAULT_FILE_NAME = "conf/configuration.properties";
	
	private Level _minLevel;
	private String _hostName;
	private Charset _charset;
	private Integer _port;
	private Properties _properties;
	
	protected DefaultConfiguration() {}
	
	@Override
	public final String getFileName() {
		return DEFAULT_FILE_NAME;
	}

	@Override
	public final Level getMinLevel() {
		return _minLevel;
	}

	@Override
	public final Charset getCharset() {
		return _charset;
	}

	@Override
	public final String getCharsetName() {
		return _charset.name();
	}

	@Override
	public final String getHostName() {
		return _hostName;
	}

	@Override
	public final Integer getPort() {
		return _port;
	}

	@Override
	public final void setPort(final String parPort) {
		this._port=resolvePort(parPort);
		_properties.put(EnumConfiguration.PORT.getConstName(), _port.toString());
	}

	@Override
	public final void setHostName(final String parHostName) {
		_hostName = resolveHostname(parHostName);
		_properties.put(EnumConfiguration.HOSTNAME.getConstName(), _hostName);
	}

	@Override
	public final void setCharset(final String parCharset) {
		this._charset = resolveCharset(parCharset);
		_properties.put(EnumConfiguration.CHARSET.getConstName(), getCharsetName());
	}

	@Override
	public final void setMinLevel(final String parLevel) {
		_minLevel = resolveLevel(parLevel);
		_properties.put(EnumConfiguration.MIN_LEVEL.getConstName(), _minLevel.getName());
	}

	@Override
	public final Configuration load() throws BlindTestException {
		_properties = new Properties();
		try {
			_properties.load(new FileInputStream(DEFAULT_FILE_NAME));
		} catch (IOException locException) {
			throw new BlindTestException("Erreur lors du chargement de la configuration.", locException);
		}
		final String locMinLevel = _properties.getProperty(EnumConfiguration.MIN_LEVEL.getConstName());
		setMinLevel(locMinLevel);
		final String locPort = _properties.getProperty(EnumConfiguration.PORT.getConstName());
		setPort(locPort);
		final EnumConfiguration locHostNameEnum = EnumConfiguration.HOSTNAME;
		final String locHostName = _properties.getProperty(locHostNameEnum.getConstName(), (String) locHostNameEnum.getDefaultValue());
		setHostName(locHostName);
		final String locCharset = _properties.getProperty(EnumConfiguration.CHARSET.getConstName());
		setCharset(locCharset);
		return refresh();
	}

	@Override
	public final Configuration refresh() throws BlindTestException {
		try {
			_properties.store(new FileOutputStream(DEFAULT_FILE_NAME), "");
			return this;
		} catch (IOException locException) {
			throw new BlindTestException("Impossible de mettre à jour le fichier " + DEFAULT_FILE_NAME, locException);
		}
	}
	
	@Override
	public final int hashCode() {
		final int locPrime = 31;
		int locResult = 1;
		locResult = locPrime * locResult
				+ ((_charset == null) ? 0 : _charset.hashCode());
		locResult = locPrime * locResult
				+ ((_hostName == null) ? 0 : _hostName.hashCode());
		locResult = locPrime * locResult
				+ ((_minLevel == null) ? 0 : _minLevel.hashCode());
		locResult = locPrime * locResult + ((_port == null) ? 0 : _port.hashCode());
		return locResult;
	}

	@Override
	public final boolean equals(final Object parObject) {
		if (this == parObject)
			return true;
		if (parObject == null)
			return false;
		if (getClass() != parObject.getClass())
			return false;
		final DefaultConfiguration locOther = (DefaultConfiguration) parObject;
		if (_charset == null) {
			if (locOther._charset != null)
				return false;
		} else if (!_charset.equals(locOther._charset))
			return false;
		if (_hostName == null) {
			if (locOther._hostName != null)
				return false;
		} else if (!_hostName.equals(locOther._hostName))
			return false;
		if (_minLevel == null) {
			if (locOther._minLevel != null)
				return false;
		} else if (!_minLevel.equals(locOther._minLevel))
			return false;
		if (_port == null) {
			if (locOther._port != null)
				return false;
		} else if (!_port.equals(locOther._port))
			return false;
		return true;
	}

	private final String resolveHostname(final String parHostname) {
		if(StringUtil.isEmpty(parHostname)) {
			return (StringUtil.isEmpty(_hostName)) ? _hostName : (String) EnumConfiguration.HOSTNAME.getDefaultValue();
		}
		try {
			InetAddress.getByName(parHostname);
		} catch (UnknownHostException e) {
			return (StringUtil.isEmpty(_hostName)) ? _hostName : (String) EnumConfiguration.HOSTNAME.getDefaultValue();
		}
		return parHostname;
	}
	
	private final Charset resolveCharset(final String parCharset) {
		if(StringUtil.isEmpty(parCharset)) {
			return (_charset != null) ? _charset : (Charset) EnumConfiguration.CHARSET.getDefaultValue();
		}
		try {
			return Charset.forName(parCharset);
		} catch(Exception locException) {
			return (_charset != null) ? _charset : (Charset) EnumConfiguration.CHARSET.getDefaultValue();
		}
	}
	
	private final Integer resolvePort(final String parName) {
		final Integer locPort = StringUtil.toInteger(parName);
		if(locPort == null) {
			return (_port != null) ? _port : (Integer) EnumConfiguration.PORT.getDefaultValue();
		}
		return locPort;
	}
	
	private final Level resolveLevel(final String parMinLevel) {
		if(StringUtil.isEmpty(parMinLevel)) {
			return (_minLevel != null) ? _minLevel : (Level) EnumConfiguration.MIN_LEVEL.getDefaultValue();
		}
		try {
			return Level.parse(parMinLevel);
		} catch (IllegalArgumentException locException) {
			return (_minLevel != null) ? _minLevel : (Level) EnumConfiguration.MIN_LEVEL.getDefaultValue();
		}
	}

	
}
