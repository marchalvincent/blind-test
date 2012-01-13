package org.server.persistence;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;

import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;

/**
 * La classe représentant la connexion à la base de données MySQL.
 * @author pitton
 *
 */
final class MySQLConnexion {

	static protected final MySQLConnexion instance() {
		return Loader.INSTANCE;
	}
	
	static private final class Loader {
		static private final MySQLConnexion INSTANCE = new MySQLConnexion(InfoProviderManager.getFileProvider());
	}
	
	private Properties _properties;
	final private Connection _connection;
	final private InfoProvider _infoProvider;
	
	private MySQLConnexion(final InfoProvider parInfoProvider) {
		_infoProvider = parInfoProvider;
		_connection = init(_infoProvider);
	}
	
	/**
	 * Retourne les {@link Properties} de la base de données.
	 * @return les {@link Properties} de la base de données.
	 */
	protected final Properties getProperties() {
		return new Properties(_properties);
	}
	
	/**
	 * Retourne l'{@link InfoProvider} de la connexion à la base de données.
	 * @return l'{@link InfoProvider} de la connexion à la base de données.
	 */
	protected final InfoProvider getInfoProvider() {
		return _infoProvider;
	}
	
	/**
	 * Retourne la {@link Connection} à la base de données.
	 * @return
	 */
	protected final Connection getConnection() {
		return _connection;
	}
	
	/**
	 * Initialise la {@link Connection} à la base de données. 
	 * @param parProvider {@link InfoProvider} un logger pour le démarrage de la connexion.
	 * @return {@link Connection} la connexion à la base de données.
	 */
	private final Connection init(final InfoProvider parProvider) {		
		_properties = new Properties();
		try {
			_properties.load(MySQLConnexion.class.getResourceAsStream("database.properties"));
		} catch (IOException locException) {
			parProvider.appendMessage(Level.SEVERE, "Impossible de charger la configuration de la base de données", locException);
			SystemUtil.exit();
		}
		try {
			Class.forName(_properties.getProperty(EnumDatabaseProperties.DRIVER.getConstName()));
		} catch (ClassNotFoundException locException) {
			parProvider.appendMessage(Level.SEVERE, "Impossible de charger le driver MySQL", locException);
			SystemUtil.exit();
		}
		final String locURL = _properties.getProperty(EnumDatabaseProperties.URL.getConstName());
		final String locUser = _properties.getProperty(EnumDatabaseProperties.LOGIN.getConstName());
		final String locPassword = _properties.getProperty(EnumDatabaseProperties.PASSWORD.getConstName());
		try {
			final Connection locConnection = DriverManager.getConnection(locURL, locUser, locPassword);
			locConnection.setAutoCommit(false);
			return locConnection;
		} catch (SQLException locException) {
			parProvider.appendMessage(Level.SEVERE, "Impossible de se connecter à la base de données", locException);
			SystemUtil.exit();
		}
		return null;
	}
}
