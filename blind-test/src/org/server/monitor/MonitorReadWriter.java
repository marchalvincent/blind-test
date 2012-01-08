package org.server.monitor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public final class MonitorReadWriter extends DataOutputStream {

	static private final MonitorReadWriter INSTANCE = new MonitorReadWriter();
	
	static public final MonitorReadWriter instance() {
		return INSTANCE;
	}
	
	private final transient Scanner _reader;
	
	private MonitorReadWriter() {
		this(System.in);
	}
	
	protected MonitorReadWriter(final InputStream parReader) {
		super(System.err);
		_reader = new Scanner(parReader);
	}
	
	protected final String readLine() {
		return _reader.nextLine();
	}
	
	protected final int readInt() {
		return _reader.nextInt();
	}
	
	protected final void println(final String parObject) {
		System.err.println(parObject);
	}

	@Override
	public final void close() throws IOException {
		super.close();
		_reader.close();
	}
}
