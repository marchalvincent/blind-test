package org.commons.downloader;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.commons.entity.User;
import org.commons.incremental.DownloadImageFacade;
import org.commons.logger.InfoProvider;
import org.commons.message.DownloadMessage;
import org.commons.message.IMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

public final class ServerDownloader extends AbstractDownloader {
	
	private final User _user;
	
	public ServerDownloader(final User parUser,final Socket parSocket, final InfoProvider parInfoProvider) {
		super(parSocket, parInfoProvider);
		
		_user = parUser;
	}
	
	@Override
	public final Boolean call() {
		IMessage locDownloadMessage = null;
		try {
			locDownloadMessage = ReadWriterUtil.read(_socket);
		} catch (IOException e) {
			_infoProvider.appendMessage(Level.SEVERE, String.format("Le téléchargement de l'utilisateur %s a échoué.", _user.getConstName()), e);
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
			_infoProvider.appendMessage(Level.SEVERE, String.format("Le téléchargement de l'utilisateur %s a échoué.", _user.getConstName()), e);
			return Boolean.FALSE;
		} finally {
			SystemUtil.close(_socket);
		}
		return Boolean.TRUE;
	}

	@Override
	public final Boolean download() {
		final String locStartMessage = String.format("L'utilisateur %s lance un téléchargement d'images.", _user.getConstName());
		final String locEndMessage = String.format("L'utilisateur %s a fini le téléchargement des images.", _user.getConstName());
		return DownloaderPool.getInstance().submitWithMessage(this, _infoProvider, locStartMessage, locEndMessage);
	}
}
