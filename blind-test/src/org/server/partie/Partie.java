package org.server.partie;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
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
	private List<Banque> banqueList;
	private String _name;
	private AbstractCache<User, Socket> _sockets;
	private Banque _banque;
	private AtomicInteger _currentAck;
	private AtomicBoolean _hasChangedImage;
	private AtomicBoolean _hasWinner;
	private final int _size;

	public Partie(final String name, final int parSize){
		_size = parSize;
		banqueList = new ArrayList<Banque>();
		_userList = new ArrayList<User>();
		_name = name;
		_sockets = Caches.createSocketCache();
		_currentAck = new AtomicInteger(0);
		_hasChangedImage = new AtomicBoolean(false);
		_hasWinner = new AtomicBoolean(false);
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

	public void updateImage(){	
		Manager<Banque> bm = Managers.createBanqueManager();	
		final List<Banque> listImage = bm.findAll();
		final int locSize = listImage.size();
		if(locSize > _size) {
			for(int i = 0 ; i < locSize - _size ; ++i) {
				listImage.remove(i);
			}
		}
		Collections.shuffle(listImage);//On tire aléatoirement
		banqueList.addAll(listImage);
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
	}

	public void removeUser(User user){
		_userList.remove(user);
		_sockets.remove(user);
	}

	@Override
	public String getConstName() {
		return _name;
	}

	public boolean isFinished(){
		return banqueList.isEmpty();
	}

	//Renvoi le prochain élement qu'on doit afficher
	public final Banque next(){
		this.setChangedImage(true);
		_banque = banqueList.remove(banqueList.size()-1);
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

	public final void updateStats(final User parWinner) {
		final Manager<User> locUserManager = Managers.createUserManager();
		for(final User locUser : _sockets.keys()) {
			if(locUser.equals(parWinner)) {
				locUser.setVictoire(locUser.getVictoire().intValue() + 1);
			} else {
				locUser.setDefaite(locUser.getDefaite().intValue() + 1);
			}
			locUserManager.merge(locUser);
		}
	}

	public final void notifyWinner(final InfoProvider parInfoProvider, final String parWinner, final boolean parNoWinner) {
		final WinnerMessage locMessage = (WinnerMessage) EnumMessage.WINNER.createMessage();
		final String locValueMessage;
		if(parNoWinner == false) {
			locValueMessage = String.format("Le joueur %s a gagné la manche.", parWinner);
		} else {
			locValueMessage = String.format("Il n'y a aucun gagnant. Le joueur %s a fait passer l'image.", parWinner);
		}
		locMessage.setLogin(parWinner);
		locMessage.setMessage(locValueMessage);
		parInfoProvider.appendMessage(Level.INFO, locValueMessage);
		for(final Map.Entry<User, Socket> locEntry : _sockets.entrySet()) {
			final Socket locSocket = locEntry.getValue();
			try {
				ReadWriterUtil.write(locSocket, locMessage);
				parInfoProvider.appendMessage(Level.INFO, String.format("Le joueur %s a été notifié qu'il y a un gagnant.", locEntry.getKey().getConstName()));
			} catch (IOException e) {
				final User locUser = locEntry.getKey();
				// On vire le user de la partie, du cache socket de la partie et des connectés.
				removeUser(locUser);
				final String locLogin = locUser.getConstName();
				_sockets.remove(locUser);
				final String locIp = locSocket.getInetAddress().getHostAddress();
				parInfoProvider.appendMessage(Level.SEVERE, String.format("Impossible d'écrire dans la socket d'adresse %s du joueur %s.", locIp, locLogin), e);
				SystemUtil.close(locSocket);
			}
		}
		this.setChangedImage(false);
	}

	public final void notifyWinner(final InfoProvider parInfoProvider, final String parWinner) {
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
		_sockets = null;
		_userList.clear();
		_userList = null;
	}
}
