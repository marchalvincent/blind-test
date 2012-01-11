package org.server.action;

import java.net.Socket;

import org.commons.message.EnumMessage;
import org.commons.message.IMessage;
import org.commons.util.IWithId;

public enum EnumAction implements IWithId {

	INSCRIPTION(EnumMessage.INSCRIPTION.getId()) {
		
		@Override
		final public AbstractAction createAction (final Socket parSocket, final IMessage parMessage) {
			return new InscriptionAction(parSocket, parMessage);
		}
		
	},
	CONNEXION(EnumMessage.CONNEXION.getId()) {
		
		@Override
		final public AbstractAction createAction (final Socket parSocket, final IMessage parMessage) {
			return new ConnexionAction(parSocket, parMessage);
		}
		
	},
	PLAY(EnumMessage.PLAY.getId()) {
		
		@Override
		final public AbstractAction createAction (final Socket parSocket, final IMessage parMessage) {
			return new PlayAction(parSocket, parMessage);
		}
		
	},
	STAT(EnumMessage.STAT.getId()){
		
		@Override
		final public AbstractAction createAction (final Socket parSocket, final IMessage parMessage) {
			return new StatAction(parSocket, parMessage);
		}
	};
	
	final private Integer _id;
	
	private EnumAction(final Integer parId) {
		_id = parId;
	}
	
	@Override
	final public Integer getId() {
		return _id;
	}
	
	@Override
	final public String toString() {
		return name();
	}
	
	abstract public AbstractAction createAction(final Socket parSocket, final IMessage parMessage);
	
}
