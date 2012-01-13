package org.client.ui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.client.ui.listeners.RejoindreListener;
import org.commons.util.StringUtil;

public class PartiesPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private DefaultListModel<String> _model;
	private JList<String> _jlist;
	private String _login;
	
	public PartiesPanel (String login) {
		_login = login;
	}
	
	@Override
	public PartiesPanel initPanel() {
		_model = new DefaultListModel<String>();
		_jlist = new JList<String> (_model);
		_jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_jlist.setAutoscrolls(true);
		_jlist.setOpaque(false);
		_jlist.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getContraintes().fill = GridBagConstraints.BOTH;
		this.add(_jlist, getContraintes());
		this.setBackground(Color.BLACK);
		
		BoutonGris boutonRejoindre = new BoutonGris ("Rejoindre");

		boutonRejoindre.addMouseListener(new RejoindreListener (_login, _jlist, boutonRejoindre));
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().anchor = GridBagConstraints.PAGE_END;
		this.add(boutonRejoindre, getContraintes());
				
		return this;
	}
	
	
	
	public void refresh (final List<String> parListe) {
		final int locSelectedIndex = _jlist.getSelectedIndex();
		_model.clear();
		for (final String locPartieName : parListe) {
			if (StringUtil.isEmpty(locPartieName)) continue;
			_model.addElement(locPartieName);
		}
		_jlist.setSelectedIndex(locSelectedIndex);
		_jlist.validate();
	}
}
