package org.server.partie;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;

import org.commons.cache.AbstractCache;
import org.commons.cache.Caches;
import org.commons.entity.Banque;
import org.commons.entity.User;
import org.commons.logger.InfoProvider;
import org.commons.message.EnumMessage;
import org.commons.message.WinnerMessage;
import org.commons.util.IWithName;
import org.commons.util.StringUtil;
import org.commons.util.SystemUtil;
import org.server.concurrent.ReadWriterUtil;
import org.server.persistence.Manager;
import org.server.persistence.Managers;

public class Partie implements IWithName, Closeable {

	private List<User> _userList;
	private List<Banque> _banqueList;
	private String _name;
	private AbstractCache<User, Socket> _sockets;
	private Banque _banque;
	private AtomicInteger _currentAck;
	private AtomicBoolean _hasChangedImage;
	private AtomicBoolean _hasWinner;
	private int _size;
	private TreeMap<String, Integer> _currentStat;
	private ReentrantReadWriteLock _lock;

	public Partie(final String name, final int parSize){
		_size = parSize;
		_banqueList = new ArrayList<Banque>();
		_userList = new ArrayList<User>();
		_name = name;
		_sockets = Caches.createSocketCache();
		_currentAck = new AtomicInteger(0);
		_hasChangedImage = new AtomicBoolean(false);
		_hasWinner = new AtomicBoolean(false);
		_currentStat = new TreeMap<String, Integer>();
		_lock = new ReentrantReadWriteLock();
	}
	
	protected synchronized final ReentrantReadWriteLock lock() {
		return _lock;
	}

	public List<User> getUsers(){
		return new ArrayList<User>(_userList);//Recopie défensive + Renvoi une sous liste non modifiable
	}

	public final boolean hasUser (final User parUser) {
		return _userList.contains(parUser);
	}

	public final boolean isEmpty() {
		return _userList.isEmpty();
	}

	public final void updateImage(){	
		final Manager<Banque> locBanqueManager = Managers.createBanqueManager();	
		final List<Banque> listImage = locBanqueManager.findAll();
		Collections.shuffle(listImage, new Random(System.nanoTime()));
		final int locSize = listImage.size();
		if(_size <= 0 || _size > locSize)  {
			_size = locSize;
			_banqueList.addAll(listImage);
			return;
		}
		final List<Banque> locRealList = new ArrayList<Banque>(_size);
		for(int i = 0 ; i < _size ; ++i) {
			locRealList.add(listImage.get(i));
		}
		_banqueList.addAll(locRealList);
	}

	public final boolean hasChangedImage() {
		return _hasChangedImage.get();
	}

	public final void setChangedImage(final boolean parNewValue) {
		_hasChangedImage.set(parNewValue);
	}

	public void addUser(final User user, final Socket parSocket){
		_userList.add(user);
		_sockets.put(user, parSocket);
		_currentStat.put(user.getConstName(), 0);
	}

	public void removeUser(final User user){
		_userList.remove(user);
		_sockets.remove(user);
		_currentStat.remove(user.getConstName());
	}

	@Override
	public String getConstName() {
		return _name;
	}

	public boolean isFinished(){
		return _banqueList.isEmpty();
	}

	//Renvoi le prochain élement qu'on doit afficher
	public final Banque next(){
		this.setChangedImage(true);
		_banque = _banqueList.remove(_banqueList.size()-1);
		return _banque;
	}	

	public final Banque getCurrentAnswer() {
		return _banque;
	}

	public final boolean isValidAnswer(final String parAnswer) {
		final boolean locAnswer = StringUtil.equals(parAnswer, _banque.getAnswer());
		if(true == locAnswer) {
			this._hasWinner.set(true);
		}
		return locAnswer;
	}

	public final boolean hasWinner() {
		return _hasWinner.get();
	}

	public boolean canDisplayNewImage() {
		return _userList.size() == _currentAck.get();
	}

	public final int incrementAck() {
		return _currentAck.incrementAndGet();
	}

	public final boolean isReboot() {
		return _currentAck.get() == 0;
	}

	public final void rebootAck() {
		_currentAck.set(0);
		this._hasWinner.set(false);
	}

	public final void updatePassStat(final User parLooser) {
		final Manager<User> locUserManager = Managers.getUserManager();
		for(final User locUser : _sockets.keys()) {
			if(locUser.equals(parLooser) == false) continue;
			
			parLooser.setDefaite(parLooser.getDefaite() + 1);
			int locCurrentStat = _currentStat.get(parLooser.getConstName());
			locCurrentStat -= 1;
			_currentStat.put(locUser.getConstName(), Integer.valueOf(locCurrentStat));
		}
		locUserManager.merge(parLooser);
	}
	
	public final void updateStats(final User parWinner) {
		final Manager<User> locUserManager = Managers.getUserManager();
		for(final User locUser : _sockets.keys()) {
			int locCurrentStat = _currentStat.get(locUser.getConstName());
			if(locUser.equals(parWinner)) {
				locUser.setVictoire(locUser.getVictoire().intValue() + 1);
				locCurrentStat += 1;
				_currentStat.put(locUser.getConstName(), Integer.valueOf(locCurrentStat));
			} else {
				locUser.setDefaite(locUser.getDefaite().intValue() + 1);
				locCurrentStat -= 1;
				_currentStat.put(locUser.getConstName(), Integer.valueOf(locCurrentStat));
			}
			locUserManager.merge(locUser);
		}
	}
	
	public final Map<String, Integer> getCurrentScore() {
		return new TreeMap<String, Integer>(_currentStat);
	}

	public final void notifyWinner(final InfoProvider parInfoProvider, final User parWinner, final boolean parHasWinner) {
		final WinnerMessage locMessage = (WinnerMessage) EnumMessage.WINNER.createMessage();
		final String locValueMessage;
		if(parHasWinner == true) {
			locValueMessage = String.format("Le joueur %s a gagné la manche.", parWinner);
			updateStats(parWinner);
		} else {
			locValueMessage = String.format("Il n'y a aucun gagnant. Le joueur %s a fait passer l'image.", parWinner);
			updatePassStat(parWinner);
		}
		locMessage.setLogin(parWinner.getConstName());
		locMessage.setMessage(locValueMessage);
		parInfoProvider.appendMessage(Level.INFO, locValueMessage);
		for(final Map.Entry<User, Socket> locEntry : _sockets.entrySet()) {
			final Socket locSocket = locEntry.getValue();
			try {
				ReadWriterUtil.write(locSocket, locMessage);
			} catch (IOException e) {
				final User locUser = locEntry.getKey();
				// On vire le user de la partie, du cache socket de la partie et des connectés.
				removeUser(locUser);
				final String locLogin = locUser.getConstName();
				_sockets.remove(locUser);
				_currentStat.remove(locUser.getConstName());
				final String locIp = locSocket.getInetAddress().getHostAddress();
				parInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s du joueur %s.", locIp, locLogin), e);
				SystemUtil.close(locSocket);
			}
		}
		this.setChangedImage(false);
	}

	public final void notifyWinner(final InfoProvider parInfoProvider, final User parWinner) {
		this.notifyWinner(parInfoProvider, parWinner, true);
	}

	@Override
	public final String toString() {
		return getConstName();
	}

	@Override
	public final void close() {
		Caches.parties().remove(getConstName());
		_banque = null;
		_currentAck = null;
		_hasChangedImage = null;
		_hasWinner = null;
		for(final Socket locSocket : _sockets.values()) {
			SystemUtil.close(locSocket);
		}
		_currentStat = null;
		_sockets = null;
		_userList.clear();
		_userList = null;
	}
}
