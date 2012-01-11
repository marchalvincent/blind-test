package org.client.ui.parties;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.client.ui.PartiesPanel;
import org.commons.cache.Caches;

public class PartieTask extends TimerTask {
	
	
	Timer timer;
	List <String> listParties;
	private JFrame _partiesWindow;
	private PartiesPanel _partiesPanel;
	
	public PartieTask (Timer timer) {
		this.timer = timer;
		listParties = Caches.parties().keys();
		_partiesWindow = new JFrame ("Parties");
		_partiesPanel = new PartiesPanel(listParties).initPanel();
		_partiesWindow.setContentPane(_partiesPanel);
		_partiesWindow.setSize(300, 300);
		_partiesWindow.setVisible(true);
	}
	
	
	@Override
	public void run() {
		listParties = Caches.parties().keys();
		_partiesPanel.refresh(listParties);
		_partiesWindow.validate();
	    timer.cancel(); 
	    }


	public final List<String> getListParties() {
		return listParties;
	}


	public final void setListParties(List<String> listParties) {
		this.listParties = listParties;
	}
	}
