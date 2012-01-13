package org.commons.util;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Une suite de tests pour tous les packages utilitaires
 * @author pitton
 *
 */
@RunWith(Suite.class)
@SuiteClasses({ 
	StringUtilTest.class,
	BanqueFacadeTest.class,
	MD5EncryptorTest.class
	})
public class UtilSuite {

}
