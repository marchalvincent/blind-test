package org.client.ui;

import java.awt.Color;

import javax.swing.JTextArea;

/**
 * class log du client
 * @author francois
 *
 */
public class LogClient {

	private static JTextArea log;
	private static StringBuffer txtLog;
	
	public static JTextArea getInstance () {
		if (log == null) {
			log = new JTextArea ();
			log.setOpaque(false);
			log.setForeground(Color.WHITE);
			log.setVisible(true);
		}
		return log;
	}
	
	public static StringBuffer getStringBuffer () {
		return txtLog;
	}
	
	public static void update () {
		log.setText(txtLog.toString());
	}
}
