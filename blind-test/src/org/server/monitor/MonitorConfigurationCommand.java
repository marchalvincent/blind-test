package org.server.monitor;

import org.commons.configuration.EnumConfiguration;
import org.commons.exception.BlindTestException;
import org.commons.util.WithUtilities;

/**
 * L'implémentation d'un {@link MonitorCommand} pour obtenir des informations sur la configuration.
 * @author pitton
 *
 */
public final class MonitorConfigurationCommand extends MonitorCommand {

	public MonitorConfigurationCommand(final String parCommand) {
		super(EnumMonitorCommand.CONFIGURATION, parCommand, null);
	}

	@Override
	public final String call() throws Exception {
		final EnumConfiguration locEnumConfiguration = WithUtilities.getByName(EnumConfiguration.values(), getConstName());
		if(locEnumConfiguration == null) throw new BlindTestException("La commande spécifiée est " + getConstName() + ". Elle n'existe pas.");
		
		return locEnumConfiguration.getSupport();
	}

}
