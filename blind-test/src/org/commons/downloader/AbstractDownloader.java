package org.commons.downloader;

import java.net.Socket;

import org.commons.logger.InfoProvider;

public abstract class AbstractDownloader implements Downloader {

	protected final Socket _socket;
	protected final InfoProvider _infoProvider;
	
	protected AbstractDownloader(final Socket parSocket, final InfoProvider parInfoProvider) {
		_infoProvider = parInfoProvider;
		_socket = parSocket;
	}

	protected final Socket getSocket() {
		return _socket;
	}
	
	protected final InfoProvider getInfoProvider() {
		return _infoProvider;
	}
	
}
