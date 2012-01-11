package org.client.ui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * class log du client
 * @author francois
 *
 */
public class LogClient {
	
	JScrollPane scrollPane;
	private JTextArea log;
	private StringBuilder txtLog;
	
	public LogClient () {
		txtLog = new StringBuilder ();
		init ();
	}
	
	protected void init () {
		log = new JTextArea ();
		log.setEditable(false);
		log.setOpaque(false);
		log.setForeground(Color.WHITE);
		log.setVisible(true);
		scrollPane = new JScrollPane(log, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setPreferredSize(new Dimension(700, 300));
	}
	
	protected final JScrollPane getTextArea() {
		return scrollPane;
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
