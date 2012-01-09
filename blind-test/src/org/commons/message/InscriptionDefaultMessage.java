package org.commons.message;


public final class InscriptionDefaultMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String login = null;
	private String password = null;
	private String nom = null;

	protected InscriptionDefaultMessage() {
		this ("", "", "");
	}
	
	protected InscriptionDefaultMessage(String login, String password, String nom) {
		super(EnumMessage.INSCRIPTION);
		this.login = login;
		this.password = password;
		this.nom = nom;
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

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
