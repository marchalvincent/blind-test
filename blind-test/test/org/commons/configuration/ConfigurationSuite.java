package org.commons.configuration;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ 
	ArgumentTest.class, 
	ConfigurationTest.class 
})
public class ConfigurationSuite {

}
