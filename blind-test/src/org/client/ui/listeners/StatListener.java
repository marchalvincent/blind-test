package org.client.ui.listeners;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import org.client.ui.AbstractPanel;
import org.client.ui.BoutonGris;
import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.StatMessage;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

/**
 * Affiche les stats
 * @author Takfarinas
 *
 */
public class StatListener extends AbstractBoutonListener {

	private final String _login;
	private  AbstractPanel _panel;
	public StatListener(final String parLogin, AbstractPanel panel, BoutonGris bouton) {
		super(bouton);

		_login = parLogin;
		_panel = panel;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);

		Configuration config = ConfigurationManager.getConfiguration();
		InfoProvider fileProvider = InfoProviderManager.getUiInfoProvider();
		String displayMessage ="";
		StatMessage locStatMessage = (StatMessage) EnumMessage.STAT.createMessage();
		locStatMessage.setLogin(_login);
		Socket socket = null;

		try {
			socket = new Socket(config.getHostName(), config.getPort());
			ReadWriterUtil.write(socket,locStatMessage);
			//on écoute la réponse
			IMessage messageRetour = null;
			messageRetour = ReadWriterUtil.read(socket);
			
			if (messageRetour instanceof StatMessage == false) {
				fileProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + messageRetour);
				SystemUtil.close(socket);
				return;
			}
			
			StatMessage msgStatRetour = (StatMessage) messageRetour;
			int locPercent = msgStatRetour.getVictoire()+msgStatRetour.getDefaite();
			if(locPercent == 0) locPercent= 1;
			
			int percentVictoire = (int)(msgStatRetour.getVictoire()/locPercent) * 100;
			int percentDefaite = (int)(msgStatRetour.getDefaite()/locPercent) * 100;
			displayMessage = "Nombre de victoires : "+msgStatRetour.getVictoire()+"\n"+"Nombre de défaites : "+msgStatRetour.getDefaite()+"\n"+"% Victoires : "+percentVictoire+"%"+"\n"+"% Défaites : "+percentDefaite+"%";
			
		} catch (IOException e1) {
			fileProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s", socket.getInetAddress().getHostAddress()), e1);
		}
		catch (Exception e2) {
			fileProvider.appendMessage(Level.SEVERE, String.format("Probleme : "), e2);
		}
		finally {
			SystemUtil.close(socket);
		}
		if (StringUtil.isNotEmpty(displayMessage)){
			JOptionPane.showMessageDialog(_panel, displayMessage);
		}
	}
}