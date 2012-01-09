package org.server.main;

import java.io.BufferedReader;
import java.util.Properties;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
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

	private List<BufferedWriter> allClients = new LinkedList<BufferedWriter>();
	
	public ServerMain() {}
	
	public final static void main(final String[] parArguments) {
		final Configuration locConfiguration = loadConfiguration(parArguments);
		loadApplication(locConfiguration);
		
		ServerMain server = new ServerMain();
		server.startServer(locConfiguration);
	}
	
	private void startServer(Configuration locConfiguration) {
		try {
			ServerSocket s = new ServerSocket(locConfiguration.getPort());
			System.out.println("Serveur connecte au port : " + s.getLocalPort());
			
			FluxToNFlux flux;
			BufferedWriter writer;
			BufferedReader reader;
			
			Socket co = null;
			while (s.isClosed() == false) {
				co = s.accept();

				reader = new BufferedReader(new InputStreamReader(co.getInputStream()));
				writer = new BufferedWriter(new OutputStreamWriter(co.getOutputStream()));
				
				addWriter(writer);
				
				flux = new FluxToNFlux(reader, writer, this);
				flux.start();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private synchronized void addWriter(BufferedWriter writer) {
		allClients.add(writer);
	}
	
	public List<BufferedWriter> getWriters() {
		return allClients;
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
		final MonitorRunnable locRunnable = new MonitorRunnable();
		locRunnable.start();
		final InfoProvider locProvider = InfoProviderManager.getFileProvider();
		locProvider.appendMessage(Level.INFO, "Démarrage de l'application.");
		locProvider.appendMessage(Level.INFO, String.format("L'adresse du serveur est \"%s\".", parConfiguration.getHostName()));
		locProvider.appendMessage(Level.INFO, String.format("Le port par défaut est \"%d\".", parConfiguration.getPort()));
	}
}
