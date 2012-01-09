package org.client.main;

import java.util.concurrent.Callable;
import java.util.logging.Level;

import org.client.ui.Fenetre;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.EnumConfiguration;
import org.commons.exception.BlindTestException;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;

/**
 * La classe principale du client
 * 
 * @author pitton
 * 
 */
public final class ClientMain {

	public final static void main(final String[] parArguments) {
		final Configuration locConfiguration = loadConfiguration(parArguments);
		loadApplication(locConfiguration);
		//new Fenetre ();
		Callable<Boolean> c = new ThreadInscription("login", "password", "nom");
		try {
			boolean rep = c.call();
			System.out.println(rep);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static private final Configuration loadConfiguration(final String[] parArguments) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		try {
			EnumConfiguration.updateConfiguration(locConfiguration,
					parArguments);
		} catch (final BlindTestException locException) {
			final String locDocumentation = EnumConfiguration
					.getDocumentation();
			System.err.println(locDocumentation);
			SystemUtil.exit();
		}
		return locConfiguration;
	}

	static private final void loadApplication(
			final Configuration parConfiguration) {
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".",	parConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", parConfiguration.getPort()));
	}
}
