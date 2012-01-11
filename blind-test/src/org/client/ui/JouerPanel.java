package org.client.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Observable;

import javax.swing.JTextField;

import org.client.ui.listeners.AbstractBoutonListener;
import org.client.ui.listeners.ValidListener;
import org.client.ui.listeners.boutonValidEntree;
import org.commons.entity.BanqueFacade;
import org.commons.util.StringUtil;

/**
 * panel pour jouer au jeu
 * @author francois
 *
 */
public class JouerPanel extends AbstractPanel {

	private static final long serialVersionUID = 1L;
	private String imagePath;
	private JTextField champsReponse;
	private AbstractBoutonListener _observable;
	
	public JouerPanel (String imagePath) {
		super ();
		this.imagePath = imagePath;
	}
	
	public JouerPanel () {
		this ("");
	}
	
	@Override
	protected void initPanel() {		
		//Champs réponse
		champsReponse = new JTextField (30);
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		this.add(champsReponse, getContraintes());
		
		//Bouton Valider !
		BoutonGris boutonValid = new BoutonGris ("Valider !");
		_observable = new ValidListener (this, boutonValid);
		boutonValid.addMouseListener(_observable);
		boutonValid.addKeyListener(new boutonValidEntree ());
		getContraintes().gridx = 1;
		getContraintes().gridy = 0;
		this.add(boutonValid, getContraintes());
	}
	
	public final String getAnswer() {
		return champsReponse.getText();
	}
	
	public final Observable getObservable() {
		return _observable;
	}
	
	public String getCurrentImagePath () {
		return imagePath;
	}
	
	private void setImagePath (String imagePath) {
		this.imagePath = imagePath;
	}
	
	public void newTest (String imagePath) {
		setImagePath(imagePath);
		champsReponse.setText("");
		this.validate();
	}
	
	@Override
	public void paintComponent (Graphics g) {
		super.paintComponent(g);
		if(StringUtil.isEmpty(imagePath)) return;
		
		try {
			RenderedImage image = BanqueFacade.instance().readImage(imagePath);
			g.drawImage((Image) image, 0, 0, Fenetre.instance().getWidth(), Fenetre.instance().getHeight(), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.setColor(Color.WHITE);
		//g.drawRect(20, 20, Fenetre.instance().getWidth() - 20, Fenetre.instance().getHeight() - 20);
	}
}
