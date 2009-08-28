package org.javabuilders.swing.test;

import org.javabuilders.swing.test.layout.LayoutConstraintsTest;
import org.javabuilders.swing.test.validations.ValidationsTest;
import org.javabuilders.test.CoreTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	CoreTestSuite.class,
	TypeDefinitionTest.class,
	IssuesTests.class,
	LayoutConstraintsTest.class,
	ValidationsTest.class,
	DataBindingTests.class,
	SamplesTests.class,
	JTableTests.class,
	ChildrenCardinalityTests.class,
	GlazedListsTests.class,
	TypeHandlersTest.class
})

public class SwingTestSuite {}
