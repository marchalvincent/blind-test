package org.server.persistence;

import java.util.List;

/**
 * L'interface commune à tous les objets pouvant manipuler des entités.
 * @author pitton
 *
 * @param <T> une entité
 */
public interface Manager<T> {

	/**
	 * Ajoute l'entité spécifiée dans la base de données et renvoie la nouvelle entité.
	 * @param parObject {@code T} l'entité à sauvegarder
	 * @return {@code T} l'entité mise à jour.
	 */
	T add (final T parObject);
	
	/**
	 * Supprime l'entité dont l'identifiant est spécifié.
	 * @param parId {@code int} l'identifiant de l'objet à supprimer.
	 * @return {@code boolean} vrai si l'entité à supprimer.
	 */
	boolean remove (final int parId);
	
	/**
	 * Met à jour l'entité spécifiée et renvoie l'entité mise à jour.
	 * @param parObject {@code T} une entité à mettre à jour.
	 * @return {@code T} l'entité mise à jour
	 */
	T merge (final T parObject);
	
	/**
	 * Retourne l'entité dont l'identifiant est spécifié.
	 * @param parId {@code int} un identifiant.
	 * @return {@code T} l'entité dont l'identifiant est spécifié.
	 */
	T find(final int parId);
	
	/**
	 * Retourne l'entité dont le nom constant est spécifié.
	 * @param parName {@link String} le nom constant d'une entité.
	 * @return {@code T} l'entité dont le nom est spécifié.
	 */
	T find(final String parName);
	
	/**
	 * Retourne la {@link List} de toutes les entités.
	 * @return {@link List} la liste de toutes les entités.
	 */
	List<T> findAll();
	
}
