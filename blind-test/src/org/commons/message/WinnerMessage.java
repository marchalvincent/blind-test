package org.commons.message;

import org.commons.util.IWithSupport;

public class WinnerMessage extends AbstractMessage implements IWithSupport {

	private static final long serialVersionUID = 1L;
	private String login = null;
	private String message = null;
	
	protected WinnerMessage() {
		super(EnumMessage.WINNER);
	}

	@Override
	public String getSupport() {
		return message;
	}

	public final String getLogin() {
		return login;
	}

	public final void setLogin(String login) {
		this.login = login;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

}
