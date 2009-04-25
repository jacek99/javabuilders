package org.javabuilders.test;

import static org.junit.Assert.*;

import java.util.List;

import org.javabuilders.JBStringUtils;
import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.LayoutConstraints;
import org.junit.Test;

public class LayoutConstraintsTests {

	@Test
	public void testQuotesAndCommas() {
		String test = "11\"a,b\"11,22\"c, d\"22";
		
		List<String> tokens = JBStringUtils.split(test, ',');
		
		assertEquals(2, tokens.size());
		assertEquals("11\"a,b\"11",tokens.get(0));
		assertEquals("22\"c, d\"22",tokens.get(1));
	}
	
	@Test
	public void testCellSplittingWithLiteralControlsAndEmbeddedCommas() {
		String layout =" >\"label,1\"=5,\"label,2\"  date=5";

		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, "[]","[]");
		
		List<ControlConstraint> controls = c.getCellAt(0,0).getControls();
		
		assertEquals(2, controls.size());
		assertEquals("\"label,1\"", controls.get(0).getControlName());
		assertEquals("\"label,2\"", controls.get(1).getControlName());
		assertEquals(5, controls.get(0).getSizeGroup());
		
		controls = c.getCellAt(0,1).getControls();
		assertEquals(1, controls.size());
		assertEquals("date", controls.get(0).getControlName());
	}

	
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
	
	@Test
	public void testComplexSimpleStringLiteralControlsWithEmbeddedCharacters() {
		String layout =
			" >\"Date YYYY/MM/DD\"=5  date=5";
		
		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, "[]","[]");
	
		List<ControlConstraint> controls = c.getCellAt(0,0).getControls();
		
		assertEquals(1, controls.size());
		assertEquals("\"Date YYYY/MM/DD\"", controls.get(0).getControlName());
		assertEquals(5, controls.get(0).getSizeGroup());
		
		controls = c.getCellAt(0,1).getControls();
		assertEquals(1, controls.size());
		assertEquals("date", controls.get(0).getControlName());
		
	}
	
	@Test
	public void testCellSplittingWithLiteralControls() {
		String layout =" >\"Date YYYY/MM/DD\"=5,someLabel  date=5";

		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, "[]","[]");
		
		List<ControlConstraint> controls = c.getCellAt(0,0).getControls();
		
		assertEquals(2, controls.size());
		assertEquals("\"Date YYYY/MM/DD\"", controls.get(0).getControlName());
		assertEquals("someLabel", controls.get(1).getControlName());
		assertEquals(5, controls.get(0).getSizeGroup());
		
		controls = c.getCellAt(0,1).getControls();
		assertEquals(1, controls.size());
		assertEquals("date", controls.get(0).getControlName());
	}
	
	@Test
	public void testCellSplittingWith2LiteralControls() {
		String layout =" >\"Date YYYY/MM/DD\"=5,\"Some other label\"  date=5";

		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, "[]","[]");
		
		List<ControlConstraint> controls = c.getCellAt(0,0).getControls();
		
		assertEquals(2, controls.size());
		assertEquals("\"Date YYYY/MM/DD\"", controls.get(0).getControlName());
		assertEquals("\"Some other label\"", controls.get(1).getControlName());
		assertEquals(5, controls.get(0).getSizeGroup());
		
		controls = c.getCellAt(0,1).getControls();
		assertEquals(1, controls.size());
		assertEquals("date", controls.get(0).getControlName());
	}
	
}
