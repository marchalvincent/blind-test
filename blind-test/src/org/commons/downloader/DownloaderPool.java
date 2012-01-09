package org.commons.downloader;

import java.util.concurrent.Future;

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
	
	private DownloaderPool() {
		_executor = new BlindTestExecutor(10);
	}
}
