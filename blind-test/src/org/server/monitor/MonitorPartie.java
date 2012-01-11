package org.server.monitor;

import java.util.List;

import org.commons.cache.AbstractCache;
import org.commons.cache.Caches;
import org.commons.entity.User;
import org.commons.util.WithUtilities;
import org.server.partie.Partie;

public final class MonitorPartie extends MonitorCommand {

	protected MonitorPartie(final String parCommandName) {
		super(EnumMonitorCommand.PARTIE, parCommandName, null);
	}

	@Override
	public final String call() throws Exception {
		final AbstractCache<String, Partie> locPartieCache = Caches.parties();
		final List<Partie> locPartieList = locPartieCache.values();
		WithUtilities.sortName(locPartieList);
		final StringBuilder locBuilder = new StringBuilder();
		for(final Partie locPartie : locPartieList) {
			locBuilder.append(" - Partie \"").append(locPartie.getConstName()).append("\"\n");
			final List<User> locUsers = locPartie.getUsers();
			WithUtilities.sortName(locUsers);
			locBuilder.append("\t Joueurs : ").append(locUsers).append("\n");
		}
		return locBuilder.toString();
	}
}
