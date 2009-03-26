package org.javabuilders.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Core builder test suite
 * @author Jacek Furmankiewicz
 *
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  CoreTests.class,
  ControlConstraintTests.class,
  IssuesTests.class
})
public class CoreTestSuite {}
