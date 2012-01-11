package org.commons.message;

public class StatMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String login = null;
	private Integer victoire = null;
	private Integer defaite = null;

	protected StatMessage() {
		this ("", 0, 0);
	}
	
	protected StatMessage(String login, Integer victoire, Integer defaite) {
		super(EnumMessage.INSCRIPTION);
		this.login = login;
		this.victoire = victoire;
		this.defaite = defaite;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Integer getVictoire() {
		return victoire;
	}

	public void setVictoire(Integer victoire) {
		this.victoire = victoire;
	}

	public Integer getDefaite() {
		return defaite;
	}

	public void setDefaite(Integer defaite) {
		this.defaite = defaite;
	}
	
	public int getScore(){
		return victoire.intValue() - defaite.intValue();
	}

}
