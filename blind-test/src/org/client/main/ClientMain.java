package org.client.main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
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

	public ClientMain() {}
	
	public final static void main(final String[] parArguments) {
		final Configuration locConfiguration = loadConfiguration(parArguments);
		loadApplication(locConfiguration);
		
		ClientMain client = new ClientMain();
		client.startClient(locConfiguration);
	}
	
	private void startClient(Configuration locConfiguration) {
		Socket socket = null;
		while (socket == null) {
			try {
				socket = new Socket(locConfiguration.getHostName(), locConfiguration.getPort());
				
				//Pour le mode écriture, on lis sur la console et on écrit sur le serveur
				BufferedReader readerOnLocal = new BufferedReader(new InputStreamReader(System.in));
				BufferedWriter writerToServer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				FluxToFlux sortie = new FluxToFlux(readerOnLocal, writerToServer);
				sortie.start();
				
				//Pour le mode lecture, on lis sur le serveur et on écrit sur la console
				BufferedReader readOnServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				BufferedWriter writeOnLocal = new BufferedWriter(new OutputStreamWriter(System.out));
				FluxToFlux entree = new FluxToFlux(readOnServer, writeOnLocal);
				entree.start();
				
			} catch (java.net.ConnectException e) {
				
				//On n'arrive pas à se connecter donc on sleep 1sec
				try {
					
					Thread.sleep(1000);
					System.out.println("Echec de connexion au serveur, retentative...");
					final InfoProvider locProvider = InfoProviderManager.getFileProvider();
					locProvider.appendMessage(Level.INFO, "Echec de connexion d'un client au serveur, retentative...");
					
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".", parConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", parConfiguration.getPort()));
	}
 }
