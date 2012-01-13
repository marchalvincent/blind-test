package org.client.ui;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.client.main.ClientManager;
import org.client.ui.parties.PartieTimer;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.DisconnectMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

/**
 * Classe contenant un singleton de la fenetre de
 * l'application.
 *
 */
public class Fenetre extends WindowAdapter {

	static private final Fenetre INSTANCE = new Fenetre();
	private JFrame fenetre;
	private JFrame fenetreParties;
	private PartiesPanel _partiesPanel;
	private PartieTimer _partieTimer;
	
	static public final Fenetre instance() {
		return INSTANCE;
	}
	
	private Fenetre () {
		fenetre = new JFrame ("Blind Test");
	}
	
	public void initFenetre () {
		fenetre.setSize(800, 700);
		fenetre.setMinimumSize(new Dimension (800, 700));
		fenetre.setContentPane(new ConnexionPanel().initPanel());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setVisible(true);
		fenetre.toFront();
		fenetre.addWindowFocusListener(new WindowFocusListener() {
			
			@Override
			public void windowLostFocus(WindowEvent e) {}
			
			@Override
			public void windowGainedFocus(WindowEvent e) {
				if(fenetreParties != null) {
					fenetreParties.dispose();
					fenetreParties = null;
				}
			}
		});
		fenetre.addWindowListener(this);
	}
	
	public void changePage (JPanel nouveau) {
		fenetre.setContentPane(nouveau);
		fenetre.validate();
	}
	
	public int getPartieHeight () {
		return fenetreParties.getHeight();
	}
	
	public int getPartieWidth() {
		return fenetreParties.getWidth();
	}
	
	public void chargeListParties (final String parLogin) {
		fenetreParties = new JFrame ("Parties");
		_partiesPanel = new PartiesPanel(parLogin).initPanel();
		fenetreParties.setContentPane(_partiesPanel);
		fenetreParties.setSize(300, 300);
		fenetreParties.setVisible(true);
		fenetreParties.setLocation(800, 300);
		fenetreParties.addWindowListener(new WindowAdapter() {
			
		    public final void windowOpened(WindowEvent e) {
		    	if(_partieTimer != null) {
		    		_partieTimer.cancel();
		    		_partieTimer = null;
		    	}
		    	_partieTimer = new PartieTimer(parLogin, fenetreParties, _partiesPanel);
		    }
			
			public final void windowClosing(final WindowEvent parEvent) {
				 if(_partieTimer != null) {
					 _partieTimer.cancel();
					 _partieTimer = null;
				 }
			 }
			
			public final void windowClosed(final WindowEvent parEvent) {
				 if(_partieTimer != null) {
					 _partieTimer.cancel();
					 _partieTimer = null;
				 }
			 }
		});
	}
	
	public JFrame getPartieWindow () {
		return fenetreParties;
	}
	
	public int getHeight () {
		return fenetre.getHeight();
	}
	
	public int getWidth () {
		return fenetre.getWidth();
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		closeWindow();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		closeWindow();
	}
	
	private void closeWindow() {
		IMessage message = EnumMessage.DISCONNECT.createMessage();
		DisconnectMessage disconnectMessage = (DisconnectMessage) message;
		disconnectMessage.setLogin(ClientManager.getLogin());
		
		Configuration conf = ConfigurationManager.getConfiguration();
		InfoProvider info = InfoProviderManager.getUiInfoProvider();
		
		if (ClientManager.getLogin() != "") {
			try {
				Socket socket = new Socket(conf.getHostName(), conf.getPort());
				ReadWriterUtil.write(socket, disconnectMessage);
				SystemUtil.close(socket);
			} catch (IOException e) {
				info.appendMessage(Level.SEVERE, "Impossible de spécifier au serveur que l'utilisateur s'est déconnecté.");
			}
		}
		
		
	}
}
