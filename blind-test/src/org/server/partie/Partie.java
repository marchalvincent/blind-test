package org.server.partie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.commons.entity.Banque;
import org.commons.entity.User;
import org.server.persistence.BanqueManager;
import org.server.persistence.Managers;


public class Partie {
	
	private final List<User> userList;
	private List<Banque> banqueList;
	
	public Partie(){
		banqueList = new ArrayList<Banque>();
		userList = new ArrayList<User>();
	}
	
	public List<User> getUsers(){
		return Collections.unmodifiableList(userList);//Recopie défensive + Renvoi une sous liste non modifiable
	}
	
	public void updateImage(){	
		
		/*if (banqueList.isEmpty()){
			BanqueManager bm =  Managers.createBanqueManager();	
			bm.findAll();
			
			Collections.shuffle(arrayList);
			
		}*/
	}
	
	public void addUser(User user){
		userList.add(user);
	}
	
	public void removeUser(User user){
		userList.remove(user);
	}
	
}
