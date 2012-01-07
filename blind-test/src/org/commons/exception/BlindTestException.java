package org.commons.exception;

public final class BlindTestException extends Exception {

	private static final long serialVersionUID = 1L;

	public BlindTestException() {
		super();
	}
	
	public BlindTestException(final String parMessage) {
		super(parMessage);
	}
	
	public BlindTestException(final String parMessage, final Exception parException) {
		super(parMessage, parException);
	}
	
}
