package org.commons.message;

import org.commons.util.IWithId;

public enum EnumMessage implements IWithId {
	
	INSCRIPTION (1),
	CONNEXION (2);
	
	private final int id;
	
	EnumMessage (int id) {
		this.id = id;
		
	}

	@Override
	public final Integer getId() {
		return Integer.valueOf(id);
	}

}
