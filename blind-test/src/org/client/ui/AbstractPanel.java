package org.client.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;

public abstract class AbstractPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagLayout layout = new GridBagLayout();
	private GridBagConstraints contraintes = new GridBagConstraints();
	private Insets marges = new Insets(0, 0, 0, 0);
	
	protected abstract void initPanel ();
	
	@Override
	public GridBagLayout getLayout () {
		return layout;
	}
	
	protected GridBagConstraints getContraintes () {
		return contraintes;
	}
	
	protected Insets getMarges () {
		return marges;
	}
}
