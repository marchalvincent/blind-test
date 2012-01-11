package org.commons.logger;

import java.util.logging.Level;

/**
 * L'interface commune de tous les logger de l'application.
 * @author pitton
 *
 */
public interface InfoProvider {

	/**
	 * Ajoute et affiche un message à l'{@code InfoProvider}. Si le niveau spécifié est inférieur
	 * au niveau requis, le message n'est pas traité.
	 * @param parLevel {@link Level} le niveau du message.
	 * @param parMessage {@link String} le message à afficher.
	 * @return {@code InfoProvider} l'infoprovider qui a envoyé le message.
	 */
	InfoProvider appendMessage (final Level parLevel, final String parMessage);
	
	/**
	 * Ajoute et affiche un message à l'{@code InfoProvider} ainsi que l'exception spécifiée. Si le niveau
	 * spécifié est inférieur au niveau requis, le message n'est pas traité.
	 * @param parLevel {@link Level} le niveau du message.
	 * @param parMessage {@link String} le message à afficher.
	 * @param parThrowable {@link Throwable} une exception. 
	 * @return {@code InfoProvider} l'infoprovider qui a envoyé le message
	 */
	InfoProvider appendMessage (final Level parLevel, final String parMessage, final Throwable parThrowable);
	
	/**
	 * Retourne le {@link Level} minimum auquel un message peut être loggué.
	 * Si le niveau est {@link Level#OFF}, le message n'est pas loggué.
	 * @return {@link Level} le niveau minimum auquel un message peut être loggué.
	 */
	Level getMinLevel();
	
	/**
	 * Retourne vrai si le {@link Level} spécifié est suffisant pour afficher un message.
	 * @param parLevel {@link Level} le niveau à tester.
	 * @return {@code boolean} vrai si le {@link Level} spécifié est suffisant pour afficher un message.
	 */
	boolean isShowable(final Level parLevel);
	
}
