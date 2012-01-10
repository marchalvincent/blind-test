package org.server.monitor;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.entity.Banque;
import org.commons.entity.BanqueFacade;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.security.Encryptor;
import org.commons.security.MD5Encryptor;
import org.server.persistence.Manager;
import org.server.persistence.Managers;

/**
 * La commande correspond à l'ajout d'une image dans l'application.
 * @author pitton
 *
 */
public final class MonitorAddImageCommand extends MonitorCommand {

	protected MonitorAddImageCommand(final String parCommandName, final String[] parArguments) {
		super(EnumMonitorCommand.ADD, parCommandName, parArguments);
	}

	@Override
	public final String call() {
		final InfoProvider locFileProvider = InfoProviderManager.getFileProvider();
		// si pas d'arguments on l'affiche
		if(_arguments == null || _arguments.length < 2) {
			locFileProvider.appendMessage(Level.WARNING, String.format("La commande %s nécessite au moins deux arguments correspondant au chemin vers l'image et à la réponse.", getConstName()));
			return "";
		}
		final String locFile = _arguments[0];
		final String locAnswer = _arguments[1];
		final BanqueFacade locFacade = BanqueFacade.instance();
		RenderedImage locImage = null;
		try {
			// On charge l'image en mémoire
			locImage = locFacade.readImage(locFile);
		} catch (IOException parException) {
			locFileProvider.appendMessage(Level.WARNING, String.format("Le fichier %s n'existe pas ou n'est pas accessible en lecture.", locFile));
			return "";
		}
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final String locImageDirectory = locConfiguration.getImageDirectory();
		// On récupère le nom de l'image
		final String locFileName = locFacade.getFileName(locFile);
		final Encryptor locEncryptor = new MD5Encryptor(locFileProvider);
		// On crypte le nom de l'image en conservant le type de fichier
		final String locTmpFileName = locEncryptor.encrypt(locFileName) + "." + locFacade.getFormat(locFileName);
		// On déplace l'image dans le dossier des images
		final String locRealFileName = locImageDirectory + locTmpFileName;
		try {
			// On crée la nouvelle image
			locFacade.writeImage(locRealFileName, locImage);
		} catch (IOException e) {
			locFileProvider.appendMessage(Level.SEVERE, "Impossible de copier l'image dans le répertoire adéquat. Vérifiez que ce dossier est accessible en écriture.");
			return "";
		}
		final Manager<Banque> locBanqueManager = Managers.createBanqueManager();
		final Banque locCurrentBanque = locBanqueManager.find(locTmpFileName);
		// Si l'image existait déjà, on met à jour la "potentielle" nouvelle réponse et le numéro de version
		if(locCurrentBanque != null) {
			locCurrentBanque.setAnswer(locAnswer);
			locFileProvider.appendMessage(Level.INFO, String.format("L'image %s a été mise à jour. La réponse associée est %s. La version actuelle est : %d", locFileName, locAnswer, locCurrentBanque.getVersion() + 1));
			locBanqueManager.merge(locCurrentBanque);
		} else {
			// Sinon, on ajoute une nouvelle image
			final Banque locBanque = new Banque();
			locBanque.setId(-1);
			locBanque.setAnswer(locAnswer);
			locBanque.setDirectory(locRealFileName);
			locBanque.setName(locTmpFileName);
			locBanque.setVersion(new AtomicInteger(1));
			locBanqueManager.add(locBanque);
			locFileProvider.appendMessage(Level.INFO, String.format("L'image %s a correctement été ajouté. La réponse associée est %s.", locFileName, locAnswer));
		}
		return "";
	}

}
