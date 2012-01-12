package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

import org.client.ui.listeners.RejoindreListener;
import org.commons.entity.BanqueFacade;
import org.commons.util.StringUtil;

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
				
		return this;
	}
	
	
	
	public void refresh (final List<String> parListe) {
		_listParties = parListe;
		final int locSelectedIndex = _jlist.getSelectedIndex();
		_model.clear();
		for (final String locPartieName : _listParties) {
			System.out.println("partie panel refresh parties =>" + locPartieName);
			if (StringUtil.isEmpty(locPartieName))
				continue;
			_model.addElement(locPartieName);
		}
		_jlist.setSelectedIndex(locSelectedIndex);
		_jlist.validate();
	}
	
	@Override
	public void paintComponent (Graphics g) {
		try {
			RenderedImage image = BanqueFacade.instance().readImage("image/fond-parties.jpg");
			g.drawImage((Image) image, 0, 0, Fenetre.instance().getPartieWidth(), Fenetre.instance().getPartieHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
