package org.commons.message;

public abstract class AbstractMessage implements IMessage {

	private static final long serialVersionUID = 1L;
	private final Integer id;
	protected AbstractMessage (EnumMessage parMessage) {
		id = parMessage.getId ();
	}
	
	@Override
	public Integer getId () {
		return id;
	}
	
	@Override
	public String toString() {
		return id.toString();
	}

}
