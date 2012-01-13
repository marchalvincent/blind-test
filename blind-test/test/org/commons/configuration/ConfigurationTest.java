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
		locConfiguration.setPort("123");
		assertEquals(Integer.valueOf(123), locConfiguration.getPort());
	}

	@Test
	public final void testSetHostName() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		final String locHostName = "173.194.67.105";
		locConfiguration.setHostName(locHostName);
		assertEquals(locHostName, locConfiguration.getHostName());
	}

	@Test
	public final void testSetCharset() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		final Charset locCharset = Charset.defaultCharset();
		locConfiguration.setCharset("utf-8");
		assertEquals(locCharset, locConfiguration.getCharset());
		assertEquals(locCharset.name(), locConfiguration.getCharsetName());
	}

	@Test
	public final void testSetMinLevel() throws BlindTestException {
		final Configuration locConfiguration = new DefaultConfiguration().load();
		locConfiguration.setMinLevel("WARNING");
		assertEquals(Level.WARNING, locConfiguration.getMinLevel());
	}

	@Test
	public final void testLoad() throws Exception {
		final Configuration locDefaultConfiguration = ConfigurationManager.getConfiguration();
		
		
		final Configuration locNewConfiguration = new DefaultConfiguration();
		locNewConfiguration.load();
		assertEquals(locDefaultConfiguration, locNewConfiguration);
	}
}
