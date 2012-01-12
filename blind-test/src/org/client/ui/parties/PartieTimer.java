package org.client.ui.parties;

import java.util.Timer;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;

public class PartieTimer {

	Timer timer;

	public PartieTimer () {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		timer = new Timer();
		timer.schedule(new PartieTask(), locConfiguration.getTimer());
	}

	public final Timer getTimer() {
		return timer;
	}

	public final void setTimer(Timer timer) {
		this.timer = timer;
	}

}
