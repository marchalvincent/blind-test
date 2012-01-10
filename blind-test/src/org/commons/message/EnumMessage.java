package org.commons.message;

import org.commons.util.IWithId;

public enum EnumMessage implements IWithId {
	
	INSCRIPTION (1) {
		@Override
		public IMessage createMessage() {
			return new InscriptionDefaultMessage();
		}
	},
	CONNEXION (2) {
		@Override
		public IMessage createMessage() {
			return new ConnexionMessage();
		}
	},
	ANSWER(3) {
		@Override
		public IMessage createMessage() {
			return new AnswerMessage();
		}
	},
	DOWNLOAD(4) {
		@Override
		public IMessage createMessage() {
			return new DownloadDefaultMessage();
		}
	},
	DISPLAY(5) {
		@Override
		public IMessage createMessage() {
			return new DisplayDefaultMessage();
		}
	},
	INFO(6) {
		@Override
		public IMessage createMessage() {
			return new InfoDefaultMessage();
		}
	},
	ERROR(7) {
		@Override
		public IMessage createMessage() {
			return new ErrorDefaultMessage();
		}
	};
	
	private final int id;
	
	EnumMessage (int id) {
		this.id = id;
	}

	@Override
	public final Integer getId() {
		return Integer.valueOf(id);
	}

	static public final boolean isError(final EnumMessage parMessage) {
		return EnumMessage.ERROR.equals(parMessage);
	}
	
	static public final boolean isInfo(final EnumMessage parMessage) {
		return EnumMessage.INFO.equals(parMessage);
	}
	
	public abstract IMessage createMessage();
}
