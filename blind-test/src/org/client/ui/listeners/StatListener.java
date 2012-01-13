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
import org.commons.util.ScoreUtil;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;

/**
 * Affiche les stats
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
		StringBuilder sb = new StringBuilder();
		
		StatMessage locStatMessage = (StatMessage) EnumMessage.STAT.createMessage();
		locStatMessage.setLogin(_login);
		Socket socket = null;

		try {
			//on créé la socket, puis on envoie la demande de stat
			socket = new Socket(config.getHostName(), config.getPort());
			ReadWriterUtil.write(socket, locStatMessage);
			
			//on écoute la réponse
			IMessage messageRetour = null;
			messageRetour = ReadWriterUtil.read(socket);
			
			if (messageRetour instanceof StatMessage == false) {
				fileProvider.appendMessage(Level.SEVERE, "Le type du message est incorrect. Le message reçu est : " + messageRetour);
				SystemUtil.close(socket);
				return;
			}
			
			StatMessage statMessage = (StatMessage) messageRetour;
			double locVictoires = statMessage.getVictoire();
			double locDefaites = statMessage.getDefaite();
			
			double locTotal = locVictoires + locDefaites;
			
			double locScore = ScoreUtil.score(locVictoires, locDefaites);
			
			if(locTotal == 0) {
				locTotal = 1;
			}
			final double locPercentVictoire = (locVictoires / locTotal) * 100;
			final double locPercentDefaite = (locDefaites / locTotal) * 100;

			sb.append("Nombre de victoires : " + (int) locVictoires + "\n");
			sb.append("Nombre de défaites : " + (int) locDefaites + "\n");
			sb.append("Victoires : " + Math.rint(locPercentVictoire) + "%"+"\n");
			sb.append("Défaites : " + Math.rint(locPercentDefaite) + "%\n");
			sb.append("Score total : " + (int) locScore + "\n");
			
			if (locVictoires*2 < locDefaites) {
				sb.append("T'es un looser :-p");
			}
			else if (locVictoires > locDefaites) {
				sb.append("Bien joué winner ;-)");
			}
			else {
				sb.append("Mouai... peut mieux faire !");
			}
			
		} catch (IOException e1) {
			fileProvider.appendMessage(Level.SEVERE, "Impossible de communiquer avec le serveur.", e1);
		}
		catch (Exception e2) {
			fileProvider.appendMessage(Level.SEVERE, "Impossible de lire dans la socket.", e2);
		}
		finally {
			SystemUtil.close(socket);
		}
		
		String displayMessage = sb.toString();
		if (StringUtil.isNotEmpty(displayMessage)) {
			JOptionPane.showMessageDialog(_panel, displayMessage);
		}
	}
}