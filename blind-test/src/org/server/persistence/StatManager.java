package org.server.persistence;

import java.util.List;

import org.commons.entity.Stat;


public class StatManager extends AbstractManager<Stat> {

	
	static private final String ADD = "INSERT INTO stat VALUES (NULL,?,?)";
	static private final String MERGE = "UPDATE stat SET defaite= ?, victoire = ? WHERE idStat = ?";
	static private final String REMOVE_ID = "DELETE FROM stat WHERE idStat = ?";
	static private final String FINDALL = "SELECT * FROM stat";
	static private final String FIND_ID = "SELECT * from stat WHERE idStat = ?";
	
	
	
	@Override
	public Stat add(Stat parObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(String parName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean remove(Integer parId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Stat merge(Stat parObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stat find(int parId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stat find(String parName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Stat> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
