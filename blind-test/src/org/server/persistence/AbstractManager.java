package org.server.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.commons.logger.InfoProvider;


public abstract class AbstractManager<T> implements Manager<T> {

	protected final Connection _connection;
	protected final InfoProvider _infoProvider;
	protected final ReentrantReadWriteLock _lock;
	
	protected AbstractManager() {
		final MySQLConnexion locConnexion = MySQLConnexion.instance();
		_connection = locConnexion.getConnection();
		_infoProvider = locConnexion.getInfoProvider();
		_lock = new ReentrantReadWriteLock(true);
	}
	
	protected final InfoProvider getInfoProvider() {
		return _infoProvider;
	}
	
	protected final Connection getConnection() {
		return _connection;
	}
	
	protected final Exception closeStatement (final Statement parStatement) {
		if(parStatement == null) return null;
		
		try {
			if(parStatement.isClosed() == false) {
				parStatement.close();
			}
		} catch (SQLException locException) {
			return locException;
		}
		return null;
	}
	
	protected final Exception closeResultSet(final ResultSet parResultSet) {
		if(parResultSet == null) return null;
		
		try {
			if(parResultSet.isClosed() == false) {
				parResultSet.close();
			}
		} catch (SQLException locException) {
			return locException;
		}
		return null;
	}
}
