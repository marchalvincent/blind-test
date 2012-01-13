package org.commons.downloader;

import java.util.concurrent.Future;
import java.util.logging.Level;

import org.commons.logger.InfoProvider;
import org.server.concurrent.BlindTestExecutor;

public final class DownloaderPool {

	static private final DownloaderPool INSTANCE = new DownloaderPool();
	
	static public final DownloaderPool getInstance() {
		return INSTANCE;
	}
	
	private BlindTestExecutor _executor;
	
	final public void shutdown() {
		_executor.shutdown();
	}
	
	final public boolean isShutdown() {
		return _executor.isShutdown();
	}
	
	final protected Boolean submit(final Downloader parDownloader) {
		final Future<Boolean> locFuture = _executor.submit(parDownloader);
		try {
			return locFuture.get();
		} catch (Exception e) {
		}
		return Boolean.FALSE;
	}
	
	final protected Boolean submitWithMessage(final Downloader parDownloader, final InfoProvider parInfoProvider, final String parStartMessage, final String parEndMessage) {
		parInfoProvider.appendMessage(Level.INFO, parStartMessage);
		final Boolean locResultat = submit(parDownloader);
		parInfoProvider.appendMessage(Level.INFO, parEndMessage);
		return locResultat;
	}
	
	private DownloaderPool() {
		_executor = new BlindTestExecutor(2);
	}
}
