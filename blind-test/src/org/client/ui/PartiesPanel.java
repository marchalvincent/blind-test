package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.client.ui.listeners.RejoindreListener;

public class PartiesPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private List<String> _listParties;
	private DefaultListModel<String> _model;
	private JList<String> _jlist;
	private String _login;
	
	public PartiesPanel (String login, List<String> parListParties) {
		_listParties = parListParties;
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
		refresh (_listParties);
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
		
		//Focus Listener
		
		
		return this;
	}
	
	
	
	public void refresh (List<String> parListe) {
		_listParties = parListe;
		_model.clear();
		for (String i : _listParties) {
			_model.addElement(i);
		}
		_jlist.validate();
	}
	
	@Override
	public void paintComponent (Graphics g) {
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
