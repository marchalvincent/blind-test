package org.commons.message;

public class ConnexionMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String login = null;
	private String mdp = null;

	protected ConnexionMessage(String login, String mdp) {
		super(EnumMessage.CONNEXION);
		this.login = login;
		this.mdp = mdp;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
}
