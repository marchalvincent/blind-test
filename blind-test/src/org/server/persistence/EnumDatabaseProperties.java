package org.server.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.commons.util.IWithName;
import org.commons.util.IWithSupport;

/**
 * Une énumeration de toutes les propriétés de la base de données.
 * @author pitton
 *
 */
public enum EnumDatabaseProperties implements IWithName, IWithSupport {

	URL("db_url", true) {
		
		@Override
		final public String getSupport() {
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("L'URL de la base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	},
	DRIVER("db_driver", true) {
		
		@Override
		final public String getSupport() {	
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("Le driver de la base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	},
	LOGIN("db_login", true) {
		
		@Override
		final public String getSupport() {	
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("Le login de l'utilisateur de la base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	},
	PASSWORD("db_password", false) {
		
		@Override
		final public String getSupport() {
			return "Aucun support n'est fourni pour cette propriété.";
		}	
	},
	TYPE("db_type", true) {
		
		@Override
		final public String getSupport() {	
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("Le type de base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	},
	HOSTNAME("db_host", true) {
		
		@Override
		final public String getSupport() {	
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("L'adresse de la base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	},
	PORT("db_port", true) {
		
		@Override
		final public String getSupport() {	
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("Le port de la base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	},
	NAME("db_name", true) {
		
		@Override
		final public String getSupport() {	
			final Properties locProperties = Managers.getDatabaseConfiguration();
			return String.format("Le nom de la base de données est : \"%s\".", locProperties.getProperty(getConstName()));
		}
	};
	final private String _name;
	final private boolean _isShowable;
	
	private EnumDatabaseProperties(final String parName, final boolean parIsShowable) {
		_name = parName;
		_isShowable = parIsShowable;
	}
	
	/**
	 * Retourne vrai si l'on peut voir la valeur associée à la propriété de la base.
	 * @return {@code boolean} vrai si l'on peut voir la valeur associée à la propriété de la base.
	 */
	final public boolean isShowable() {
		return _isShowable;
	}
	
	@Override
	final public String getConstName() {
		return _name;
	}
	
	@Override
	final public String toString() {
		return getConstName();
	}
	
	/**
	 * Retourne la {@link List} de tous les noms constants pouvant être vu.
	 * @see EnumDatabaseProperties#isShowable()
	 * @return {@link List} de tous les noms constants pouvant être vu.
	 */
	static public final List<String> getKeys() {
		final EnumDatabaseProperties[] locValues = values();
		final List<String> locValueList = new ArrayList<String>(locValues.length);
		for(final EnumDatabaseProperties locProperties : locValues) {
			if(locProperties.isShowable()) {
				locValueList.add(locProperties.getConstName());
			}
		}
		return locValueList;
	}
}
