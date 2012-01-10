package org.commons.message;


public final class ConnexionMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String login = null;
	private String password = null;

	protected ConnexionMessage() {
		this("", "");
	}
	
	protected ConnexionMessage(String login, String password) {
		super(EnumMessage.CONNEXION);
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
