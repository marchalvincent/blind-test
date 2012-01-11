package org.client.ui.parties;

import java.util.Timer;

public class PartieTimer {

	Timer timer;
	
public PartieTimer (int seconds) {
    timer = new Timer();
    timer.schedule(new PartieTask(timer), seconds*1000);
}

public final Timer getTimer() {
	return timer;
}

public final void setTimer(Timer timer) {
	this.timer = timer;
}

}
