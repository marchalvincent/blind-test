package org.server.monitor;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.configuration.EnumConfiguration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.server.concurrent.BlindTestExecutor;

public class MonitorCommandTest {

	private static BlindTestExecutor _executor;
	static private Configuration _configuration;
	
	@BeforeClass
	public final static void beforeClass() {
		_executor = new BlindTestExecutor(5);
		_configuration = ConfigurationManager.getConfiguration();
	}
	
	@AfterClass
	static public final void afterClass() {
		_configuration = null;
		_executor.shutdown();
		_executor = null;
	}
	
	@Test
	public final void testHostName() throws InterruptedException, ExecutionException {
		final String locHostName = _configuration.getHostName();
		final MonitorCommand locCommand = EnumMonitorCommand.CONFIGURATION.createCommand(EnumConfiguration.HOSTNAME.getConstName(), null);
		final Future<String> locFuture = _executor.submit(locCommand);
		assertEquals(true, locFuture.get().contains(locHostName));
	}
	
	@Test
	public final void testCharset() throws InterruptedException, ExecutionException {
		final String locCharset = _configuration.getCharsetName();
		final MonitorCommand locCommand = EnumMonitorCommand.CONFIGURATION.createCommand(EnumConfiguration.CHARSET.getConstName(), null);
		final Future<String> locFuture = _executor.submit(locCommand);
		assertEquals(true, locFuture.get().contains(locCharset));
	}
	
	@Test
	public final void testMinLevel() throws InterruptedException, ExecutionException {
		final String locMinLevelName = _configuration.getMinLevel().getName();
		final MonitorCommand locCommand = EnumMonitorCommand.CONFIGURATION.createCommand(EnumConfiguration.MIN_LEVEL.getConstName(), null);
		final Future<String> locFuture = _executor.submit(locCommand);
		assertEquals(true, locFuture.get().contains(locMinLevelName));
	}
	
	@Test
	public final void testPort() throws InterruptedException, ExecutionException {
		final String locPort = _configuration.getPort().toString();
		final MonitorCommand locCommand = EnumMonitorCommand.CONFIGURATION.createCommand(EnumConfiguration.PORT.getConstName(), null);
		final Future<String> locFuture = _executor.submit(locCommand);
		assertEquals(true, locFuture.get().contains(locPort));
	}

}
