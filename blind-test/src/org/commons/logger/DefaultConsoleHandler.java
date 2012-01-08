package org.commons.logger;

import java.util.logging.Formatter;
import java.util.logging.StreamHandler;

/**
 * Un {@link StreamHandler} qui remplace le {@link java.util.logging.ConsoleHandler} par défaut.
 * Le {@link java.util.logging.ConsoleHandler} n'accepte aucune modification de son {@link Formatter}
 * et les messages loggés et affichés se trouvent être différents. 
 * Ce {@link StreamHandler} s'appuie sur le même {@link Formatter} que le {@link FileInfoProvider}.
 * @author pitton
 *
 */
public final class DefaultConsoleHandler extends StreamHandler {

	protected DefaultConsoleHandler() {
		super(System.err, new DefaultFormatter());
	}
	
}
