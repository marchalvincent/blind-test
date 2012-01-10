package org.client.ui;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.JTextArea;

import org.client.ui.listeners.RetourMenuListener;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;

/**
 * panel pour afficher les regles du jeu
 * @author francois
 *
 */
public class ReglesPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private String fichierTxtRegles = "conf/regles.txt";
	
	public ReglesPanel () {
		super ();
	}
	
	@Override
	protected void initPanel () {
		
		//Lecture du fichier regles
		FileInputStream fis;
		final File locFile = new File(fichierTxtRegles);
		final byte[] locResult = new byte[(int) (locFile.length() + 1)];
		try {
			fis = new FileInputStream (fichierTxtRegles);
			fis.read(locResult);
		}
		catch (IOException e) {
		}
		
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final Charset locCharset = locConfiguration.getCharset(); 
		final String locRule = new String(locResult, locCharset);
		
		//Présentation
		JTextArea locArea = new JTextArea(locRule);
		locArea.setOpaque(false);
		locArea.setForeground(Color.WHITE);
		locArea.setEditable(false);
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		this.add(locArea, getContraintes());
		
		//Bouton Retour
		BoutonGris boutonRetour = new BoutonGris ("< Retour");
		boutonRetour.addMouseListener(new RetourMenuListener (boutonRetour));
		getContraintes().gridy = 1;
		this.add(boutonRetour, getContraintes());
		
	}
}
