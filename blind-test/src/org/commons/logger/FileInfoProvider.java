package org.commons.logger;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;

/**
 * Un {@link InfoProvider} qui affiche les messages et les stocke dans un fichier de logs.
 * @author pitton
 *
 */
public class FileInfoProvider implements InfoProvider {

	/**
	 * Le chemin de fichier
	 */
	final private String _filename;
	/**
	 * Le {@link Logger} par défaut
	 */
	final private Logger _logger;
	
	protected FileInfoProvider(final String parFileName) {
		_filename = parFileName;
		_logger = Logger.getLogger(_filename);
		configure();		
	}

	@Override
	public InfoProvider appendMessage(final Level parLevel, final String parMessage) {
		return appendMessage(parLevel, parMessage, null);
	}

	@Override
	public InfoProvider appendMessage(final Level parLevel, final String parMessage, final Throwable parThrowable) {
		if(this.isShowable(parLevel)) {
			final LogRecord locRecord = new LogRecord(parLevel, parMessage);
			locRecord.setThrown(parThrowable);
			_logger.log(locRecord);
			final String locValue = getFormatter().format(locRecord);
			if(isError(parLevel)) {
				System.err.print(locValue);
			} else {
				System.out.print(locValue);
			}
		}
		return this;
	}
	
	public final boolean isError(final Level parLevel) {
		if(this.isShowable(parLevel)) {
			return Level.WARNING.equals(parLevel) || Level.SEVERE.equals(parLevel);
		}
		return false;
	}
	
	protected final Formatter getFormatter() {
		return _logger.getHandlers()[0].getFormatter();
	}

	@Override
	public final Level getMinLevel() {
		return _logger.getLevel();
	}

	@Override
	public final boolean isShowable(final Level parLevel) {
		return (parLevel == null) ? false : _logger.isLoggable(parLevel);
	}
	
	private final void configure() {
		try {
			_logger.setUseParentHandlers(false);
			final Configuration locConfiguration = ConfigurationManager.getConfiguration();
			final Level locMinLevel = locConfiguration.getMinLevel();
			_logger.setLevel(locMinLevel);
			final FileHandler locHander = new FileHandler(_filename, true);
			locHander.setLevel(locMinLevel);
			locHander.setFormatter(new DefaultFormatter());
			_logger.addHandler(locHander);
			_logger.addHandler(new DefaultConsoleHandler());
			LogManager.getLogManager().addLogger(_logger);
		} catch (Exception locException) {
			locException.printStackTrace();
		}
	}

}
