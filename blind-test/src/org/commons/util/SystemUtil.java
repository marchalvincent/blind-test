package org.commons.util;


import java.io.Closeable;
import java.io.IOException;

/**
 * Une classe utilitaire pour tous les appels systèmes.
 * @author pitton
 *
 */
public final class SystemUtil {

	/**
	 * Eteins l'application.
	 */
	static final public void exit() {
		System.exit(-1);
	}
	
	/**
	 * Ferme un {@link Closeable} et renvoie l'{@link Exception} levée, s'il y en a eu une.
	 * @param parCloseable {@link Closeable} un objet à fermer.
	 * @see Closeable
	 * @return {@link Exception} l'exception levée lors de la fermeture du {@link Closeable}, ou null s'il n'y en a pas eu.
	 */
	static final public Exception close(final Closeable parCloseable) {
		if(parCloseable == null) return null;
		
		try {
			parCloseable.close();
		} catch (IOException locException) {
			return locException;
		}
		return null;
	}
	
	private SystemUtil() {}
}
