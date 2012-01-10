package org.commons.entity;

import java.io.Serializable;

import org.commons.util.IWithId;
import org.commons.util.IWithName;
import org.commons.util.StringUtil;

public class User implements Comparable<User>, IWithName, IWithId, Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer _id;
	private String _name;
	private String _login;
	private String _password;
	private Integer _victoire;	
	private Integer _defaite;
	private Integer _score;
	
	//Constructeur initialisé
	public User() {
		this(Integer.valueOf(-1),"", "", "", Integer.valueOf(0), Integer.valueOf(0));
	}
	
	public User (final Integer parId, final String parName, final String parLogin, final String parPassword, final Integer parVictoire, final Integer parDefaite) {
		_id = parId;
		_name = parName;
		_login = parLogin;
		_password = parPassword;
		_victoire = parVictoire;
		_defaite = parDefaite;
	}
	
	public Integer getVictoire() {
		return _victoire;
	}

	public void setVictoire(Integer _victoire) {
		this._victoire = _victoire;
	}

	public Integer getDefaite() {
		return _defaite;
	}

	public void setDefaite(Integer _defaite) {
		this._defaite = _defaite;
	}

	@Override
	public final Integer getId() {
		return _id;
	}
	
	public final void setId(final Integer parId) {
		_id = parId;
	}
	
	public final int getScore() {
		if (_victoire >= _defaite){
			return _victoire.intValue()-_defaite.intValue();
		} else {
			return 0;
		}
	}
	
	public final void setScore(final Integer parScore) {
		_score = parScore;
	}
	
	@Override
	public final String getConstName() {
		return _login;
	}
	
	public final void setLogin(final String parLogin) {
		_login = parLogin;
	}
	
	
	public final String getName() {
		return _name;
	}
	
	public final void setName(final String parName) {
		_name = parName;
	}
	
	public final String getPassword() {
		return _password;
	}
	
	public final void setPassword(final String parPassword) {
		_password = parPassword;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_login == null) ? 0 : _login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (_login == null) {
			if (other._login != null)
				return false;
		} else if (!_login.equals(other._login))
			return false;
		return true;
	}

	@Override
	public final int compareTo(final User parUser) {
		if(parUser == null) return 1;
		final int locCompareTo = StringUtil.compareTo(_login, parUser._login);
		return locCompareTo;
	}
	
	@Override
	public final String toString() {
		return _login;
	}
	
}
