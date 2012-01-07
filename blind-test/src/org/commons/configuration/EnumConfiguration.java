package org.commons.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import org.commons.exception.BlindTestException;
import org.commons.util.IWithName;
import org.commons.util.SystemUtil;

public enum EnumConfiguration implements IWithName {

	MIN_LEVEL("min_level", "-l", Level.WARNING) {

		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setMinLevel(parValue);
		}
	},
	PORT("port", "-p", Integer.valueOf(9999)) {

		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setPort(parValue);
		}
	},
	HOSTNAME("hostname", "-h", "127.0.0.1") {

		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setHostName(parValue);
		}
	},
	CHARSET("charset", "-c", Charset.forName("utf-8")) {

		public final void setConfigurationValue(
				final Configuration parConfiguration, final String parValue) {
			parConfiguration.setCharset(parValue);
		}
	};

	final private String _name;
	final private Object _defaultValue;
	final private String _argument;

	private EnumConfiguration(final String parConstName,
			final String parArgument, final Object parDefaultValue) {
		_name = parConstName;
		_argument = parArgument;
		_defaultValue = parDefaultValue;
	}

	abstract public void setConfigurationValue(
			final Configuration parConfiguration, final String parValue);

	@Override
	final public String getConstName() {
		return _name;
	}

	final public String getArgument() {
		return _argument;
	}

	final public Object getDefaultValue() {
		return _defaultValue;
	}

	@Override
	final public String toString() {
		return getConstName();
	}

	static public final void readArguments(final Configuration parConfiguration, final String[] parValue) throws BlindTestException {
		if (parValue == null || parValue.length == 0 || parConfiguration == null) {
			return;
		}
		final EnumConfiguration[] locConfigurationValues = EnumConfiguration.values();
		final List<String> locValueList = Arrays.asList(parValue);
		for (final EnumConfiguration locConfiguration : locConfigurationValues) {
			// si la valeur n'est pas un argument on le skip
			final int locArgument = locValueList.indexOf(locConfiguration.getArgument());
			if (locArgument == -1) {
				continue;
			}
			// si la valeur de l'argument est vide
			if (locArgument + 1 >= parValue.length) {
				throw new BlindTestException();
			}
			final String locArgValue = parValue[locArgument + 1];
			locConfiguration.setConfigurationValue(parConfiguration, locArgValue);
		}
	}

	static private final String DOCUMENTATION = "conf/documentation.txt";

	static public final String getSupport() {
		FileInputStream locStream = null;
		try {
			final File locFile = new File(DOCUMENTATION);
			locStream = new FileInputStream(locFile);
			final byte[] locByteArray = new byte[((int) locFile.length()) + 1];
			locStream.read(locByteArray);
			return new String(locByteArray, "UTF-8");
		} catch (IOException e) {
			final String locMessage = "Le fichier de documentation %s n'est pas accessible. Impossible d'afficher cette dernière.";
			System.out.println(locMessage);
			e.printStackTrace();
			return locMessage;
		} finally {
			SystemUtil.close(locStream);
		}
	}
}
