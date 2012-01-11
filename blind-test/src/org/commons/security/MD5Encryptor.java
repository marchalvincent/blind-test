package org.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import org.commons.configuration.Configuration;
import org.commons.configuration.ConfigurationManager;
import org.commons.logger.InfoProvider;

/**
 * Une classe pouvant crypter en MD5.
 * @author pitton
 *
 */
public final class MD5Encryptor implements Encryptor {

	static private final String NAME = "MD5";

	final private InfoProvider _provider;
	final private MessageDigest _messageDigest;

	public MD5Encryptor(final InfoProvider parInfoProvider) {
		_provider = parInfoProvider;
		_messageDigest = init(_provider);
	}

	private final MessageDigest init(final InfoProvider parInfoProvider) {
		try {
			return MessageDigest.getInstance(NAME);
		} catch (NoSuchAlgorithmException locException) {
			_provider.appendMessage(Level.SEVERE, String.format("L'algorithme %s n'existe pas", NAME), locException);
		}
		return null;
	}

	@Override
	public final String getConstName() {
		return NAME;
	}

	@Override
	public final String encrypt(final String parValue) {
		_messageDigest.reset();
		final Configuration locConfiguration = ConfigurationManager.getConfiguration();
		final byte[] locValueByte = parValue.getBytes(locConfiguration.getCharset());
		final byte[] locByteArray = _messageDigest.digest(locValueByte);
		final StringBuilder locHashString = new StringBuilder();
		for (int i = 0; i < locByteArray.length; i++) {
			final String locHexa = Integer.toHexString(locByteArray[i]);
			if (locHexa.length() == 1) {
				locHashString.append('0');
				locHashString.append(locHexa.charAt(locHexa.length() - 1));
			} else
				locHashString.append(locHexa.substring(locHexa.length() - 2));
		}
		return locHashString.toString();
	}

	@Override
	public InfoProvider getInfoProvider() {
		return _provider;
	}

}
