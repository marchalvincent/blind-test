package org.server.partie;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.commons.cache.AbstractCache;
import org.commons.cache.Caches;
import org.commons.entity.Banque;
import org.commons.entity.User;
import org.commons.util.IWithName;
import org.commons.util.StringUtil;
import org.server.persistence.Manager;
import org.server.persistence.Managers;


public class Partie implements IWithName {
	
	private final List<User> userList;
	private List<Banque> banqueList;
	private String _name;
	private AbstractCache<User, Socket> _sockets;
	private Banque _banque;
	private AtomicBoolean _hasWinner;
	
	public Partie(final String name){
		banqueList = new ArrayList<Banque>();
		userList = new ArrayList<User>();
		_name = name;
		_sockets = Caches.createSocketCache();
		_hasWinner = new AtomicBoolean(false);
	}
	
	public List<User> getUsers(){
		return Collections.unmodifiableList(userList);//Recopie défensive + Renvoi une sous liste non modifiable
	}
	
	public void updateImage(){	
		if (isFinished()){
			Manager<Banque> bm = Managers.createBanqueManager();	
			List<Banque>listImage = bm.findAll();
			Collections.shuffle(listImage);//On tire aléatoirement
			banqueList.addAll(listImage);
		}
	}
	
	public void addUser(User user){
		userList.add(user);
	}
	
	public void removeUser(User user){
		userList.remove(user);
	}

	@Override
	public String getConstName() {
		return _name;
	}
	
	public boolean isFinished(){
		return banqueList.isEmpty();
	}
	
	//Renvoi le prochain élement qu'on doit afficher
	public Banque next(){
		_banque = banqueList.remove(banqueList.size()-1);
		return _banque;
	}	
	
	public final boolean isValidAnswer(final String parAnswer) {
		return StringUtil.equals(parAnswer, _banque.getAnswer());
	}
	
	public final boolean hasWinner() {
		return _hasWinner.get();
	}
	
	public final boolean setWinner(final boolean parWinner) {
		return _hasWinner.getAndSet(parWinner);
	}
	
}
