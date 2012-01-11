package org.commons.message;

import org.commons.util.IWithSupport;

public class EndGameMessage extends AbstractMessage implements IWithSupport {

	private static final long serialVersionUID = 1L;
	private String message;

	protected EndGameMessage() {
		this("");
	}
	
	protected EndGameMessage(String message) {
		super(EnumMessage.FINISH_GAME);
		this.message = message;
	}

	@Override
	public String getSupport() {
		return getMessage();
	}

	public final String getMessage() {
		return message;
	}

	public final void setMessage(String message) {
		this.message = message;
	}

}
