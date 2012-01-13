package org.server.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

import org.commons.entity.Banque;

public final class BanqueManager extends AbstractManager<Banque> {

	static private final String ADD = "INSERT INTO banque VALUES (NULL,?,?,?,?)";
	static private final String MERGE = "UPDATE banque SET answer = ?, name = ?, directory = ?, version = ? WHERE id = ?";
	static private final String REMOVE_NAME = "DELETE FROM banque WHERE name = ?";
	static private final String REMOVE_ID = "DELETE FROM banque WHERE id = ?";
	static private final String FINDALL = "SELECT * FROM banque";
	static private final String FIND_ID = "SELECT * from banque WHERE id = ?";
	static private final String FIND_NAME = "SELECT * FROM banque WHERE name = ?";
	
	protected BanqueManager() {
		super();
	}

	@Override
	public final Banque add(final Banque parBanque) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		try {
			locStatement = getConnection().prepareStatement(ADD, PreparedStatement.RETURN_GENERATED_KEYS);
			locStatement.setString(1, parBanque.getAnswer());
			locStatement.setString(2, parBanque.getConstName());
			locStatement.setString(3, parBanque.getDirectory());
			// la version est forcément de 1 lors de l'insertion
			locStatement.setInt(4, 1);
			locStatement.executeUpdate();
			_connection.commit();
			locResultSet = locStatement.getGeneratedKeys();
			locResultSet.next();
			final int locId = locResultSet.getInt(1);
			parBanque.setId(Integer.valueOf(locId));
			parBanque.setVersion(new AtomicInteger(1));
			return parBanque;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible d'insérer %s en base", parBanque.toString()), locException);
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public final boolean remove(final Integer parId) {
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
	public final boolean remove(final String parName) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		try {
			locStatement = _connection.prepareStatement(REMOVE_NAME);
			locStatement.setString(1, parName);
			locStatement.executeUpdate();
			_connection.commit();
			return true;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de supprimer le nom %s", parName), locException);
		} finally {
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}		
		return false;
	}

	@Override
	public final Banque merge(final Banque parBanque) {
		_lock.writeLock().lock();
		PreparedStatement locStatement = null;
		try {
			locStatement = _connection.prepareStatement(MERGE,PreparedStatement.RETURN_GENERATED_KEYS);
			locStatement.setString(1, parBanque.getAnswer());
			locStatement.setString(2, parBanque.getConstName());
			locStatement.setString(3, parBanque.getDirectory());
			locStatement.setInt(4, parBanque.incrementAndGetVersion());
			locStatement.setInt(5, parBanque.getId());
			locStatement.executeUpdate();
			_connection.commit();
			return parBanque;
		} catch (SQLException locException) {
			getInfoProvider().appendMessage(Level.SEVERE, String.format("Impossible de mettre à jour %s.", parBanque.toString()), locException);
		} finally {
			closeStatement(locStatement);
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public final Banque find(final int parId) {
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
			return createBanque(locResultSet);
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
	public final Banque find(final String parName) {
		_lock.readLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		try {
			locStatement = _connection.prepareStatement(FIND_NAME);
			locStatement.setString(1, parName);
			locResultSet = locStatement.executeQuery();
			if(locResultSet.next() == false) {
				return null;
			}
			return createBanque(locResultSet);
		} catch (SQLException locException) {
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.readLock().unlock();
		}
		return null;
	}

	@Override
	public final List<Banque> findAll() {
		_lock.readLock().lock();
		PreparedStatement locStatement = null;
		ResultSet locResultSet = null;
		final List<Banque> locBanqueList = new ArrayList<Banque>();
		try {
			locStatement = _connection.prepareStatement(FINDALL);
			locResultSet = locStatement.executeQuery();
			while (locResultSet.next()) {
				final Banque locBanque = createBanque(locResultSet);
				locBanqueList.add(locBanque);
			}
			return locBanqueList;
		} catch (SQLException locException) {
		} finally {
			closeResultSet(locResultSet);
			closeStatement(locStatement);
			_lock.readLock().unlock();
		}
		return locBanqueList;
	}
	
	private final Banque createBanque (final ResultSet parResultSet) throws SQLException {
		final Banque locBanque = new Banque();
		final int locId = parResultSet.getInt(1);
		final String locAnswer = parResultSet.getString(2);
		final String locName = parResultSet.getString(3);
		final String locDirectory = parResultSet.getString(4);
		final int locVersion = parResultSet.getInt(5);
		locBanque.setId(Integer.valueOf(locId));
		locBanque.setAnswer(locAnswer);
		locBanque.setName(locName);
		locBanque.setDirectory(locDirectory);
		locBanque.setVersion(new AtomicInteger(locVersion));
		return locBanque;
	}
}
