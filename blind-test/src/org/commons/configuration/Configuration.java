package org.commons.configuration;

import java.nio.charset.Charset;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;

/**
 * Une interface pour définir une configuration pour l'application. Cette interface
 * implique que la configuration est définie dans un fichier de configuration.
 * @author pitton
 *
 */
public interface Configuration {

	/**
	 * Retourne le chemin vers le fichier de configuration.
	 * @return {@link String} le chemin vers le fichier de configuration.
	 */
	String getFileName();
	
	/**
	 * Retourne le {@link Level} par défaut pour stocker les messages.
	 * @return {@link Level} le niveau par défaut pour stocker les messages.
	 */
	Level getMinLevel();
	
	/**
	 * Retourne le {@link Charset} par défaut de l'application.
	 * @return {@link Charset} le charset par défaut de l'application.
	 */
	Charset getCharset();
	
	/**
	 * Retourne le nom du {@link Charset} utilisé par l'application.
	 * @return {@link String} le nom du {@link Charset} utilisé par l'application.
	 */
	String getCharsetName();
	
	/**
	 * Retourne le hostname, ou l'adresse ip, du serveur. Attention, à n'utiliser que par le client.
	 * @return {@link String} le hostname, ou l'adresse ip, du serveur.
	 */
	String getHostName();
	
	/**
	 * Retourne le port par défaut du serveur.
	 * @return {@link Integer} le port par défaut du serveur.
	 */
	Integer getPort();

	/**
	 * Modifie le port de l'application.
	 * @param parPort {@link Integer} le nouveau port de l'application.
	 */
	void setPort(final String parPort);
	
	/**
	 * Modifie l'adresse du serveur.
	 * @param parHostName {@link String} la nouvelle adresse du serveur.
	 */
	void setHostName(final String parHostName);
	
	/**
	 * Modifie le {@link Charset} de l'application.
	 * @param parCharset {@link String} le nouveau charset de l'application.
	 */
	void setCharset(final String parCharset);
	
	/**
	 * Modifie le {@link Level} des messages de l'application.
	 * @param parLevel {@link String} le nouveau niveau de l'application.
	 */
	void setMinLevel(final String parLevel);
	
	/**
	 * Charge la configuration de l'application.
	 * @return {@code Configuration} la configuration chargée.
	 * @throws BlindTestException Si le fichier de configuration n'existe pas.
	 */
	Configuration load() throws BlindTestException;
	
	/**
	 * Met à jour le fichier de configuration.
	 * @return {@code Configuration} la configuration.
	 * @throws BlindTestException Si l'écriture dans le fichier de configuration a échouée.
	 */
	Configuration refresh() throws BlindTestException;
	
	
	
}
