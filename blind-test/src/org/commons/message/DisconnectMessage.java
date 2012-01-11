package org.commons.message;

import org.commons.util.IWithSupport;

public class DisconnectMessage extends AbstractMessage implements IWithSupport {

	private static final long serialVersionUID = 1L;
	private String message;

	protected DisconnectMessage() {
		this("");
	}
	
	protected DisconnectMessage(String message) {
		super(EnumMessage.DISCONNECT);
		this.message = message;
	}

	@Override
	public String getSupport() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

}
