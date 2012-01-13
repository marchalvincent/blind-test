package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.client.ui.AccueilPanel;
import org.client.ui.BoutonGris;
import org.client.ui.Fenetre;
import org.commons.logger.InfoProviderManager;
import org.commons.message.IMessage;
import org.server.concurrent.ReadWriterUtil;

public class QuitterPartieListener extends AbstractBoutonListener {
	
	private String _login;
	private Socket socket;
	private IMessage message;
	
	public QuitterPartieListener(String parLogin, Socket socket, IMessage message, BoutonGris bouton) {
		super(bouton);
		_login = parLogin;
		this.socket = socket;
		this.message = message;
	}
	
	@Override
	public void mouseClicked (MouseEvent e) {
		super.mouseClicked(e);
		try {
			ReadWriterUtil.write(socket, message);
		} catch (IOException e1) {
			InfoProviderManager.getUiInfoProvider().appendMessage(Level.SEVERE, "Impossible de lire dans la socket");
		}
		Fenetre.instance().changePage(new AccueilPanel(_login).initPanel());
	}
}
