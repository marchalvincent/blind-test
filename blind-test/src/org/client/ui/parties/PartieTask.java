package org.client.ui.parties;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.commons.cache.Caches;

public class PartieTask extends TimerTask {

	Timer timer;
	List <String> listParties;
	
	public PartieTask (Timer timer) {
		this.timer = timer;
	}
	
	
	@Override
	public void run() {
		listParties = Caches.parties().keys();
	    timer.cancel(); 
	    }


	public final List<String> getListParties() {
		return listParties;
	}


	public final void setListParties(List<String> listParties) {
		this.listParties = listParties;
	}
	}
