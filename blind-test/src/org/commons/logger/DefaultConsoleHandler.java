package org.commons.logger;

import java.util.logging.StreamHandler;

import org.server.monitor.MonitorReadWriter;

public final class DefaultConsoleHandler extends StreamHandler {

	protected DefaultConsoleHandler() {
		super(MonitorReadWriter.instance(), new DefaultFormatter());
	}
	
}
