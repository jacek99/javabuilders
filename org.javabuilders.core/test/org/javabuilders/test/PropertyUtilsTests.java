package org.javabuilders.test;

import static org.junit.Assert.*;

import javax.swing.JLabel;

import org.javabuilders.test.resources.GlobalBuildFilePanel;
import org.javabuilders.util.PropertyUtils;
import org.junit.Test;

/**
 * Tests for the PropertyUtils class
 * @author jacek
 *
 */
public class PropertyUtilsTests {

	@Test
	public void testIsValid() {
		JLabel test = new JLabel("TEST");
		
		assertTrue(PropertyUtils.isValid(test, "text"));
		assertFalse(PropertyUtils.isValid(test, "test"));
	}
	
	@Test
	public void testNestedExpression() {
		
		GlobalBuildFilePanel panel = new GlobalBuildFilePanel();
		String test = "BIG TEST";
		
		panel.getLabel().setText(test);
		
		String value = (String) PropertyUtils.getNestedProperty(panel, "label.text");
		
		assertEquals(test, value);
		
	}
	
	@Test
	public void testExpression() {
		JLabel label = new JLabel("TEST");
		
		String value = (String) PropertyUtils.getNestedProperty(label, "text");
		assertEquals("TEST", value);
		
		value = (String) PropertyUtils.getProperty(label, "text");
		assertEquals("TEST", value);
	}
	
	@Test
	public void testSetter() {
		JLabel label = new JLabel("TEST");
		
		PropertyUtils.setProperty(label, "text", "NEW TEST");
		assertEquals("NEW TEST", label.getText());
	}
	

}
