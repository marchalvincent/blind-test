package org.client.ui.parties;

import java.util.Timer;

import javax.swing.JFrame;

import org.client.ui.PartiesPanel;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;

public class PartieTimer {

	private Timer _timer;
	
	public PartieTimer (String login, JFrame window, PartiesPanel panel) {
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		_timer = new Timer();
		PartieTask pt = new PartieTask(login, locConfiguration, window, panel);
		_timer.scheduleAtFixedRate(pt, 100, locConfiguration.getTimer());
	}
	
	public final void cancel() {
		_timer.cancel();
	}
	
}
