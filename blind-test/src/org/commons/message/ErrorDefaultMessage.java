package org.commons.message;


public final class ErrorDefaultMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private String message = null;

	protected ErrorDefaultMessage() {
		this("");
	}
	
	protected ErrorDefaultMessage(String message) {
		super(EnumMessage.ERROR);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
