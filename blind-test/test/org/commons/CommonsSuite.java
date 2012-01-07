package org.commons;

import org.commons.util.UtilSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Une suite de tests pour tout les packages {@code commons}
 * @author pitton
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	UtilSuite.class
})
public final class CommonsSuite {

}
