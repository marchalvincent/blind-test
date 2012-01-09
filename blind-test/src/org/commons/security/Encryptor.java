package org.commons.security;

import org.commons.logger.InfoProvider;
import org.commons.util.IWithName;

/**
 * Une interface commune à tous les objets pouvant crypter une chaine de caractères.
 * @author pitton
 *
 */
public interface Encryptor extends IWithName{

	/**
	 * Crypte la chaine de caractères spécifiées et retourne le résultat.
	 * @param parValue {@link String} une chaine à crypter.
	 * @return {@link String} la chiane cryptée.
	 */
	String encrypt(final String parValue);
	
	/**
	 * Retourne l'{@link InfoProvider} de la classe.
	 * @return l'{@link InfoProvider} de la classe.
	 */
	InfoProvider getInfoProvider();
	
}
