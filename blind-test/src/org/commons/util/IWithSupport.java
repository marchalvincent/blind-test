package org.commons.util;

/**
 * Une interface marquant tous les objets pouvant renvoyer un message d'aide.
 * @author pitton
 *
 */
public interface IWithSupport {

	/**
	 * Retourne un {@link String} message à titre informatif.
	 * @return {@link String} un message d'information.
	 */
	String getSupport();
	
}
