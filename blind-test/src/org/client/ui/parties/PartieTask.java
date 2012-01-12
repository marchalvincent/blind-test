package org.client.ui.parties;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;
import java.util.TimerTask;
import java.util.logging.Level;

import javax.swing.JFrame;

import org.client.ui.PartiesPanel;
import org.commons.configuration.Configuration;
import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.message.ListGamesMessage;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

public class PartieTask extends TimerTask {
	
	private JFrame _partiesWindow;
	private PartiesPanel _partiesPanel;
	private final Configuration _configuration;
	
	public PartieTask (final Configuration parConfiguration) {
		_configuration = parConfiguration;
		_partiesWindow = new JFrame ("Parties");
		_partiesPanel = new PartiesPanel(Arrays.<String>asList("")).initPanel();
		_partiesWindow.setContentPane(_partiesPanel);
		_partiesWindow.setSize(300, 300);
		_partiesWindow.setVisible(true);
	}
	
	
	@Override
	public final void run() {
		final InfoProvider locInfoProvider = InfoProviderManager.getUiInfoProvider();
		
		Socket locSocket = null;
		IMessage messageDemande;
		try {
			//On créé la socket et on envoie le message de demande des parties vide
			locSocket = new Socket(_configuration.getHostName(), _configuration.getPort());
			
			messageDemande = EnumMessage.LIST_GAMES.createMessage();
			ReadWriterUtil.write(locSocket, messageDemande);
			
			//On attend la réponse
			IMessage locMessageRetour = ReadWriterUtil.read(locSocket);
			if (locMessageRetour instanceof ListGamesMessage == false) {
				locInfoProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + locMessageRetour);
				SystemUtil.close(locSocket);
				return;
			}
			ListGamesMessage locGameMessage = (ListGamesMessage) locMessageRetour;
			List<String> listGames = locGameMessage.getListGames();
			
			_partiesPanel.refresh(listGames);
			_partiesWindow.validate();

		} catch (UnknownHostException e) {
			locInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible de trouver le serveur %s", _configuration.getHostName()));
		} catch (IOException e) {
			locInfoProvider.appendMessage(Level.SEVERE, "Impossible d'envoyer la demande des listes des parties.");
		} catch (ClassNotFoundException e) {
			locInfoProvider.appendMessage(Level.SEVERE, "Impossible de lire la listes des parties dans la socket.");
		} finally {
			SystemUtil.close(locSocket);
		}
		
	}
}
