package org;

import org.commons.configuration.ConfigurationSuite;
import org.commons.util.UtilSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.server.monitor.MonitorSuite;
import org.server.persistence.PersistenceSuite;

@RunWith(Suite.class)
@SuiteClasses({
	UtilSuite.class,
	ConfigurationSuite.class,
	MonitorSuite.class,
	PersistenceSuite.class
})
public class FastTestSuite {

}
