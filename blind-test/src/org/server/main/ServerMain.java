package org.server.main;

import java.io.IOException;
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
			ss = new ServerSocket(port);
			System.out.println("Server listening on "+ss.getLocalPort());
			
			t = new Thread(new AcceptConnexion(ss));
			t.start();
			
		} catch (IOException e) {
			System.err.println("Port number "+ss.getLocalPort()+" is already used !!");
		}
	
	}
	
	static private final Configuration loadConfiguration(final String[] parArguments) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		try {
			EnumConfiguration.updateConfiguration(locConfiguration, parArguments);
		} catch (final BlindTestException locException) {
			final String locDocumentation = EnumConfiguration.getSupport();
			System.err.println(locDocumentation);
			SystemUtil.exit();
		}
		return locConfiguration;
	}
	
	static private final void loadApplication(final Configuration parConfiguration) {		
		final MonitorRunnable locRunnable = new MonitorRunnable();
		locRunnable.start();
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".", parConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", parConfiguration.getPort()));
	}
}
