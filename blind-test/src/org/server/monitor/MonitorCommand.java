package org.server.monitor;

import java.util.concurrent.Callable;

import org.commons.util.IWithName;

public abstract class MonitorCommand implements IWithName, Callable<String> {

	protected final EnumMonitorCommand _type;
	protected final String[] _arguments;
	protected final String _commandName;
	
	protected MonitorCommand(final EnumMonitorCommand parType, final String parCommandName, final String[] parArguments) {
		_type = parType;
		_commandName = parCommandName;
		_arguments = parArguments;
	}
	
	@Override
	public final String getConstName() {
		return _commandName;
	}	
	
	final public boolean hasArguments() {
		return _type.hasArguments();
	}
}
