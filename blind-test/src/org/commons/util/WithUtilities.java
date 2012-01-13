package org.commons.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Une classe utilitaire pour toutes les interfaces de type IWith*
 * Cette classe permet de factoriser les méthodes de type recherche, tri, ... pour tous les objets
 * dérivant des interface de type IWith*.
 * @see IWithName
 * @see IWithId
 * @author pitton
 *
 */
public final class WithUtilities {

	/**
	 * Retourne le {@code T} dans le tableau spécifié ayant le nom spécifié.
	 * @param parArray {@code T[]} un tableau d'objet.
	 * @param parName {@link String} le nom à rechercher
	 * @return {@code T} l'objet dont le nom constant est spécifié, null s'il n'a pas été trouvé.
	 */
	final static public <T extends IWithName> T getByName(final T[] parArray, final String parName) {
		return getByName(Arrays.<T>asList(parArray), parName);
	}
	
	/**
	 * Retourne le {@code T} dans la collection spécifiée ayant le nom spécifié.
	 * @param parCollection {@link Collection} une collection d'objets.
	 * @param parName {@link String} le nom à rechercher
	 * @return {@code T} l'objet dont le nom constant est spécifié, null s'il n'a pas été trouvé.
	 */
	final static public <T extends IWithName> T getByName(final Collection<T> parCollection, final String parName) {
		if(StringUtil.isEmpty(parName) || parCollection== null) return null;
		
		for(final T locValue : parCollection) {
			if(StringUtil.equals(parName, locValue.getConstName())) {
				return locValue;
			}
		}
		return null;
	}
	
	/**
	 * Retourne le {@code T} du tableau spécifié ayant l'identifiant spécifié.
	 * @param parArray {@code T[]} un tableau d'éléments.
	 * @param parId {@link Integer} un identifiant
	 * @return {@code T} l'objet dont l'identifiant est spécifié, null sinon.
	 */
	final static public <T extends IWithId> T getById(final T[] parArray, final Integer parId) {
		return getById(Arrays.<T>asList(parArray), parId);
	}
	

	/**
	 * Retourne le {@code T} de la collection spécifiée ayant l'identifiant spécifié.
	 * @param parCollection {@link Collection} une collection d'éléments.
	 * @param parId {@link Integer} un identifiant
	 * @return {@code T} l'objet dont l'identifiant est spécifié, null sinon.
	 */
	final static public <T extends IWithId> T getById(final Collection<T> parCollection, final Integer parId) {
		if(parId == null || parCollection == null) return null;
		
		for(final T locValue : parCollection) {
			if(parId.equals(locValue.getId())) {
				return locValue;
			}
		}
		return null;
	}
	
	/**
	 * Tri la {@link List} spécifiée en fonction du nom constant de la {@link List} d'éléments.
	 * @see IWithName#getConstName()
	 * @param parList {@link List} une liste à trier
	 */
	final static public <T extends IWithName> void sortName(final List<T> parList) {
		Collections.sort(parList, WithNameComparatorLoader.INSTANCE);
	}
	
	/**
	 * Tri le tableau spécifié en fonction du nom constant de chaque élément
	 * @see IWithName#getConstName()
	 * @param parArray {@code T[]} un tableau d'éléments
	 */
	final static public <T extends IWithName> void sortName(final T[] parArray) {
		Arrays.sort(parArray, WithNameComparatorLoader.INSTANCE);
	}
	
	/**
	 * Tri la {@link List} en fonction de l'identifiant de chaque élément de la {@link List}.
	 * @see IWithId#getId()
	 * @param parList {@link List} une liste à trier
	 */
	final static public <T extends IWithId> void sortId(final List<T> parList) {
		Collections.sort(parList, WithIdComparatorLoader.INSTANCE);
	}

	/**
	 * Tri le {@code T[]} tableau en fonction de l'identifiant de chaque élément du {@code T[]}
	 * @see IWithId#getId()
	 * @param parArray {@code T[]} un tableau d'éléments à trier
	 */
	final static public <T extends IWithId> void sortId(final T[] parArray) {
		Arrays.sort(parArray, WithIdComparatorLoader.INSTANCE);
	}
	
	/**
	 * Un singleton pour les {@link Comparator} par identifiant.
	 * @see WithIdComparator
	 * @author pitton
	 */
	static private final class WithIdComparatorLoader {
		static private final WithIdComparator INSTANCE = new WithIdComparator();
	}
	
	/**
	 * Un singleton pour les {@link Comparator} par nom.
	 * @see WithNameComparator
	 * @author pitton
	 */
	static private final class WithNameComparatorLoader {
		static private final WithNameComparator INSTANCE = new WithNameComparator();
	}
	
	private WithUtilities() {}
}
