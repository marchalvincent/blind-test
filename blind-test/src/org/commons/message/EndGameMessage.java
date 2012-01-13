package org.commons.message;

import java.util.HashMap;
import java.util.Map;

import org.commons.util.IWithSupport;

public class EndGameMessage extends AbstractMessage implements IWithSupport {

	private static final long serialVersionUID = 1L;
	private String message;
	private Map<String, Integer> _scores;

	protected EndGameMessage() {
		this("");
	}
	
	protected EndGameMessage(String message) {
		super(EnumMessage.END_GAME);
		this.message = message;
		_scores = new HashMap<String, Integer>();
	}

	@Override
	public String getSupport() {
		return getMessage();
	}

	public final String getMessage() {
		return message;
	}
	
	public final Map<String, Integer> toMap() {
		return new HashMap<String, Integer>(_scores);
	}

	public final void setMessage(String message) {
		this.message = message;
	}
	
	public final void putAll(final Map<String, Integer> parMap) {
		_scores.putAll(parMap);
	}

}
