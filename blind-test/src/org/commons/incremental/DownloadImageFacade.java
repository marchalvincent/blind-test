package org.commons.incremental;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.commons.entity.Banque;
import org.commons.entity.BanqueFacade;
import org.commons.message.DownloadDefaultMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.server.persistence.Managers;

public final class DownloadImageFacade {

	final static public IMessage getImages (final Map<String, Integer> parIndexation) throws IOException {
		final List<Banque> locBanqueList = Managers.createBanqueManager().findAll();
		final Map<String, byte[]> locImagesMap = new HashMap<String, byte[]>();
		final Map<String, Integer> locVersionMap = new HashMap<String, Integer>();
		for(final Banque locBanque : locBanqueList) {
			final String locFileName = locBanque.getConstName();
			if(parIndexation.containsKey(locFileName)) {
				// On vérifie la version
				final Integer locClientVersion = parIndexation.get(locFileName);
				if(locBanque.getVersion() > locClientVersion.intValue()) {
					addImage(locBanque, locImagesMap, locVersionMap);
				}
			} else {
				addImage(locBanque, locImagesMap, locVersionMap);
			}
		}
		
		final DownloadDefaultMessage locDownloadMessage = (DownloadDefaultMessage) EnumMessage.DOWNLOAD.createMessage();
		locDownloadMessage.putAllImage(locImagesMap);
		locDownloadMessage.putAllVersion(locVersionMap);
		return locDownloadMessage;
	}
	
	final static public void addImage(final Banque parBanque, final Map<String, byte[]> parImages, final Map<String, Integer> parVersion) throws IOException {
		final BanqueFacade locFacade = BanqueFacade.instance();
		final String locFileName = parBanque.getConstName();
		final RenderedImage locImage = locFacade.readImage(locFileName);
		final byte[] locByteImage = locFacade.convertImage(locFacade.getFormat(locFileName), locImage);
		parImages.put(locFileName, locByteImage);
		parVersion.put(locFileName, parBanque.getVersion());
	}
	
	private DownloadImageFacade() {}
}
