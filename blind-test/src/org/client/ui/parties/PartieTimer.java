package org.client.ui.parties;

import java.util.Timer;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;

public class PartieTimer {

	Timer timer;
	private PartieTask pt;

	public PartieTimer (String login) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		timer = new Timer();
		pt = new PartieTask(login, locConfiguration);
		timer.schedule(pt, locConfiguration.getTimer());
	}

	public final Timer getTimer() {
		return timer;
	}

	public final void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public PartieTask getPartieTask () {
		return pt;
	}
}
