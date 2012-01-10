package org.server.monitor;

import java.util.List;

import org.commons.cache.AbstractCache;
import org.commons.cache.Caches;
import org.commons.entity.User;


public final class MonitorUserConnected extends MonitorCommand {

	protected MonitorUserConnected(String parCommandName) {
		super(EnumMonitorCommand.USER, parCommandName, null);
	}

	@Override
	public final String call() throws Exception {
		final AbstractCache<String, User> locCache = Caches.user();
		final List<String> locUserList = locCache.keys();
		final StringBuilder locBuilder = new StringBuilder();
		locBuilder.append("Les utilisateurs connectés sont : ").append(locUserList.toString());
		return locBuilder.toString();
	}

}
