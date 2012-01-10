package org.commons.configuration;

import java.io.File;
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

/**
 * Une {@link Configuration} par défaut de l'application.
 * @author pitton
 *
 */
public final class DefaultConfiguration implements Configuration {

	/**
	 * Le fichier de configuration
	 */
	static private final String DEFAULT_FILE_NAME = "conf/configuration.properties";
	
	private Level _minLevel;
	private String _imageDirectory;
	private String _hostName;
	private Charset _charset;
	private String _indexFile;
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
	public final String getImageDirectory() {
		return _imageDirectory;
	}
	
	@Override
	public final String getIndexFile() {
		return _indexFile;
	}
	
	@Override
	public final void setIndexFile(final String parIndexFile) {
		_indexFile = resolveIndexFile(parIndexFile);
		_properties.put(EnumConfiguration.INDEX_FILE.getConstName(), parIndexFile);
	}
	
	@Override
	public final void setImageDirectory(final String parImageDirectory) {
		_imageDirectory = resolveImageDirectory(parImageDirectory);
		_properties.put(EnumConfiguration.IMAGE_DIRECTORY.getConstName(), _imageDirectory);
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
		final String locImageDirectory = _properties.getProperty(EnumConfiguration.IMAGE_DIRECTORY.getConstName());
		setImageDirectory(locImageDirectory);
		final String locIndexFile = _properties.getProperty(EnumConfiguration.INDEX_FILE.getConstName());
		setIndexFile(locIndexFile);
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

	/**
	 * Vérifie que la valeur spécifiée n'est pas incorrecte. Si c'est le cas, 
	 * cette méthode garde la valeur précédente, s'il y en avait une, ou prend la valeur par défaut.
	 * Une adresse inaccessible est considérée comme invalide.
	 * @param parHostname {@link String} l'adresse du serveur
	 * @return {@link String} l'adresse du serveur.
	 */
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
	
	/**
	 * Vérifie le nom du {@link Charset} spécifié n'est pas invalide. Si c'est le cas, 
	 * cette méthode garde la valeur précédente, s'il y en avait une, ou prend la valeur par défaut.
	 * Un nom de {@link Charset} associé à aucun {@link Charset} est invalide.
	 * @param parCharset {@link String} le nom d'un {@link Charset}
	 * @see Charset#availableCharsets()
	 * @see Charset#defaultCharset()
	 * @return {@link Charset} le charset de l'application
	 */
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
	
	/**
	 * Vérifie que le port de l'application n'est pas invalide. Si c'est le cas, 
	 * cette méthode garde la valeur précédente, s'il y en avait une, ou prend la valeur par défaut.
	 * Un port qui n'est pas un entier ou qui est null est invalide.
	 * @param parName {@link String} le port de l'application.
	 * @return {@link Integer} le port de l'application
	 */
	private final Integer resolvePort(final String parName) {
		final Integer locPort = StringUtil.toInteger(parName);
		if(locPort == null) {
			return (_port != null) ? _port : (Integer) EnumConfiguration.PORT.getDefaultValue();
		}
		return locPort;
	}
	
	/**
	 * Vérifie que le niveau des messages n'est pas invalide. Si c'est le cas, 
	 * cette méthode garde la valeur précédente, s'il y en avait une, ou prend la valeur par défaut.
	 * Un {@link Level} est considéré comme invalide si la méthode {@link Level#parse(String)} lance une
	 * {@link IllegalArgumentException}, ou que le nom de niveau est null.
	 * @param parMinLevel {@link String} le nom du niveau des messages de l'application
	 * @see {@link Level}
	 * @see {@link Level#parse(String)}
	 * @return {@link Level} le niveau des message de l'application.
	 */
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
	
	private final String resolveImageDirectory (final String parPath) {
		if(StringUtil.isEmpty(parPath)) {
			return (_imageDirectory != null) ? _imageDirectory : (String) EnumConfiguration.INDEX_FILE.getDefaultValue();
		}
		final File locDirectory = new File(parPath);
		return (locDirectory.isDirectory()) ? parPath : (String) EnumConfiguration.INDEX_FILE.getDefaultValue();
	}
	private final String resolveIndexFile (final String parPath) {
		if(StringUtil.isEmpty(parPath)) {
			return (_imageDirectory != null) ? _imageDirectory : (String) EnumConfiguration.INDEX_FILE.getDefaultValue();
		}
		final File locDirectory = new File(parPath);
		return (locDirectory.isFile()) ? parPath : (String) EnumConfiguration.INDEX_FILE.getDefaultValue();
	}
}
