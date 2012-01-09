package org.commons.message;

public class ConnexionMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String login, password;
	protected ConnexionMessage() {
		super(EnumMessage.CONNEXION);
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
