package org.commons.cache;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractCache<K, V> {

	private final Map<K, V> _cache;
	
	protected AbstractCache() {
		_cache = new ConcurrentHashMap<K, V>();
	}
	
	public final void remove(final K parKey) {
		_cache.remove(parKey);
	}
	
	public final V put(final K parKey, final V parValue) {
		return _cache.put(parKey, parValue);
	}
	
	public final V get(final K parKey) {
		return _cache.get(parKey);
	}
	
	public final int size() {
		return _cache.size();
	}
	
	public final boolean containsKey(final K parKey) {
		return _cache.containsKey(parKey);
	}
	
	public final boolean containsValue(final V parValue) {
		return _cache.containsValue(parValue);
	}
	
	public final List<K> keys() {
		return new ArrayList<K>(_cache.keySet());
	}
	
	public final List<V> values() {
		return new ArrayList<V>(_cache.values());
	}
	
	public final Set<Map.Entry<K,V>> entrySet() {
		return new HashSet<Map.Entry<K,V>>(_cache.entrySet());
	}
	
}
