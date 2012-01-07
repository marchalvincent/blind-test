package org.commons.configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
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
	public final void setPort(final Integer parPort) {
		this._port=parPort;
		_properties.put(EnumConfiguration.PORT.getConstName(), _port.toString());
	}

	@Override
	public final void setHostName(final String parHostName) {
		_hostName = parHostName;
		_properties.put(EnumConfiguration.HOSTNAME.getConstName(), _hostName);
	}

	@Override
	public final void setCharset(final Charset parCharset) {
		this._charset = parCharset;
		_properties.put(EnumConfiguration.CHARSET.getConstName(), getCharsetName());
	}

	@Override
	public final void setMinLevel(final Level parLevel) {
		_minLevel = parLevel;
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
		setMinLevel(resolveLevel(locMinLevel));
		final String locPort = _properties.getProperty(EnumConfiguration.PORT.getConstName());
		setPort(resolvePort(locPort));
		final EnumConfiguration locHostNameEnum = EnumConfiguration.HOSTNAME;
		final String locHostName = _properties.getProperty(locHostNameEnum.getConstName(), (String) locHostNameEnum.getDefaultValue());
		setHostName(locHostName);
		final String locCharset = _properties.getProperty(EnumConfiguration.CHARSET.getConstName());
		setCharset(resolveCharset(locCharset));
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
	
	private final Charset resolveCharset(final String parCharset) {
		if(StringUtil.isEmpty(parCharset)) {
			return (Charset) EnumConfiguration.CHARSET.getDefaultValue();
		}
		try {
			return Charset.forName(parCharset);
		} catch(IllegalCharsetNameException locException) {
			return (Charset) EnumConfiguration.CHARSET.getDefaultValue();
		}
	}
	
	private final Integer resolvePort(final String parName) {
		final Integer locPort = StringUtil.toInteger(parName);
		if(locPort == null) {
			return (Integer) EnumConfiguration.PORT.getDefaultValue();
		}
		return locPort;
	}
	
	private final Level resolveLevel(final String parMinLevel) {
		if(StringUtil.isEmpty(parMinLevel)) {
			return (Level) EnumConfiguration.MIN_LEVEL.getDefaultValue();
		}
		try {
			return Level.parse(parMinLevel);
		} catch (IllegalArgumentException locException) {
			return (Level) EnumConfiguration.MIN_LEVEL.getDefaultValue();
		}
	}

}
