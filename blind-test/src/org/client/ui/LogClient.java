package org.client.ui;

import java.awt.Color;

import javax.swing.JTextArea;

/**
 * class log du client
 * @author francois
 *
 */
public class LogClient {

	private JTextArea log;
	private StringBuffer txtLog;
	
	public LogClient () {
		init ();
	}
	
	protected void init () {
		log = new JTextArea ();
		log.setOpaque(false);
		log.setForeground(Color.WHITE);
		log.setVisible(true);
	}
	
	public JTextArea getInstance () {
		return log;
	}
	
	public void ecrire (String texte) {
		txtLog.append(texte);
		txtLog.append("\n");
		log.setText(txtLog.toString());
	}
	
	public String getText () {
		return log.getText();
	}
}
