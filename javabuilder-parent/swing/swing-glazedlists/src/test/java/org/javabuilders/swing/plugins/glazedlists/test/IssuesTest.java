package org.javabuilders.swing.plugins.glazedlists.test;

import org.javabuilders.swing.plugin.glazedlists.SwingGlazedListsConfig;
import org.javabuilders.swing.plugins.glazedlists.test.resource.Issue126;
import org.junit.BeforeClass;
import org.junit.Test;

public class IssuesTest {

	@BeforeClass
	public static void init() {
		SwingGlazedListsConfig.init();
	}
	
	@Test
	public void testIssue126() {
		//should be OK and not throw an exception
		Issue126 panel = new Issue126();
	}
	
}
