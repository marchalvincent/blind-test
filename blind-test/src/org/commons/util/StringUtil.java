package org.commons.util;

import org.apache.commons.lang3.StringUtils;

/**
 * Une classe utilitaire pour les {@link CharSequence}. 
 * @author pitton
 *
 */
public final class StringUtil {

	/**
	 * Retourne true si la chaine de caractères spécifiée est null ou vide.
	 * @param parValue {@link CharSequence} une chaîne de caractères
	 * @return {@code boolean} true si la chaine de caractère spécifiée est null ou vide.
	 */
	final static public boolean isEmpty(final CharSequence parValue) {
		return StringUtils.isEmpty(parValue);
	}
	
	/**
	 * Retourne true si la chaine de caractères spécifiée n'est ni null ni vide.
	 * @param parValue {@link CharSequence} une chaîne de caractères
	 * @return {@code boolean} true si la chaine de caractère spécifiée n'est ni null ni vide.
	 */
	final static public boolean isNotEmpty(final CharSequence parValue) {
		return StringUtils.isNotEmpty(parValue);
	}
	
	/**
	 * Retourne true si les deux chaines de caractères spécifiées sont identiques, en prenant
	 * en compte la casse.
	 * On considère donc que :
	 * <ul>
	 * 	<li>"AbC" et "abc" = false</li>
	 * 	<li>"abc" et "abc" = true</li>
	 * 	<li>null et null = true</li>
	 * </ul>
	 * @param parValue1 {@link CharSequence} une chaine de caractère
	 * @param parValue2 {@link CharSequence} une chaine de caractère
	 * @return {@code boolean} vrai si les deux {@link CharSequence} sont identiques en prenant en compte la casse.
	 */
	final static public boolean equals(final CharSequence parValue1, final CharSequence parValue2) {
		return StringUtils.equals(parValue1, parValue2);
	}
	
	/**
	 * Retourne true si les deux chaines de caractères spécifiées sont identiques, sans prendre
	 * en compte la casse.
	 * On considère donc que :
	 * <ul>
	 * 	<li>"AbC" et "abc" = true</li>
	 * 	<li>"abc" et "abc" = true</li>
	 * 	<li>null et null = true</li>
	 * </ul>
	 * @param parValue1 {@link CharSequence} une chaine de caractère
	 * @param parValue2 {@link CharSequence} une chaine de caractère
	 * @return {@code boolean} vrai si les deux {@link CharSequence} sont identiques sans prendre en compte la casse.
	 */
	final static public boolean equalsIgnoreCase(final CharSequence parValue1, final CharSequence parValue2) {
		return StringUtils.equalsIgnoreCase(parValue1, parValue2);
	}
	
	/**
	 * Capitalise la chaîne de caractère spécifiée. Cela implique que <b>"abc"</b>
	 * devient <b>"Abc"</b>.
	 * @param parValue {@link String} la chaine de caractère à capitaliser.
	 * @return {@link String} la chaine de caractère capitalisée.
	 */
	final static public String capitalize(final String parValue) {
		return StringUtils.capitalize(parValue);
	}
	
	/**
	 * Compare deux chaine de caractère et renvoie la différence numérique entre les deux, en prenant
	 * en compte la casse.
	 * Deux {@link String} null sont considérés comme identique.
	 * @param parValue1 {@link String} la chaine de caractère à comparer
	 * @param parValue2 {@link String} la chaine de caractère comparante
	 * @return {@code int} le résultat de la comparaison du premier {@link String} par le second.
	 */
	final static public int compareTo(final String parValue1, final String parValue2) {
		if(parValue1 == null) {
			return (parValue2 == null) ? 0 : -1;
		}
		return (parValue2 == null) ? 1 : parValue1.compareTo(parValue2); 
	}

	/**
	 * Compare deux chaine de caractère et renvoie la différence numérique entre les deux, sans prendre
	 * en compte la casse.
	 * Deux {@link String} null sont considérés comme identique.
	 * @param parValue1 {@link String} la chaine de caractère à comparer
	 * @param parValue2 {@link String} la chaine de caractère comparante
	 * @return {@code int} le résultat de la comparaison du premier {@link String} par le second.
	 */
	final static public int compareToIgnoreCase(final String parValue1, final String parValue2) {
		if(parValue1 == null) {
			return (parValue2 == null) ? 0 : -1;
		}
		return (parValue2 == null) ? 1 : parValue1.compareToIgnoreCase(parValue2); 
	}
	
	/**
	 * Retourne true si la chaine de caractères spécifiée peut être transformé
	 * en {@link Integer}. Cette méthode est préférable à l'emploi de 
	 * {@link Integer#parseInt(String)} car elle ne lève pas d'exception.
	 * @param parValue {@link CharSequence} une chaine de caractère
	 * @return {@code boolean} vrai si la chaine de caractères spécifiée peut être transformée
	 * en {@link Integer}
	 */
	final static public boolean isNumeric(final CharSequence parValue) {
		return StringUtils.isNumeric(parValue);
	}
	
	/**
	 * Convertit un {@link String} en {@link Integer} de manière safe. Si le {@link String}
	 * ne peut être convertie en {@link Integer}, cette méthode renvoie null.
	 * @param parValue {@link String} une chaine à convertir en {@link Integer}.
	 * @return {@link Integer} la chaine de caractères convertie en nombre.
	 */
	final static public Integer toInteger(final String parValue) {
		if(isNumeric(parValue)) {
			return Integer.parseInt(parValue);
		}
		return null;
	}
	
	private StringUtil() {}
}
