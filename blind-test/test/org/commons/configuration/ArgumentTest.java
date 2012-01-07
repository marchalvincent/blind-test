package org.commons.configuration;

import static junit.framework.Assert.*;

import java.nio.charset.Charset;
import java.util.Map;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;
import org.commons.util.StringUtil;
import org.junit.Test;

public class ArgumentTest {

	final private Configuration _configuration = ConfigurationManager.getConfiguration();
	
	@Test
	public final void testReadArgumentsPort() throws BlindTestException {
		EnumConfiguration.readArguments(_configuration, convert("-p", "123"));
		assertEquals(_configuration.getPort(), Integer.valueOf(123));
		
		EnumConfiguration.readArguments(_configuration, convert("-p", "12300"));
		assertEquals(_configuration.getPort(), Integer.valueOf(12300));
		
		_configuration.load();
		final Integer locValue = _configuration.getPort();
		EnumConfiguration.readArguments(_configuration, convert("-p", "salut"));
		assertEquals(_configuration.getPort(), locValue);
	}

	@Test
	public final void testReadArgumentsHostname() throws BlindTestException {
		EnumConfiguration.readArguments(_configuration, convert("-h", "127.0.0.1"));
		assertEquals(_configuration.getHostName(), "127.0.0.1");
		
		EnumConfiguration.readArguments(_configuration, convert("-h", "localhost"));
		assertEquals(_configuration.getHostName(), "localhost");
		
		_configuration.load();
		final String locValue = _configuration.getHostName();
		EnumConfiguration.readArguments(_configuration, convert("-h", "salut"));
		assertEquals(_configuration.getHostName(), locValue);
	}
	
	@Test
	public final void testReadArgumentsCharset() throws BlindTestException {
		Charset locCharset = null;
		final Charset locDefaultCharset = Charset.defaultCharset();
		for(final Map.Entry<String, Charset> locEntry : Charset.availableCharsets().entrySet()) {
			if(StringUtil.equalsIgnoreCase(locEntry.getKey(), "utf-8")) {
				continue;
			}
			final Charset locValue = locEntry.getValue();
			if(locValue.equals(locDefaultCharset)) {
				continue;
			}
			locCharset = locValue;
			break;
		}
		assertNotNull(locCharset);
		EnumConfiguration.readArguments(_configuration, convert("-c", locCharset.name()));
		assertEquals(_configuration.getCharset(), locCharset);
		assertEquals(_configuration.getCharsetName(), locCharset.name());
		
		EnumConfiguration.readArguments(_configuration, convert("-c", locDefaultCharset.name()));
		assertEquals(_configuration.getCharset(), locDefaultCharset);
		assertEquals(_configuration.getCharsetName(), locDefaultCharset.name());
		
		_configuration.load();
		final Charset locValue = _configuration.getCharset();
		EnumConfiguration.readArguments(_configuration, convert("-c", "123charset"));
		assertEquals(_configuration.getCharset(), locValue);
		assertEquals(_configuration.getCharsetName(), locValue.name());
	}

	@Test
	public final void testReadArgumentsMinLevel() throws BlindTestException {
		EnumConfiguration.readArguments(_configuration, convert("-l", "FINEST"));
		assertEquals(_configuration.getMinLevel(), Level.FINEST);
		
		EnumConfiguration.readArguments(_configuration, convert("-l", "OFF"));
		assertEquals(_configuration.getMinLevel(), Level.OFF);
		
		_configuration.load();
		final Level locValue = _configuration.getMinLevel();
		EnumConfiguration.readArguments(_configuration, convert("-l", "test"));
		assertEquals(_configuration.getMinLevel(), locValue);
	}
	
	@Test(expected = BlindTestException.class)
	final public void emptyArgumentValue() throws BlindTestException {
		EnumConfiguration.readArguments(_configuration, convert("-p"));
	}
	
	final public void testDocumentation() {
		
	}
	
	static private final String[] convert(String... parArguments) {
		return parArguments;
	}
	
}
