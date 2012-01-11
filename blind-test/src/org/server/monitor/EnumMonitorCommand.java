package org.server.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.commons.configuration.EnumConfiguration;
import org.commons.util.IWithId;
import org.commons.util.StringUtil;
import org.server.persistence.EnumDatabaseProperties;

/**
 * L'énumération de toutes les commandes pouvant être effectuées par un utilisateur.
 * @author pitton
 *
 */
public enum EnumMonitorCommand implements IWithId {

	ADD(1, "add", true) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorAddImageCommand(parCommandName, parArguments);
		}
	},
	REMOVE(2, "remove", true) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorRemoveCommand(parCommandName, parArguments);
		}
	},
	USER(3, "users", true) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorUserConnected(parCommandName);
		}
	},
	CONFIGURATION(4, EnumConfiguration.getKeys(), false) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorConfigurationCommand(parCommandName);
		}
	},
	DATABASE(5, EnumDatabaseProperties.getKeys(), false) {
		
		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorDatabaseCommand(parCommandName);
		}
	},
	PARTIE(6, "partie", false) {
		
		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorPartie(parCommandName);
		}
	};
	
	final private int _id;
	final private List<String> _command;
	final private boolean _hasArguments;
	
	private EnumMonitorCommand(final int parId, final String parCommand, final boolean parHasArgument) {
		this(parId, Arrays.<String>asList(parCommand), parHasArgument);
	}
	
	private EnumMonitorCommand(final int parId, final List<String> parCommandList, final boolean parHasArguments) {
		_id = parId;
		_command = parCommandList;
		_hasArguments = parHasArguments;
	}
	
	/**
	 * Crée le {@link MonitorCommand} avec les arguments spécifiés.
	 * @param parArguments {@code String[]} un tableau d'arguments.
	 * @return {@link MonitorCommand} une commande.
	 */
	abstract public MonitorCommand createCommand(final String parCommandName, final String[] parArguments);

	final public boolean hasArguments() {
		return _hasArguments;
	}
	
	@Override
	public final Integer getId() {
		return Integer.valueOf(_id);
	}
	
	/**
	 * Retourne vrai si le nom d'une commande est contenu dans le type de commande.
	 * @param parCommand {@link String} le nom d'une commande
	 * @return {@code boolean} vrai si le nom de la commande spécifiée est contenu dans le type de commande.
	 */
	public final boolean contains (final String parCommand) {
		return _command.contains(parCommand);
	}
	
	/**
	 * Retourne le {@code EnumMonitorCommand} associée au nom d'une commande.
	 * @param parCommand {@link String} le nom d'une commande.
	 * @return le {@code EnumMonitorCommand} associée au nom d'une commande.
	 */
	static public final EnumMonitorCommand getCommand (final String parCommand) {
		if(StringUtil.isEmpty(parCommand)) return null;
		
		for(final EnumMonitorCommand locCommand : values()) {
			if(locCommand.contains(parCommand)) {
				return locCommand;
			}
		}
		return null;
	}
	
	/**
	 * Retourne la {@link List} de toutes les commandes ayant des arguments.
	 * @return la {@link List} de toutes les commandes ayant des arguments.
	 */
	static public final List<String> getArgumentCommands() {
		final List<String> locResultat = new ArrayList<String>();
		for(final EnumMonitorCommand locCommand : values()) {
			if(locCommand.hasArguments()) {
				locResultat.addAll(locCommand._command);
			}
		}
		Collections.sort(locResultat);
		return Collections.unmodifiableList(locResultat);
	}

	/**
	 * Retourne la {@link List} de toutes les commandes n'ayant pas d'arguments.
	 * @return la {@link List} de toutes les commandes n'ayant pas d'arguments.
	 */
	static public final List<String> getNoArgumentCommands() {
		final List<String> locResultat = new ArrayList<String>();
		for(final EnumMonitorCommand locCommand : values()) {
			if(locCommand.hasArguments() == false) {
				locResultat.addAll(locCommand._command);
			}
		}
		Collections.sort(locResultat);
		return Collections.unmodifiableList(locResultat);
	}
}
