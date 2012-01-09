package org.server.monitor;

import java.io.File;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.entity.Banque;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.util.StringUtil;
import org.server.persistence.Manager;
import org.server.persistence.Managers;

/**
 * La commande correspondant à la suppression d'une image de l'application.
 * @author pitton
 *
 */
public final class MonitorRemoveCommand extends MonitorCommand {

	protected MonitorRemoveCommand(final String parCommandName, final String[] parArguments) {
		super(EnumMonitorCommand.REMOVE, parCommandName, parArguments);
	}

	@Override
	public final String call() throws Exception {
		final InfoProvider locFileProvider = InfoProviderManager.getFileProvider();
		// si pas d'arguments on l'affiche
		if(_arguments == null || _arguments.length < 1) {
			locFileProvider.appendMessage(Level.WARNING, String.format("La commande %s nécessite au moins un argument correspondant à l'identifiant de l'image ou à son nom.", getConstName()));
			return "";
		}
		String locArgument = _arguments[0];
		final Manager<Banque> locBanqueManager = Managers.createBanqueManager();
		Banque locBanque = null;
		// Si c'est un nombre, on supprime un identifiant, sinon une image
		if(StringUtil.isNumeric(locArgument)) {
			locBanque = locBanqueManager.find(StringUtil.toInteger(locArgument));
			locArgument = locBanque.getConstName();
		} else {
			locBanque = locBanqueManager.find(locArgument);
		}
		// Si l'image n'est pas en base, on tente malgré tout de supprimer le fichier. Rien à perdre et cela sera plus propre.
		if(locBanque == null) {
			locFileProvider.appendMessage(Level.WARNING, String.format("L'image %s n'existe pas dans la base de données. Tentative de suppression du fichier.", locArgument));
			final Configuration locConfiguration = ConfigurationManager.getConfiguration();
			final String locImageDirectory = locConfiguration.getImageDirectory();
			deleteImage(locImageDirectory + locArgument);
			return "";
		}
		// On supprime l'image + l'entrée de la base
		deleteImage(locBanque.getDirectory());
		locBanqueManager.remove(locArgument);
		locFileProvider.appendMessage(Level.INFO, String.format("L'image %s a correctement été supprimé.", locArgument));
		return "";
	}
	
	/**
	 * 
	 * @param parPath
	 * @return
	 */
	private final boolean deleteImage(final String parPath) {
		final File locImage = new File(parPath);
		return locImage.delete();
	}

}
