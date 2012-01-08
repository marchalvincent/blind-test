package org.server.monitor;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.EnumConfiguration;
import org.commons.exception.BlindTestException;
import org.commons.util.WithUtilities;

public class MonitorConfigurationCommand extends MonitorCommand {

	public MonitorConfigurationCommand(final String parCommand) {
		super(EnumMonitorCommand.CONFIGURATION, parCommand, null);
	}

	@Override
	public final String call() throws Exception {
		final EnumConfiguration locEnumConfiguration = WithUtilities.getByName(EnumConfiguration.values(), getConstName());
		if(locEnumConfiguration == null) throw new BlindTestException("La commande spécifiée est " + getConstName() + ". Elle n'existe pas.");
		
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		return locEnumConfiguration.getFormattedValue(locConfiguration);
	}

}
