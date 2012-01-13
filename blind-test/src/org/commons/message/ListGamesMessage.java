package org.commons.message;

import java.util.ArrayList;
import java.util.List;

public class ListGamesMessage extends AbstractMessage {

	private static final long serialVersionUID = 1L;
	private List<String> listGames;
	
	protected ListGamesMessage() {
		this(new ArrayList<String>());
	}
	
	protected ListGamesMessage(List<String> list) {
		super(EnumMessage.LIST_GAMES);
		this.listGames = list;
	}
	
	public final List<String> getListGames() {
		return listGames;
	}

	public final void addAll(List<String> listGames) {
		this.listGames.addAll(listGames);
	}
	
	public final void addGame(String game) {
		this.listGames.add(game);
	}

}
