package org.server.monitor;

import org.commons.util.WithUtilities;
import org.server.persistence.EnumDatabaseProperties;

/**
 * L'implémentation d'un {@link MonitorCommand} pour obtenir des informations sur la base de données.
 * @author pitton
 *
 */
public final class MonitorDatabaseCommand extends MonitorCommand {

	protected MonitorDatabaseCommand(String parCommandName) {
		super(EnumMonitorCommand.DATABASE, parCommandName, null);
	}

	@Override
	public final String call() throws Exception {
		final EnumDatabaseProperties locProperties = WithUtilities.getByName(EnumDatabaseProperties.values(), _commandName);
		assert(locProperties != null);
		return locProperties.getSupport();
	}

}
