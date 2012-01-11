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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<String> _listParties;
	private DefaultListModel<String> _model;
	private JList _jlist;
	
	public PartiesPanel (List<String> parListParties) {
		_listParties = parListParties;
	}
	
	@Override
	public PartiesPanel initPanel() {
		// TODO Auto-generated method stub
		_model = new DefaultListModel<String>();
		_jlist = new JList (_model);
		_jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_jlist.setAutoscrolls(true);
		refresh (_listParties);
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getContraintes().fill = GridBagConstraints.BOTH;
		this.add(_jlist, getContraintes());
		this.setBackground(Color.BLACK);
		
		BoutonGris boutonRejoindre = new BoutonGris ("Rejoindre");
		//TODO : a finir : mettre le login et tout dans rejoindre, pas passer en param la
		boutonRejoindre.addMouseListener(new RejoindreListener (boutonRejoindre));
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().anchor = GridBagConstraints.PAGE_END;
		this.add(boutonRejoindre, getContraintes());
		
		return this;
	}
	
	public void refresh (List<String> parListe) {
		_listParties = parListe;
		_model.clear();
		for (String i : _listParties) {
			_model.addElement(i);
		}
		//TODO : pour tester la liste des parties
		_model.addElement("Pour tester : Partie de machin");
		_model.addElement("Pour tester : Ma Partie");
	}
	
	@Override
	public void paintComponent (Graphics g) {
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
	}
}
