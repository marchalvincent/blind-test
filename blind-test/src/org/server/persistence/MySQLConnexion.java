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


final class MySQLConnexion {

	static protected final MySQLConnexion instance() {
		return Loader.INSTANCE;
	}
	
	static private final class Loader {
		static private final MySQLConnexion INSTANCE = new MySQLConnexion(InfoProviderManager.getFileProvider());
	}
	
	final private Connection _connection;
	
	private MySQLConnexion(final InfoProvider parInfoProvider) {
		_connection = init(parInfoProvider);
	}
	
	protected final Connection getConnection() {
		return _connection;
	}
	
	private final Connection init(final InfoProvider parProvider) {		
		final Properties locProperties = new Properties();
		try {
			locProperties.load(MySQLConnexion.class.getResourceAsStream("database.properties"));
		} catch (IOException locException) {
			parProvider.appendMessage(Level.SEVERE, "Impossible de charger la configuration de la base de données", locException);
			SystemUtil.exit();
		}
		try {
			Class.forName(locProperties.getProperty(EnumDatabase.DRIVER.getConstName()));
		} catch (ClassNotFoundException locException) {
			parProvider.appendMessage(Level.SEVERE, "Impossible de charger le driver MySQL", locException);
			SystemUtil.exit();
		}
		final String locURL = locProperties.getProperty(EnumDatabase.URL.getConstName());
		final String locUser = locProperties.getProperty(EnumDatabase.LOGIN.getConstName());
		final String locPassword = locProperties.getProperty(EnumDatabase.PASSWORD.getConstName());
		try {
			return DriverManager.getConnection(locURL, locUser, locPassword);
		} catch (SQLException locException) {
			parProvider.appendMessage(Level.SEVERE, "Impossible de se connecter à la base de données", locException);
			SystemUtil.exit();
		}
		return null;
	}
	
}
