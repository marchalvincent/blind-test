package org.commons.downloader;

import java.util.concurrent.Callable;

/**
 * Une interface pour tous les objets capable d'effectuer un téléchargement
 * @author pitton
 *
 */
public interface Downloader extends Callable<Boolean> {

	/**
	 * Effectue un téléchargement
	 * @return {@link Boolean} vrai si le téléchargement s'est bien passé
	 */
	Boolean download();
}
