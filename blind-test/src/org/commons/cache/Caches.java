package org.commons.cache;

import java.net.Socket;

/**
 * Une classe pour obtenir des caches.
 * @author pitton
 *
 */
public final class Caches {

	final static public AbstractCache<String, Socket> user() {
		return UserCache.INSTANCE;
	}
	
	static private final class UserCache {
		static private final AbstractCache<String, Socket> INSTANCE = new UserConnectedCache();
	}
	
	private Caches() {}
}
