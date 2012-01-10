package org.commons.logger;

import javax.swing.JTextArea;

public final class UiInfoProvider extends FileInfoProvider {

	private final JTextArea _area;
	
	protected UiInfoProvider(final String parFileName, final JTextArea parConsole) {
		super(parFileName);
		
		_area = parConsole;
	}
}
