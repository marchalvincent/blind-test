package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;

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
	
	public JouerPanel (final RenderedImage imagePath) {
		super ();
		this._currentImage = imagePath;
		_observable = new ArrayList<Observable>();
	}
	
	public JouerPanel () {
		this (null);
	}
	
	@Override
	public JouerPanel initPanel() {		
		//Champs réponse
		champsReponse = new JTextField (30);
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		this.add(champsReponse, getContraintes());
		
		//Bouton Valider !
		BoutonGris boutonValid = new BoutonGris ("Valider !");
		ValidListener validL = new ValidListener (this, boutonValid);
		boutonValid.addMouseListener(validL);
		_observable.add(validL);
		boutonValidEntree bve = new boutonValidEntree (this, null);
		boutonValid.addKeyListener(bve);
		_observable.add(bve);
		getContraintes().gridx = 1;
		getContraintes().gridy = 0;
		this.add(boutonValid, getContraintes());
		
		return this;
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
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		if(_currentImage == null) return;
			
		g.drawImage((Image) _currentImage, 0, 0, Fenetre.instance().getWidth(), Fenetre.instance().getHeight(), null);
		g.setColor(Color.WHITE);
		//g.drawRect(20, 20, Fenetre.instance().getWidth() - 20, Fenetre.instance().getHeight() - 20);
	}
}
