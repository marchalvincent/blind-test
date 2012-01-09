package org.server.concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Un pool de threads.
 * @author pitton
 *
 */
public final class BlindTestExecutor {

	final private ExecutorService _pool;
	
	public BlindTestExecutor(final int parSize) {
		this(parSize, 600, TimeUnit.SECONDS);
	}
	
	public BlindTestExecutor(final int parSize, final long parKeep, final TimeUnit parUnit) {
		_pool = new ThreadPoolExecutor(parSize / 2, parSize, parKeep, parUnit, new ArrayBlockingQueue<Runnable>(10));
	}
	
	/**
	 * Soumet un {@link Callable} au pool et renvoie le {@link Future} associé.
	 * @param parCallable {@link Callable} une tache
	 * @return {@link Future} le résultat de la tache.
	 */
	public final <T> Future<T> submit (final Callable<T> parCallable) {
		if(parCallable == null) return null;
		
		return _pool.submit(parCallable);
	}
	
	/**
	 * Soumet un {@link Callable} au pool et renvoie directement le résultat. 
	 * @param parCallable {@link Callable} une tache.
	 * @return {@code T} le résultat de la tache.
	 * @throws InterruptedException si la tache est interrompue.
	 * @throws ExecutionException si l'exécution de la tache a échoué.
	 */
	public final <T> T submitAndGet(final Callable<T> parCallable) throws InterruptedException, ExecutionException {
		if(parCallable == null) return null;
		
		final Future<T> locFuture = submit(parCallable);
		return (locFuture == null) ? null : locFuture.get();
	}
	
	/**
	 * Soumet un {@link Runnable} au pool et renvoie un {@link Future} associé au résultat.
	 * @param parRunnable {@link Runnable} une tache.
	 * @return {@link Future} le résultat associé au {@link Runnable}
	 */
	public final Future<?> submit(final Runnable parRunnable) {
		if(parRunnable == null) return null;
		
		return _pool.submit(parRunnable);
	}
	
	/**
	 * Coupe le pool de threads
	 */
	public final void shutdown() {
		_pool.shutdown();
	}
	
	/**
	 * Renvoie vrai si le pool est eteins.
	 * @return {@code boolean} vrai si le pool est eteins.
	 */
	public final boolean isShutdown() {
		return _pool.isShutdown();
	}
}
