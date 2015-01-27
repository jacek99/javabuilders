package org.javabuilders.test;

import static org.junit.Assert.*;

import org.javabuilders.Builder;
import org.javabuilders.test.resources.GlobalBuildFilePanel;
import org.junit.Test;

public class TypeHandlersTest {

	
	@Test
	public void classAsValueTypeHandler() {
		GlobalBuildFilePanel panel = new GlobalBuildFilePanel();
		assertNotNull(panel.getTestClass());
		assertTrue(Builder.class.equals(panel.getTestClass()));
	}
}
