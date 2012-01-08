package org.server.monitor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.commons.logger.InfoProvider;

/**
 * Un flux permettant d'écrire et de lire dans la console. Ce flux est utilisé par les loggers 
 * et le thread du moniteur.
 * @see InfoProvider
 * @see MonitorRunnable
 * @author pitton
 *
 */
public final class MonitorReadWriter extends DataOutputStream {

	static private final MonitorReadWriter INSTANCE = new MonitorReadWriter();
	
	static public final MonitorReadWriter instance() {
		return INSTANCE;
	}

	private final transient Scanner _reader;
	
	private MonitorReadWriter() {
		super(System.err);
		
		_reader = new Scanner(System.in);
	}
	
	/**
	 * Retoune la dernière ligne écrite dans la console. Cette méthode est bloquante.
	 * @return {@link String} la dernière ligne écrite dans la console.
	 */
	protected final String readLine() {
		return _reader.nextLine();
	}
	
	/**
	 * Affiche un message dans la console.
	 * @param parMessage {@link String} un message à afficher dans la console.
	 */
	protected final void println(final String parMessage) {
		System.err.println(parMessage);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final void close() throws IOException {
		super.close();
		_reader.close();
	}
}
