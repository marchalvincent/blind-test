package org.server.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.commons.entity.Stat;


public class StatManager extends AbstractManager<Stat> {

	static private final String ADD = "INSERT INTO stat VALUES (NULL,?,?)";
	static private final String MERGE = "UPDATE stat SET defaite= ?, victoire = ? WHERE idStat = ?";
	static private final String REMOVE_ID = "DELETE FROM stat WHERE idStat = ?";
	static private final String FINDALL = "SELECT * FROM stat";
	static private final String FIND_ID = "SELECT * FROM stat WHERE idStat = ?";
	
	@Override
	public Stat add(Stat parStat) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		
		try{
			locStatement = getConnection().prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			locStatement.setInt(1, parStat.getId());
			locStatement.setInt(2, parStat.getDefaite());
			locStatement.setInt(3, parStat.getVictoire());
			locStatement.executeUpdate();
			_connection.commit();
			locResultSet = locStatement.getGeneratedKeys();
			locResultSet.next();
			final int locId = locResultSet.getInt(1);
			parStat.setId(Integer.valueOf(locId));
			return parStat;
		}
		catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible d'insérer %s en base", parStat.toString()), locException);
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public boolean remove(String parName) {
		// TODO Auto-generated method stub
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
	public Stat merge(Stat parStat) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;

		try {
			locStatement = _connection.prepareStatement(MERGE,PreparedStatement.RETURN_GENERATED_KEYS);
			locStatement.setInt(1, parStat.getDefaite());
			locStatement.setInt(2, parStat.getVictoire());
			locStatement.executeUpdate();
			_connection.commit();
			return parStat;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de mettre à jour %s.", parStat.toString()), locException);
		} finally {
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public Stat find(int parId) {
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
			return createStat(locResultSet);
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
	public Stat find(String parName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stat> findAll() {
		_lock.readLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		final List<Stat> locStatList = new ArrayList<Stat>();
		try {
			locStatement = _connection.prepareStatement(FINDALL);
			locResultSet = locStatement.executeQuery();
			while (locResultSet.next()) {
				final Stat locStat= createStat(locResultSet);
				locStatList.add(locStat);
			}
			return locStatList;
		} catch (SQLException locException) {
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.readLock().unlock();
		}
		return locStatList;
	}
	
	private final Stat createStat (final ResultSet parResultSet) throws SQLException {
		final Stat locStat = new Stat();
		final int locId = parResultSet.getInt(1);
		final int locDefaite = parResultSet.getInt(2);
		final int locVictoire = parResultSet.getInt(3);
		locStat.setId(Integer.valueOf(locId));
		locStat.setDefaite(locDefaite);
		locStat.setVictoire(locVictoire);
		return locStat;
	}

}
