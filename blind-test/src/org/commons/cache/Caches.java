package org.commons.cache;

import org.commons.entity.User;

/**
 * Une classe pour obtenir des caches.
 * @author pitton
 *
 */
public final class Caches {

	final static public AbstractCache<String, User> user() {
		return UserCache.INSTANCE;
	}
	
	static private final class UserCache {
		static private final AbstractCache<String, User> INSTANCE = new UserConnectedCache();
	}
	
	private Caches() {}
}
