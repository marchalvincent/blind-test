package org.server.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.commons.entity.Stat;
import org.commons.entity.User;

public class UserManager extends AbstractManager<User> {
	
	static private final String ADD = "INSERT INTO user VALUES (NULL,?,?,?,?)";
	static private final String MERGE = "UPDATE user SET idStat= ?, name = ?, login = ?, password = ? WHERE idUser = ?";
	static private final String REMOVE_LOGIN = "DELETE FROM user WHERE login = ?";
	static private final String REMOVE_ID = "DELETE FROM user WHERE idUser = ?";
	static private final String FINDALL = "SELECT * FROM user";
	static private final String FIND_ID = "SELECT * from user WHERE idUser = ?";
	static private final String FIND_LOGIN = "SELECT * FROM user WHERE login = ?";
	
	static private  Manager<Stat> stat = Managers.createStatManager();
	final Stat locStat = new Stat(-1,0,0);
	
	@Override
	public User add(User parUser) {
		// TODO Auto-generated method stub
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		stat.add(locStat);
		parUser.setStat(locStat);
		
		try{
			locStatement = getConnection().prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			locStatement.setInt(1, parUser.getStat().getId());
			locStatement.setString(2, parUser.getConstName());
			locStatement.setString(3, parUser.getLogin());
			locStatement.setString(4, parUser.getPassword());
			locStatement.executeUpdate();
			_connection.commit();
			locResultSet = locStatement.getGeneratedKeys();
			locResultSet.next();
			final int locId = locResultSet.getInt(1);
			parUser.setId(Integer.valueOf(locId));
			return parUser;
		}
		catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible d'insérer %s en base", parUser.toString()), locException);
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public boolean remove(String parLogin) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		try {
			locStatement = _connection.prepareStatement(REMOVE_LOGIN);
			locStatement.setString(1, parLogin);
			locStatement.executeUpdate();
			_connection.commit();
			return true;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de supprimer le nom %s", parLogin), locException);
		} finally {
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}		
		return false;
	}

	@Override
	public boolean remove(Integer parId) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		try {
			locStatement = _connection.prepareStatement(REMOVE_ID);
			locStatement.setInt(1, parId);
			locStatement.executeUpdate();
			_connection.commit();
			return true;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de supprimer l'identifiant est %d", parId), locException);
		} finally {
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}		
		return false;
	}

	@Override
	public User merge(final User parUser) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		stat.add(locStat);
		parUser.setStat(locStat);
		try {
			locStatement = _connection.prepareStatement(MERGE,PreparedStatement.RETURN_GENERATED_KEYS);
			locStatement.setInt(1, parUser.getStat().getId());
			locStatement.setString(2, parUser.getConstName());
			locStatement.setString(3, parUser.getLogin());
			locStatement.setString(4, parUser.getPassword());
			locStatement.executeUpdate();
			_connection.commit();
			return parUser;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de mettre à jour %s.", parUser.toString()), locException);
		} finally {
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public User find(int parId) {
		_lock.readLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		try {
			locStatement = _connection.prepareStatement(FIND_ID);
			locStatement.setInt(1, parId);
			locResultSet = locStatement.executeQuery();
			if(false == locResultSet.next()) {
				return null;
			}
			return createUser(locResultSet);
		} catch (SQLException locException) {	
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de trouver l'idenfitiant %d", parId), locException);
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.readLock().unlock();
		}
		return null;
	}

	@Override
	public User find(String parLogin) {
		_lock.readLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		try {
			locStatement = _connection.prepareStatement(FIND_LOGIN);
			locStatement.setString(1, parLogin);
			locResultSet = locStatement.executeQuery();
			if(locResultSet.next() == false) {
				return null;
			}
			return createUser(locResultSet);
		} catch (SQLException locException) {
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.readLock().unlock();
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		_lock.readLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		final List<User> locUserList = new ArrayList<User>();
		try {
			locStatement = _connection.prepareStatement(FINDALL);
			locResultSet = locStatement.executeQuery();
			while (locResultSet.next()) {
				final User locUser= createUser(locResultSet);
				locUserList.add(locUser);
			}
			return locUserList;
		} catch (SQLException locException) {
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.readLock().unlock();
		}
		return locUserList;
	}
	
	private final User createUser (final ResultSet parResultSet) throws SQLException {
		stat.add(locStat);
		final User locUser = new User();

		final int locId = parResultSet.getInt(1);
		//final int locStatId = parResultSet.getInt(2);
		final String locName = parResultSet.getString(3);
		final String locLogin = parResultSet.getString(4);
		final String locPassword = parResultSet.getString(5);
		
		locUser.setId(Integer.valueOf(locId));
		locUser.setStat(locStat);
		locUser.setName(locName);
		locUser.setLogin(locLogin);
		locUser.setPassword(locPassword);
		return locUser;
	}

}
