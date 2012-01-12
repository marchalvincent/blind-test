package org.commons.message;

public class PlayMessage extends AbstractMessage {
	
	private static final long serialVersionUID = 1L;
	private String login;
	private String nomPartie;
	private int _size;	
	
	protected PlayMessage() {
		this("", "", 10);
	}
	
	protected PlayMessage(String login, String nomPartie, final int parSize) {
		super(EnumMessage.PLAY);
		this.login = login;
		this.nomPartie = nomPartie;
	}

	public int getSize() {
		return _size;
	}
	
	public final void setSize(final int parSize) {
		_size = parSize;
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
