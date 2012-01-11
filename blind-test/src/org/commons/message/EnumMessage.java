package org.commons.message;

import org.commons.util.IWithId;

public enum EnumMessage implements IWithId {
	
	INSCRIPTION (1) {
		@Override
		public IMessage createMessage() {
			return new InscriptionMessage();
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
			return new DownloadMessage();
		}
	},
	DISPLAY(5) {
		@Override
		public IMessage createMessage() {
			return new DisplayMessage();
		}
	},
	INFO(6) {
		@Override
		public IMessage createMessage() {
			return new InfoMessage();
		}
	},
	ERROR(7) {
		@Override
		public IMessage createMessage() {
			return new ErrorMessage();
		}
	},
	
	PLAY(8) {
		@Override
		public IMessage createMessage() {
			return new PlayMessage();
		}
	}, 
	
	WINNER(9) {
		@Override
		public IMessage createMessage() {
			return new WinnerMessage();
		}
	},
	
	STAT(10){
		@Override
		public IMessage createMessage() {
			return new StatMessage();
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
	
	public static boolean isDisplay(final EnumMessage parMessage) {
		return EnumMessage.DISPLAY.equals(parMessage);
	}
	
	public static boolean isWinner(final EnumMessage parMessage) {
		return EnumMessage.WINNER.equals(parMessage);
	}
	
	public abstract IMessage createMessage();


}
