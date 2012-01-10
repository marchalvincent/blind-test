package org.commons.message;

public class PlayMessage extends AbstractMessage {
	
	private static final long serialVersionUID = 1L;
	private String login = null;

	protected PlayMessage() {
		this(null);
	}
	
	protected PlayMessage(String login) {
		super(EnumMessage.PLAY);
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
}
