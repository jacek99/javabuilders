package org.javabuilders.swing.samples.test;

import org.javabuilders.swing.plugin.glazedlists.SwingGlazedListsConfig;
import org.javabuilders.swing.samples.SwingSamplesFrame;
import org.junit.BeforeClass;
import org.junit.Test;

public class SamplesTest {

	@BeforeClass
	public static void init() {
		SwingGlazedListsConfig.init();
	}
	
	@Test
	public void testSamples() throws Exception {
		new SwingSamplesFrame();
	}
	
}
