package org.client.main;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.EnumConfiguration;
import org.commons.exception.BlindTestException;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.SystemUtil;

/**
 * La classe principale du client
 * @author pitton
 *
 */
public final class ClientMain {
	public static Socket socket = null;
	public static Thread t1;
	
	public final static void main(final String[] parArguments) {
		final Configuration locConfiguration = loadConfiguration(parArguments);
		loadApplication(locConfiguration);
		try {
			
			System.out.println("Asking Connexion");
			socket = new Socket(locConfiguration.getHostName(),locConfiguration.getPort());
			System.out.println("Connexion established with server, authentication :"); 
			
			t1 = new Thread(new Connexion(socket));
			t1.start();
			
			
		} catch (UnknownHostException e) {
		  System.err.println("Impossible to connect with this adress "+socket.getLocalAddress());
		} catch (IOException e) {
		  System.err.println("No server listening to this port "+socket.getLocalPort());
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
	
	static private final void loadApplication(final Configuration parConfiguration) {
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".", parConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", parConfiguration.getPort()));
	}
 }
