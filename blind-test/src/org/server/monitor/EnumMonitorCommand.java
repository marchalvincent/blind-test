package org.server.monitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.commons.configuration.EnumConfiguration;
import org.commons.util.IWithId;
import org.commons.util.StringUtil;

public enum EnumMonitorCommand implements IWithId {

	ADD(1, "add", true) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return null;
		}
	},
	REMOVE(2, "remove", true) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return null;
		}
	},
	USER(3, "users", true) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return null;
		}
	},
	CONFIGURATION(4, EnumConfiguration.getKeys(), false) {

		@Override
		final public MonitorCommand createCommand(final String parCommandName, final String[] parArguments) {
			return new MonitorConfigurationCommand(parCommandName);
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
	
	public final boolean contains (final String parCommand) {
		return _command.contains(parCommand);
	}
	
	static public final EnumMonitorCommand getCommand (final String parCommand) {
		if(StringUtil.isEmpty(parCommand)) return null;
		
		for(final EnumMonitorCommand locCommand : values()) {
			if(locCommand.contains(parCommand)) {
				return locCommand;
			}
		}
		return null;
	}
	
	static public final List<String> getCommands() {
		final List<String> locResultat = new ArrayList<String>();
		for(final EnumMonitorCommand locCommand : values()) {
			locResultat.addAll(locCommand._command);
		}
		Collections.sort(locResultat);
		return Collections.unmodifiableList(locResultat);
	}
}
