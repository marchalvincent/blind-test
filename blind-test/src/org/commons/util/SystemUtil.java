package org.commons.util;


import java.io.Closeable;
import java.io.IOException;


public final class SystemUtil {

	static final public void exit() {
		System.exit(-1);
	}
	
	static final public Exception close(final Closeable parCloseable) {
		if(parCloseable == null) return null;
		
		try {
			parCloseable.close();
		} catch (IOException locException) {
			return locException;
		}
		return null;
	}
	
	private SystemUtil() {}
}
