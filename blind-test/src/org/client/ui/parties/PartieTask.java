package org.client.ui.parties;

import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;

import javax.swing.JFrame;

import org.client.ui.PartiesPanel;
import org.commons.cache.Caches;
import org.commons.message.ListGamesMessage;

public class PartieTask extends TimerTask {
	
	private JFrame _partiesWindow;
	private PartiesPanel _partiesPanel;
	
	public PartieTask () {
		_partiesWindow = new JFrame ("Parties");
		_partiesPanel = new PartiesPanel(Arrays.<String>asList("")).initPanel();
		_partiesWindow.setContentPane(_partiesPanel);
		_partiesWindow.setSize(300, 300);
		_partiesWindow.setVisible(true);
	}
	
	
	@Override
	public final void run() {
		//TODO : Fixer
		_partiesPanel.refresh(null);
		_partiesWindow.validate();
	}
}
