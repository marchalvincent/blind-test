package org.commons.logger;

import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.client.ui.LogClient;

public final class UiInfoProvider extends FileInfoProvider {

	private final LogClient _area;
	
	public UiInfoProvider(final String parFileName, final LogClient parLogClient) {
		super(parFileName);
		_area = parLogClient;
	}
	
	public InfoProvider appendMessage (final Level parLevel, final String parMessage) {
		return this.appendMessage(parLevel, parMessage, null);
	}
	
	public InfoProvider appendMessage(final Level parLevel, final String parMessage, final Throwable parThrowable) {
		super.appendMessage(parLevel, parMessage, parThrowable);
		if (isShowable(parLevel)) {
			LogRecord locLog = new LogRecord(parLevel, parMessage);
			locLog.setThrown(parThrowable);
			String res = getFormatter().format(locLog);
			_area.ecrire (res);
		}
		return this;
	}
}
