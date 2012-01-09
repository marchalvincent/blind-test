package org.server.main;

import java.io.IOException;
import java.util.Properties;
import java.net.ServerSocket;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.EnumConfiguration;
import org.commons.exception.BlindTestException;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;
import org.server.monitor.MonitorRunnable;
import org.server.persistence.EnumDatabaseProperties;
import org.server.persistence.Managers;

/**
 * La classe principale du serveur
 * @author pitton
 *
 */
public final class ServerMain {
	
	public static ServerSocket ss = null;
	 public static Thread t;
	 public static int port;		

	public final static void main(final String[] parArguments) {
		final Configuration locConfiguration = loadConfiguration(parArguments);
		loadApplication(locConfiguration);
		try {
			ss = new ServerSocket(locConfiguration.getPort());
			InfoProviderManager.getFileProvider().appendMessage(Level.SEVERE, "Server listening on "+ss.getLocalPort());
			
			t = new Thread(new AcceptConnexion(ss));
			t.start();
			
		} catch (IOException e) {
			System.err.println("Port number "+ss.getLocalPort()+" is already used !!");
		}
	
	}
	
	static private final void loadApplication(final Configuration parConfiguration) {		
		final MonitorRunnable locRunnable = new MonitorRunnable();
		locRunnable.start();
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "DÃ©marrage de l'application en cours...");
		locProvider.appendMessage(Level.INFO, "Lancement du moniteur rÃ©ussi.");
		final Properties locProperties = Managers.getDatabaseConfiguration();
		final String locHostname = locProperties.getProperty(EnumDatabaseProperties.HOSTNAME.getConstName());
		final String locPort = locProperties.getProperty(EnumDatabaseProperties.PORT.getConstName());
		locProvider.appendMessage(Level.INFO, String.format("La connexion Ã  la base de donnÃ©es a Ã©tÃ© effectuÃ©e. L'adresse de la base est \"%s\" sur le port \"%s\".", locHostname, locPort));
	}
	
	static private final Configuration loadConfiguration(final String[] parArguments) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		try {
			EnumConfiguration.updateConfiguration(locConfiguration, parArguments);
		} catch (final BlindTestException locException) {
			final String locDocumentation = EnumConfiguration.getDocumentation();
			System.err.println(locDocumentation);
			SystemUtil.exit();
		}
		return locConfiguration;
	}
	
	
}
