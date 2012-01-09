package org.commons.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.commons.util.IWithId;
import org.server.persistence.Manager;

public abstract class AbstractCache<T extends IWithId> implements Manager<T> {

	private final Map<Integer, T> _idMap;
	private final Map<String, T> _nameMap;
	private final ReentrantReadWriteLock _lock;
	
	protected AbstractCache() {
		_idMap = new ConcurrentHashMap<Integer, T>();
		_nameMap = new ConcurrentHashMap<String, T>();
		_lock = new ReentrantReadWriteLock();
	}
	
	@Override
	public final T add(final T parObject) {
		if(parObject == null) return null;
		
		_lock.writeLock().lock();
		try {
			final String locPersistentName = getPersistentName(parObject);
			_idMap.put(parObject.getId(), parObject);
			_nameMap.put(locPersistentName, parObject);
			return parObject;
		} finally {
			_lock.writeLock().unlock();
		}
	}

	@Override
	public final boolean remove(String parName) {
		_lock.writeLock().lock();
		try {
			
		} finally {
			_lock.writeLock().unlock();
		}
		return false;
	}

	@Override
	public boolean remove(Integer parId) {
		_lock.writeLock().lock();
		try {
			
		} finally {
			_lock.writeLock().unlock();
		}
		return false;
	}

	@Override
	public T merge(T parObject) {
		_lock.writeLock().lock();
		try {
			
		} finally {
			_lock.writeLock().unlock();
		}
		return null;
	}

	@Override
	public T find(int parId) {
		_lock.readLock().lock();
		try {
			
		} finally {
			_lock.readLock().unlock();
		}
		return null;
	}

	@Override
	public T find(String parName) {
		//
		_lock.readLock().lock();
		try {
			
		} finally {
			_lock.readLock().unlock();
		}
		return null;
	}

	@Override
	public List<T> findAll() {

		_lock.readLock().lock();
		try {
			
		} finally {
			_lock.readLock().unlock();
		}
		return null;
	}
	
	abstract protected String getPersistentName(final T parObject);
	

}
