package org.commons.message;

import org.commons.util.IWithId;

public enum EnumMessage implements IWithId {
	
	INSCRIPTION (1) {
		@Override
		public IMessage creatMessage() {
			return new InfoMessage();
		}
	},
	CONNEXION (2) {
		@Override
		public IMessage creatMessage() {
			return new ConnexionMessage();
		}
	},
	ANSWER(3) {
		@Override
		public IMessage creatMessage() {
			return new AnswerMessage();
		}
	},
	DOWNLOAD(4) {
		@Override
		public IMessage creatMessage() {
			return new DownloadMessage();
		}
	},
	DISPLAY(5) {
		@Override
		public IMessage creatMessage() {
			return new DisplayMessage();
		}
	},
	INFO(6) {
		@Override
		public IMessage creatMessage() {
			return new InfoMessage();
		}
	},
	ERROR(7) {
		@Override
		public IMessage creatMessage() {
			return new ErrorMessage();
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

	public abstract IMessage creatMessage();
}
