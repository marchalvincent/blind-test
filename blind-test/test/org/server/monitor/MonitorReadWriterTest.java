package org.server.monitor;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.server.concurrent.BlindTestExecutor;

public class MonitorReadWriterTest {

	private static MonitorReadWriter _writer;
	private FakeThreadMonitor _thread;
	private BlindTestExecutor _executor;
	
	@BeforeClass
	static public final void beforeClass() {
		_writer = MonitorReadWriter.instance();
	}
	
	@AfterClass
	static public final void afterClass() throws IOException {
		_writer.close();
	}
	
	@Before
	public void before() {
		_thread = new FakeThreadMonitor();
		_executor = new BlindTestExecutor(2);
	}
	
	@After
	public void after() {
		_executor.shutdown();
		_executor = null;
	}

	@Test(timeout = 5000)
	public final void testReadLine() throws Exception {
		final Future<String> locResultat = _executor.submit(_thread);
		final String locResultatValue = locResultat.get();
		final BufferedOutputStream locStream = new BufferedOutputStream(System.out);
		final String locValue = "hostname\n";
		locStream.write(locValue.getBytes("UTF-8"));
		locStream.flush();
		
		Assert.assertEquals(locValue, locResultatValue);
	}

	@Test
	public final void testPrintln() {
	}

	private final class FakeThreadMonitor implements Callable<String> {
		
		@Override
		public final String call() {
			return _writer.readLine();
		}
	}
}
