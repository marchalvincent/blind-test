package org.commons.downloader;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.entity.BanqueFacade;
import org.commons.logger.InfoProvider;
import org.commons.message.DownloadMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

public final class ClientDownloader extends AbstractDownloader {
	
	public ClientDownloader(final Socket parSocket, final InfoProvider parInfoProvider) {
		super(parSocket, parInfoProvider);
	}

	@Override
	public final Boolean call() {
		final DownloadMessage locDownloadMessage = (DownloadMessage) EnumMessage.DOWNLOAD.createMessage();
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final ImageIndex locIndex = new ImageIndex();
		try {
			locIndex.load();
		} catch (IOException e) {
			_infoProvider.appendMessage(Level.SEVERE, "Impossible de charger le fichier d'index des images.", e);
			SystemUtil.close(_socket);
			return Boolean.FALSE;
		}
		final Map<String, Integer> locVersioningMap = locIndex.getData();
		locDownloadMessage.putAllVersion(locVersioningMap);
		IMessage locResponseMessage = null;
		try {
			ReadWriterUtil.write(_socket, locDownloadMessage);
			locResponseMessage = ReadWriterUtil.read(_socket);
		} catch (Exception e) {
			_infoProvider.appendMessage(Level.SEVERE, String.format("Impossible de se connecter à l'adresse %s sur le port %d.", locConfiguration.getHostName(), locConfiguration.getPort()), e);
			return Boolean.FALSE;
		} finally {
			SystemUtil.close(_socket);
		}
		final DownloadMessage locResponseDownload = (DownloadMessage) locResponseMessage;
		locIndex.putAll(locResponseDownload.getVersions());
		try {
			locIndex.write();
		} catch (IOException e) {
			_infoProvider.appendMessage(Level.SEVERE, "Impossible de mettre à jour le fichier d'index.", e);
			return Boolean.FALSE;
		}
		final BanqueFacade locFacade = BanqueFacade.instance();
		for(final Map.Entry<String, byte[]> locEntry : locResponseDownload.getDownload().entrySet()) {
			try {
				final RenderedImage locImage = locFacade.convertByte(locEntry.getValue());
				final String locPath = locConfiguration.getImageDirectory() + locEntry.getKey();
				locFacade.writeImage(locPath, locImage);
			} catch (IOException e) {
				_infoProvider.appendMessage(Level.SEVERE, String.format("Impossible de créer l'image %s", locEntry.getKey()), e);
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;
	}

	@Override
	public final Boolean download() {
		return DownloaderPool.getInstance().submit(this, _infoProvider);
	}
}
