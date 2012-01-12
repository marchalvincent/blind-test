package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import org.client.ui.BoutonGris;
import org.client.ui.ConnexionPanel;
import org.client.ui.Fenetre;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.DisconnectMessage;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.server.concurrent.ReadWriterUtil;

public class DeconnexionListener extends AbstractBoutonListener {

	private String _login;

	public DeconnexionListener(BoutonGris bouton, String parLogin) {
		super(bouton);
		_login = parLogin;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		Configuration conf = ConfigurationManager.getConfiguration();
		InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();

		Socket socket;
		try {
			socket = new Socket(conf.getHostName(), conf.getPort());

			IMessage locMessage = EnumMessage.DISCONNECT.createMessage();
			
			((DisconnectMessage) locMessage).setLogin(_login);

			ReadWriterUtil.write(socket, locMessage);
			Fenetre.instance().changePage(new ConnexionPanel().initPanel());

		} catch (IOException e1) {
			locInfoProvider.appendMessage(Level.SEVERE, "Impossible de communiquer avec le serveur.");
		}

	}
}
