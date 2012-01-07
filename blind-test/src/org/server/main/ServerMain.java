package org.server.main;

import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;

/**
 * La classe principale du serveur
 * @author pitton
 *
 */
public final class ServerMain {

	public final static void main(final String[] args) {
		loadApplication();
	}
	
	static private final void loadApplication() {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".", locConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", locConfiguration.getPort()));
	}
}
