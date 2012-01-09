package org.commons.configuration;

import org.commons.exception.BlindTestException;

/**
 * Un manager pour obtenir la {@link Configuration} par défaut de l'application
 * @author pitton
 *
 */
public final class ConfigurationManager {

	/**
	 * Retourne la {@link Configuration} par défaut de l'application.
	 * @return {@link Configuration} la configuration par défaut.
	 */
	static final public Configuration getConfiguration() {
		return Loader.INSTANCE;
	}
	
	static private final class Loader {
		static private final Configuration INSTANCE = init();
		
		static private final Configuration init() {
			final Configuration locConfiguration = new DefaultConfiguration();
			try {
				return locConfiguration.load();
			} catch (BlindTestException e) {
			}
			return null;
		}
	}
	
	private ConfigurationManager() {}
}
