package org.client.main;


public final class Connexion implements Runnable {

	private final String _login;
	private final String _password;
	
	public Connexion(final String parLogin, final String parPassword) {
		_login = parLogin;
		_password = parPassword;
	}

	@Override
	public final void run() {
	}
}
