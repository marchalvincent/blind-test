package org.commons.logger;


/**
 * Le manager des {@link InfoProvider}.
 * @author pitton
 *
 */
public final class InfoProviderManager {
	
	/**
	 * Retourne le {@link InfoProvider} par défaut de l'application
	 * @return {@link InfoProvider} par défaut de l'application
	 */
	static public final InfoProvider getFileProvider() {
		return FileProviderLoader.INSTANCE;
	}

	/**
	 * Singleton
	 */
	static private final class FileProviderLoader {
		static private final String LOG_FILE = "log/blind_test.log";
		static private final InfoProvider INSTANCE = new FileInfoProvider(LOG_FILE);
	}
	
	@SuppressWarnings("unused")
	static private InfoProvider _uiInfoProvider;
	
	static public final void setUiInfoProvider(final InfoProvider parInfoProvider) {
		_uiInfoProvider = parInfoProvider;
	}
	
	static public final InfoProvider getUiInfoProvider() {
		return getFileProvider();
	}
	
	private InfoProviderManager() {}
}
