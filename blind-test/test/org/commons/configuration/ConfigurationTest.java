package org.commons.configuration;

import static org.junit.Assert.assertEquals;

import java.nio.charset.Charset;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;
import org.junit.Test;

public final class ConfigurationTest {

	@Test
	public final void testSetPort() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		locConfiguration.setPort(123);
		assertEquals(Integer.valueOf(123), locConfiguration.getPort());
	}

	@Test
	public final void testSetHostName() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		final String locHostName = "123.234.345.786";
		locConfiguration.setHostName(locHostName);
		assertEquals(locHostName, locConfiguration.getHostName());
	}

	@Test
	public final void testSetCharset() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		final Charset locCharset = Charset.defaultCharset();
		locConfiguration.setCharset(locCharset);
		assertEquals(locCharset, locConfiguration.getCharset());
		assertEquals(locCharset.name(), locConfiguration.getCharsetName());
	}

	@Test
	public final void testSetMinLevel() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		final Level locMinLevel = Level.WARNING;
		locConfiguration.setMinLevel(locMinLevel);
		assertEquals(locMinLevel, locConfiguration.getMinLevel());
	}

	@Test
	public final void testLoad() throws Exception {
		final Configuration locDefaultConfiguration = ConfigurationManager.getConfiguration();
		
		final Configuration locConfiguration = new DefaultConfiguration();
		locConfiguration.load();
		final String locHostName = "value";
		final Charset locCharset = Charset.defaultCharset();
		final Integer locPort = 123;
		final Level locLevel = Level.OFF;
		locConfiguration.setHostName(locHostName);
		locConfiguration.setCharset(locCharset);
		locConfiguration.setMinLevel(locLevel);
		locConfiguration.setPort(locPort);
		locConfiguration.refresh();
		
		final Configuration locNewConfiguration = new DefaultConfiguration();
		locNewConfiguration.load();
		assertEquals(locNewConfiguration.getCharset(), locConfiguration.getCharset());
		assertEquals(locNewConfiguration.getMinLevel(), locConfiguration.getMinLevel());
		assertEquals(locNewConfiguration.getHostName(), locConfiguration.getHostName());
		assertEquals(locNewConfiguration.getPort(), locConfiguration.getPort());
		assertEquals(locNewConfiguration.getCharsetName(), locConfiguration.getCharsetName());
		assertEquals(locNewConfiguration.getCharset(), locCharset);
		assertEquals(locNewConfiguration.getMinLevel(), locLevel);
		assertEquals(locNewConfiguration.getHostName(), locHostName);
		assertEquals(locNewConfiguration.getPort(), locPort);
		
		locDefaultConfiguration.refresh();
	}
}
