package org.commons.message;

public class PlayMessage extends AbstractMessage {
	
	private static final long serialVersionUID = 1L;
	private String login = null;
	private String nomPartie = null;

	protected PlayMessage() {
		this("", "");
	}
	
	protected PlayMessage(String login, String nomPartie) {
		super(EnumMessage.PLAY);
		this.login = login;
		this.nomPartie = nomPartie;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getNomPartie() {
		return nomPartie;
	}

	public void setNomPartie(String nomPartie) {
		this.nomPartie = nomPartie;
	}
	
}
