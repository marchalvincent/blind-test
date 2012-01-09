package org.commons.message;


public final class InfoMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String message = null;
	
	protected InfoMessage() {
		this("");
	}
	
	protected InfoMessage(String message) {
		super(EnumMessage.INFO);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
