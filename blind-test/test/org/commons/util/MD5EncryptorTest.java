package org.commons.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.commons.logger.InfoProvider;
import org.commons.logger.InfoProviderManager;
import org.commons.security.Encryptor;
import org.commons.security.MD5Encryptor;
import org.junit.Assert;
import org.junit.Test;

public class MD5EncryptorTest {

	final List<String> ARRAYS = Arrays.asList("salut", "/;,~test.", "12345./");
	
	@Test
	public final void testEncrypt() {
		final InfoProvider locInfoProvider = InfoProviderManager.getFileProvider();
		final Encryptor locEncryptor = new MD5Encryptor(locInfoProvider);
		final List<String> locFirstList = new ArrayList<String>(ARRAYS.size());
		for(final String locValue : ARRAYS) {
			locFirstList.add(locEncryptor.encrypt(locValue));
		}
		
		final List<String> locSecondList = new ArrayList<String>(ARRAYS.size());
		for(final String locValue : ARRAYS) {
			locSecondList.add(locEncryptor.encrypt(locValue));
		}
		Assert.assertEquals(locFirstList, locSecondList);
	}
}
