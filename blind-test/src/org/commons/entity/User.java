package org.commons.entity;

import java.io.Serializable;

import org.commons.util.IWithId;
import org.commons.util.IWithName;
import org.commons.util.StringUtil;

public class User implements Comparable<User>, IWithName, IWithId, Serializable {

	
	private static final long serialVersionUID = 1L;
	private Integer _id;
	private Stat _stat;
	private String _name;
	private String _login;
	private String _password;
	
	
	public User() {
		this(Integer.valueOf(-1),new Stat(), "", "", "");
	}
	
	public User (final Integer parId, final Stat parStat, final String parName, final String parLogin, final String parPassword) {
		
		_id = parId;
		_stat = parStat;
		_name = parName;
		_login = parLogin;
		_password = parPassword;
	}
	
	@Override
	public final Integer getId() {
		return _id;
	}
	
	public final void setId(final Integer parId) {
		_id = parId;
	}
	
	public final Stat getStat() {
		return _stat;
	}
	
	public final void setStat(final Stat parStat) {
		_stat = parStat;
	}
	
	
	@Override
	public final String getConstName() {
		return _name;
	}
	
	public final void setName(final String parName) {
		_name = parName;
	}
	
	
	public final String getLogin() {
		return _login;
	}
	
	public final void setLogin(final String parLogin) {
		_login = parLogin;
	}
	
	public final String getPassword() {
		return _password;
	}
	
	public final void setPassword(final String parPassword) {
		_login = parPassword;
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
	

}
