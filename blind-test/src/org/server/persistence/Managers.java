package org.server.persistence;

import java.util.Properties;

import org.commons.entity.Banque;
import org.commons.entity.Stat;

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
	
	final static public Manager<Stat> createStatManager() {
		return ManagerStat.INSTANCE;
	}
	/**
	 * Singleton
	 */
	static private final class ManagerBanque {
		static private final Manager<Banque> INSTANCE = new BanqueManager();
	}
	
	static private final class ManagerStat {
		static private final Manager<Stat> INSTANCE = new StatManager();
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
