package org.commons.logger;

import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.commons.util.DateUtil;
import org.joda.time.DateTime;

public final class DefaultFormatter extends Formatter {

	protected DefaultFormatter() {
		
	}
	
	@Override
	public final String format(final LogRecord parRecord) {
		final StringBuilder locBuilder = new StringBuilder();
		final Level locLevel = parRecord.getLevel();
		final String locMessage = parRecord.getMessage();
		locBuilder.append(locLevel.getName()).append(" ");
		final DateTime locDate = DateUtil.getCurrentDateTime();
		locBuilder.append(DateUtil.format(locDate)).append(" ").append(locMessage);
		final Throwable locThrowable = parRecord.getThrown();
		if(locThrowable != null) {
			locBuilder.append("\n").append(locThrowable.getMessage());
		}
		locBuilder.append("\n");
		locBuilder.trimToSize();
		return locBuilder.toString();
	}

}
