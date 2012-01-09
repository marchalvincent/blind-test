package org.server.persistence;

import java.util.Properties;

import org.commons.entity.Banque;

/**
 * Une clase utilitaire pour obtenir des {@link Manager}
 * @author pitton
 *
 */
public final class Managers {

	/**
	 * Retourne un {@link Manager} de {@link Banque}
	 * @return un {@link Manager} de {@link Banque}
	 */
	final static public Manager<Banque> createBanqueManager() {
		return ManagerBanque.INSTANCE;
	}
	
	/**
	 * Singleton
	 */
	static private final class ManagerBanque {
		static private final Manager<Banque> INSTANCE = new BanqueManager();
	}
	
	/**
	 * Retourne les {@link Properties} de la base de données à titre informatif.
	 * @return les {@link Properties} de la base de données à titre informatif.
	 */
	static public final Properties getDatabaseConfiguration() {
		return MySQLConnexion.instance().getProperties();
	}
	
	private Managers() {}
}
