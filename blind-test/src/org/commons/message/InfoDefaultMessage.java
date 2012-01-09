package org.commons.message;

import org.commons.util.IWithSupport;


public final class InfoDefaultMessage extends AbstractMessage implements IWithSupport {

	private static final long serialVersionUID = 1L;
	private String message;
	
	protected InfoDefaultMessage() {
		this("");
	}
	
	protected InfoDefaultMessage(String message) {
		super(EnumMessage.INFO);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String getSupport() {
		return getMessage();
	}
}
