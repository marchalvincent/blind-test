package org.commons.cache;

import java.net.Socket;

import org.commons.entity.User;
import org.server.partie.Partie;

/**
 * Une classe pour obtenir des caches.
 * @author pitton
 *
 */
public final class Caches {
	
	final static public AbstractCache<String, User> user() {
		return UserCache.INSTANCE;
	}
	
	final static public AbstractCache<String, Partie> parties() {
		return PartieCacheLoader.INSTANCE;
	}
	
	final static public AbstractCache<User, Socket> createSocketCache() {
		return new UserSocketCache();
	}
	
	static private final class UserCache {
		static private final AbstractCache<String, User> INSTANCE = new UserConnectedCache();
	}
	
	static private final class PartieCacheLoader {
		static private final AbstractCache<String, Partie> INSTANCE = new PartieCache();
	}
	
	private Caches() {}
}
