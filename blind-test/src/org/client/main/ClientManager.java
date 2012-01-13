package org.client.main;

public final class ClientManager {

	private static String login = "";
	
	public static String getLogin() {
		return login;
	}
	
	public static void setLogin(String login) {
		ClientManager.login = login;
	}
}
