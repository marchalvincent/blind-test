package org.commons.entity;

import java.io.Serializable;

import org.commons.util.IWithId;
import org.commons.util.IWithName;

public class Stat implements Comparable<Stat>, IWithName, IWithId, Serializable {

	private static final long serialVersionUID = 1L;
	private Integer _id;
	private Integer _defaite;
	private Integer _victoire;
	
	public Stat(){
		
	}
	
	public Stat(final int parId,final int parDefaite, final int parVictoire){
		_id = parId;
		_defaite = parDefaite;
		_victoire = parVictoire;
	}
	@Override
	public Integer getId() {
		return _id;
	}
	
	public final void setId(final Integer parId) {
		_id = parId;
	}
	
	public Integer getVictoire() {
		return _victoire;
	}
	
	public final void setVictoire(final Integer parVictoire) {
		_victoire= parVictoire;
	}
	
	public Integer getDefaite() {
		return _defaite;
	}
	
	public final void setDefaite(final Integer parDefaite) {
		_defaite = parDefaite;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stat other = (Stat) obj;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		return true;
	}

	@Override
	public String getConstName() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int compareTo(final Stat parStat) {
		if(parStat == null) return 1;
		final int locCompareTo = parStat._victoire - parStat._defaite;
		return locCompareTo;
	}
	
	@Override
	public final String toString() {
		return "Victoires : "+_victoire+"\nDéfaites : "+_defaite;
	}

}
