package org.server.main;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.EnumConfiguration;
import org.commons.exception.BlindTestException;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;
import org.server.concurrent.Server;
import org.server.monitor.MonitorRunnable;
import org.server.persistence.EnumDatabaseProperties;
import org.server.persistence.Managers;

/**
 * La classe principale du serveur
 * @author pitton
 *
 */
public final class ServerMain {	

	public final static void main(final String[] parArguments) {
		final Configuration locConfiguration = loadConfiguration(parArguments);
		loadApplication(locConfiguration);
	}
	
	static private final void loadApplication(final Configuration parConfiguration) {		
		final MonitorRunnable locRunnable = new MonitorRunnable();
		locRunnable.start();
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application en cours...");
		locProvider.appendMessage(Level.INFO, "Lancement du moniteur réussi.");
		final Properties locProperties = Managers.getDatabaseConfiguration();
		final String locHostname = locProperties.getProperty(EnumDatabaseProperties.HOSTNAME.getConstName());
		final String locPort = locProperties.getProperty(EnumDatabaseProperties.PORT.getConstName());
		locProvider.appendMessage(Level.INFO, String.format("La connexion à la base de données a été effectuée. L'adresse de la base est \"%s\" sur le port \"%s\".", locHostname, locPort));
		final Server locServer = new Server(locProvider);
		try {
			locServer.start();
		} catch (IOException e) {
			locProvider.appendMessage(Level.SEVERE, String.format("Impossible de lancer le serveur sur le port %s", locPort), e);
			locProvider.appendMessage(Level.SEVERE, "Fermeture de l'application");
			SystemUtil.exit();
		}
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
