package org.client.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JTextArea;

import org.client.ui.listeners.RetourMenuListener;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;


public class ReglesPanel extends AbstractPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private String fichierTxtRegles = "conf/regles.txt";
	
	public ReglesPanel () {
		initPanel ();
	}
	
	@Override
	protected void initPanel () {
		setLayout(getLayout());
		
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
		locArea.setEditable(false);
		getContraintes().gridx = 0;
		getContraintes().gridy = 0;
		this.add(locArea, getContraintes());
		
		//Bouton Retour
		JButton boutonRetour = new JButton ("< Retour");
		boutonRetour.addMouseListener(new RetourMenuListener ());
		getContraintes().gridy = 1;
		this.add(boutonRetour, getContraintes());
		
	}
}
