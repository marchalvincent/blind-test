package org.commons.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;
import org.commons.util.StringUtil;
import org.commons.util.WithUtilities;

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
	private Integer _port, _timer;
	private String _backgroundImage;
	
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
	public final Integer getTimer() {
		return _timer;
	}
	
	@Override
	public final void setIndexFile(final String parIndexFile) {
		final EnumConfiguration locIndexFile = EnumConfiguration.INDEX_FILE;
		_indexFile = resolveFile(parIndexFile, locIndexFile, false);
		_properties.put(locIndexFile.getConstName(), parIndexFile);
	}
	
	@Override
	public final void setImageDirectory(final String parImageDirectory) {
		final EnumConfiguration locImageDirectory = EnumConfiguration.IMAGE_DIRECTORY;
		_imageDirectory = resolveFile(parImageDirectory, locImageDirectory, true);
		_properties.put(locImageDirectory.getConstName(), _imageDirectory);
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
	public void setTimer(final String parTimer) {
		_timer = resolveTimer(parTimer);
		_properties.put(EnumConfiguration.TIMER_PARTIE.getConstName(), _timer.toString());
	}

	public final String getBackgroundImage() {
		return _backgroundImage;
	}
	
	public final void setBackgroundImage (final String parBackgroundImage) {
		final EnumConfiguration locBackgroundImage = EnumConfiguration.BACKGROUND_IMAGE;
		_backgroundImage = resolveFile(parBackgroundImage, locBackgroundImage, false);
		_properties.put(locBackgroundImage.getConstName(), _backgroundImage);
	}

	@Override
	public final Configuration load() throws BlindTestException {
		_properties = new Properties();
		try {
			_properties.load(new FileInputStream(DEFAULT_FILE_NAME));
		} catch (IOException locException) {
			throw new BlindTestException("Erreur lors du chargement de la configuration.", locException);
		}
		final EnumConfiguration[] locArray = EnumConfiguration.values();
		for(final Map.Entry<Object, Object> locEntry : _properties.entrySet()) {
			final String locProperty = locEntry.getKey().toString();
			final EnumConfiguration locConfigurationEnum = WithUtilities.getByName(locArray, locProperty);
			if(locConfigurationEnum == null) continue;
			
			locConfigurationEnum.setConfigurationValue(this, locEntry.getValue().toString());
		}
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
	
	private final Integer resolveTimer(final String parTimer) {
		final Integer locTimer = StringUtil.toInteger(parTimer);
		final Integer locDefaultValue = (Integer) EnumConfiguration.TIMER_PARTIE.getDefaultValue();
		if (locTimer == null) {
			return  (_timer != null) ? _timer : locDefaultValue;
		}
		if(locTimer < locDefaultValue) {
			return locDefaultValue;
		}
		return locTimer;
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
	
	private final String resolveFile(final String parPath, final EnumConfiguration parConfiguration, final boolean parIsDirectory) {
		if(StringUtil.isEmpty(parPath)) {
			return parConfiguration.getDefaultValue().toString();
		}
		final File locFile = new File(parPath);
		if(parIsDirectory == true) {
			return (locFile.isDirectory()) ? parPath : parConfiguration.getDefaultValue().toString();
		}
		return (locFile.isFile()) ? parPath : parConfiguration.getDefaultValue().toString();
	}
}
