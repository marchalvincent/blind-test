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
	 * Retourne le répertoire dans lequel se trouve les images.
	 * @return {@link String} le répertoire dans lequel se trouve les images.
	 */
	String getImageDirectory();
	
	/**
	 * Retourne le chemin vers le fichier d'index
	 * @return {@link String} le chemin vers le fichier d'index.
	 */
	String getIndexFile();

	/**
	 * Modifie le chemin vers le fichier d'index.
	 * @param parIndexFile {@link String} le nouveau chemin d'index
	 */
	void setIndexFile(final String parIndexFile);
	
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
	 * Modifie le répertoire des images.
	 * @param parImageDirectory {@link String} le nouveau répertoire d'images.
	 */
	void setImageDirectory(final String parImageDirectory);
	
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
	
	/**
	 * Modifie la valeur du timer de refresh des parties en temps
	 */

	void setTimer(final String parValue);
	
	/**
	 * Retourne le timer par défaut du serveur.
	 * @return {@link Integer} le timer par défaut du serveur.
	 */
	
	Integer getTimer();
	
	/**
	 * Retourne le chemin du le fond d'écran de l'application.
	 * @return {@link String} le fond d'écran de l'application.
	 */
	String getBackgroundImage();
	
	/**
	 * Modifie le chemin du fond d'écran de l'application.
	 * @param parBackgroundImage le nouveau chemin du fond d'écran de l'application.
	 */
	void setBackgroundImage(final String parBackgroundImage);
}
