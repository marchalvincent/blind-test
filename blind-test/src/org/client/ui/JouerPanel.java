package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.client.ui.listeners.ValidListener;
import org.client.ui.listeners.boutonValidEntree;
import org.commons.entity.BanqueFacade;
import org.commons.logger.InfoProvider;

/**
 * panel pour jouer au jeu
 * @author francois
 *
 */
public class JouerPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private RenderedImage _currentImage;
	private JTextField champsReponse;
	private List<Observable> _observable;
	private String _login;
	private BoutonGris boutonQuitter;
	
	public JouerPanel (String parLogin) {
		_observable = new ArrayList<Observable>();
		_login = parLogin;
	}
	
	@Override
	public JouerPanel initPanel() {	
		
		//Bouton Quitter la partie
		boutonQuitter = new BoutonGris("Quitter la partie :(");
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		getContraintes().weighty = 0.1;
		getContraintes().anchor = GridBagConstraints.ABOVE_BASELINE;
		getContraintes().gridwidth = 2;
		getMarges().top = 15;
		getContraintes().insets = getMarges();
		this.add(boutonQuitter, getContraintes());
		
		//espace
		JLabel espace = new JLabel (" ");
		getContraintes().gridx = 0;
		getContraintes().gridy = 1;
		getContraintes().weighty = 1.0;
		getContraintes().gridwidth = GridBagConstraints.REMAINDER;
		this.add(espace, getContraintes());
		
		//Champs réponse
		champsReponse = new JTextField (30);
		getContraintes().gridx = 0;
		getContraintes().gridy = 2;
		getMarges().left = 15;
		getMarges().bottom = 15;
		getMarges().right = 15;
		getContraintes().insets = getMarges();
		getContraintes().anchor = GridBagConstraints.LAST_LINE_END;
		getContraintes().weighty = 0.1;
		getContraintes().gridwidth = 1;
		this.add(champsReponse, getContraintes());
		champsReponse.setFocusable(true);
		champsReponse.requestFocusInWindow();
		//Bouton Valider !
		final BoutonGris locBoutonValide = new BoutonGris ("Valider !");
		ValidListener validL = new ValidListener (this, locBoutonValide);
		locBoutonValide.addMouseListener(validL);
		_observable.add(validL);
		boutonValidEntree locBoutonValidEntree = new boutonValidEntree (this, null);
		champsReponse.addKeyListener(locBoutonValidEntree);
		_observable.add(locBoutonValidEntree);
		getContraintes().gridx = 1;
		getContraintes().gridy = 2;
		getMarges().bottom = 12;
		getContraintes().anchor = GridBagConstraints.LAST_LINE_START;
		this.add(locBoutonValide, getContraintes());		
		return this;
	}
	
	public BoutonGris getBoutonQuitter () {
		return boutonQuitter;
	}
	
	public final String getAnswer() {
		return champsReponse.getText();
	}
	
	public final List<Observable> getObservable() {
		return _observable;
	}
	
	public RenderedImage getCurrentImagePath () {
		return _currentImage;
	}
	
	private void setImagePath (final InfoProvider parInfoProvider, final String imagePath) {
		try {
			this._currentImage = BanqueFacade.instance().readImage(imagePath);
		} catch (IOException e) {
			parInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'afficher l'image %s", imagePath), e);
		}
	}
	
	public void newTest (final InfoProvider parInfoProvider, final String imagePath) {
		setImagePath(parInfoProvider, imagePath);
		champsReponse.setText("");
		this.validate();
		this.repaint();
	}
	
	public void clearText () {
		champsReponse.setText("");
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		if(_currentImage == null) return;
			
		g.drawImage((Image) _currentImage, 0, 0, Fenetre.instance().getWidth(), Fenetre.instance().getHeight(), null);
		g.setColor(Color.WHITE);
		//g.drawRect(20, 20, Fenetre.instance().getWidth() - 20, Fenetre.instance().getHeight() - 20);
	}
}
