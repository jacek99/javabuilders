package org.javabuilders.test;

import static org.junit.Assert.*;

import java.util.List;

import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.LayoutConstraints;
import org.junit.Test;

public class LayoutConstraintsTests {

	@Test
	public void testSimpleStringLiteralControls() {
		String layout =
			" \"First Name\"  firstName";
		
		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, "[]","[]");
	
		
		List<ControlConstraint> controls = c.getCellAt(0,0).getControls();
		
		assertEquals(1, controls.size());
		assertEquals("\"First Name\"", controls.get(0).getControlName());
		
		controls = c.getCellAt(0,1).getControls();
		assertEquals(1, controls.size());
		assertEquals("firstName", controls.get(0).getControlName());
	}
	
	@Test
	public void testComplexSimpleStringLiteralControls() {
		String layout =
			" >\"First Name\"=5  firstName=5";
		
		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, "[]","[]");
	
		
		List<ControlConstraint> controls = c.getCellAt(0,0).getControls();
		
		assertEquals(1, controls.size());
		assertEquals("\"First Name\"", controls.get(0).getControlName());
		assertEquals(5, controls.get(0).getSizeGroup());
		
		controls = c.getCellAt(0,1).getControls();
		assertEquals(1, controls.size());
		assertEquals("firstName", controls.get(0).getControlName());
		
	}
	
}
