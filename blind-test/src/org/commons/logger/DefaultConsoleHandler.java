package org.commons.logger;

import java.util.logging.StreamHandler;

public final class DefaultConsoleHandler extends StreamHandler {

	protected DefaultConsoleHandler() {
		super(System.err, new DefaultFormatter());
	}
	
}
