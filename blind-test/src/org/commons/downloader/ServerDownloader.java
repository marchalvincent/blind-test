package org.commons.downloader;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.incremental.DownloadImageFacade;
import org.commons.logger.InfoProvider;
import org.commons.message.DownloadMessage;
import org.commons.message.IMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

public final class ServerDownloader extends AbstractDownloader {
	
	public ServerDownloader(final Socket parSocket, final InfoProvider parInfoProvider) {
		super(parSocket, parInfoProvider);
	}
	
	@Override
	public final Boolean call() {
		IMessage locDownloadMessage = null;
		try {
			locDownloadMessage = ReadWriterUtil.read(_socket);
		} catch (IOException e) {
			_infoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire ou de lire dans la socket d'adresse %s", _socket.getInetAddress().getHostAddress()), e);
			SystemUtil.close(_socket);
			return Boolean.FALSE;
		} catch (ClassNotFoundException e) {
			SystemUtil.close(_socket);
			return Boolean.FALSE;
		} 
		final DownloadMessage locDownloadrealMessage = (DownloadMessage) locDownloadMessage;
		try {
			final IMessage locResponse = DownloadImageFacade.getImages(locDownloadrealMessage.getVersions());
			ReadWriterUtil.write(_socket, locResponse);
		} catch (IOException e) {
			_infoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'envoyer des images dans la socket d'adresse %s", _socket.getInetAddress().getHostAddress()), e);
			return Boolean.FALSE;
		} finally {
			SystemUtil.close(_socket);
		}
		return Boolean.TRUE;
	}

	@Override
	public final Boolean download() {
		return DownloaderPool.getInstance().submit(this, _infoProvider);
	}
}
