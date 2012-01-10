package org.client.main;

import java.util.logging.Level;

import javax.swing.SwingUtilities;

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
		loadUI();
//		final String locLogin = "login2";
//		final ThreadInscription locInscription = new ThreadInscription(locLogin, locLogin, locLogin);
//		locInscription.call();
//		final ThreadConnexion locConnexion = new ThreadConnexion(locLogin, locLogin);
//		locConnexion.call();
//		final DownloaderPool locPool = DownloaderPool.getInstance();
//		if(locPool.isShutdown() == false) {
//			locPool.shutdown();
//		}
	}

	static private final Configuration loadConfiguration(final String[] parArguments) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		try {
			EnumConfiguration.updateConfiguration(locConfiguration,	parArguments);
		} catch (final BlindTestException locException) {
			final String locDocumentation = EnumConfiguration.getDocumentation();
			System.err.println(locDocumentation);
			SystemUtil.exit();
		}
		return locConfiguration;
	}

	static private final void loadApplication(final Configuration parConfiguration) {
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".",	parConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", parConfiguration.getPort()));
	}
	
	static private final void loadUI() {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public final void run() {
				Fenetre.instance().initFenetre();
				//TODO : Francois, tu ajoute un UiInfoProvider ici
			}
		});
	}
}
