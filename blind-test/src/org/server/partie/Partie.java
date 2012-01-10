package org.server.partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.commons.entity.Banque;
import org.commons.entity.User;
import org.commons.util.IWithName;
import org.server.persistence.Manager;
import org.server.persistence.Managers;


public class Partie implements IWithName {
	
	private final List<User> userList;
	private List<Banque> banqueList;
	private String _name;
	
	public Partie(final String name){
		banqueList = new ArrayList<Banque>();
		userList = new ArrayList<User>();
		_name = name;
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
		return banqueList.remove(banqueList.size()-1);
	}
	
}
