package org.client.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Classe representant le panel de connexion
 * @author francois
 *
 */
public class ConnexionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GridBagLayout layout;
	private GridBagConstraints contraintes;
	private Insets marges;
	
	public ConnexionPanel () {
		layout = new GridBagLayout ();
		contraintes = new GridBagConstraints ();
		marges = new Insets (0, 15, 15, 15);
		initPanel ();
	}
	
	protected void initPanel () {
		this.setLayout(layout);
		
		//Image de Présentation
		
		
		//Label Login
		JLabel txtLogin = new JLabel ("Login");
		contraintes.gridx = 0;
		contraintes.gridy = 1;
		contraintes.weightx = (double) 0.5;
		contraintes.anchor = GridBagConstraints.LINE_END;
		contraintes.insets = marges;
		this.add(txtLogin, contraintes);
		
		//Champs Login
		JTextField champsLogin = new JTextField (15);
		contraintes.gridx = 1;
		contraintes.gridy = 1;
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		contraintes.anchor = GridBagConstraints.LINE_START;
		this.add(champsLogin, contraintes);
		
		//Label Mot de passe
		JLabel txtMdp = new JLabel ("Mot de passe");
		contraintes.gridx = 0;
		contraintes.gridy = 2;
		contraintes.gridwidth = 1;
		contraintes.anchor = GridBagConstraints.LINE_END;
		this.add(txtMdp, contraintes);
		
		//Champs Mot de passe
		JPasswordField champsMdp = new JPasswordField (15);
		contraintes.gridx = 1;
		contraintes.gridy = 2;
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		contraintes.anchor = GridBagConstraints.LINE_START;
		this.add(champsMdp, contraintes);
		
		//Bouton Connexion
		JButton boutConn = new JButton ("Connexion");
		boutConn.addMouseListener(new MouseListener () {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				Fenetre.changePage(new AccueilPanel ());
			}
			
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
		});
		contraintes.gridx = 0;
		contraintes.gridy = 3;
		contraintes.gridwidth = GridBagConstraints.REMAINDER;
		contraintes.anchor = GridBagConstraints.CENTER;
		this.add(boutConn, contraintes);
	}
}
