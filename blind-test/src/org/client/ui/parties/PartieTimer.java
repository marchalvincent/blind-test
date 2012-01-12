package org.client.ui.parties;

import java.util.Timer;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;

public class PartieTimer {

	private Timer timer;

	public PartieTimer (String login) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		timer = new Timer();
		timer.schedule(new PartieTask(login, locConfiguration), locConfiguration.getTimer());
	}
}
