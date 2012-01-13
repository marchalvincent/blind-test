package org.commons.message;

public class DisconnectMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String partie;
	private String login;

	protected DisconnectMessage() {
		this("", "");
	}
	
	protected DisconnectMessage(String partie, String login) {
		super(EnumMessage.DISCONNECT);
		this.partie = partie;
		this.login = login;
	}

	public final String getPartie() {
		return partie;
	}

	public final void setPartie(String partie) {
		this.partie = partie;
	}

	public final void setMessage(String message) {
		this.partie = message;
	}

	public final String getLogin() {
		return login;
	}

	public final void setLogin(String login) {
		this.login = login;
	}

}
