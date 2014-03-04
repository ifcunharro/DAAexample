package es.uvigo.esei.daa;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.dao.StateDAOTest;
import es.uvigo.esei.daa.rest.StateTest;
import es.uvigo.esei.daa.web.StateWebTest;

@SuiteClasses({ StateDAOTest.class, StateTest.class, StateWebTest.class })
@RunWith(Suite.class)
public class StateTestSuite {
}
