package org.javabuilders.test;

import static org.junit.Assert.*;

import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.DefaultResize;
import org.javabuilders.layout.mig.MigLayoutCommon;
import org.junit.Before;
import org.junit.Test;

public class MigLayoutCommonTest {

	private String control1 = "control1";
	private StringBuilder builder = new StringBuilder();
	
	@Before
	public void before() {
		builder.setLength(0);
	}
	
	@Test
	public void testResizeBoth() {
		ControlConstraint c = new ControlConstraint("control1");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("grow",builder.toString());
	}

	@Test
	public void testResizeXAxis() {
		ControlConstraint c = new ControlConstraint("control1");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.X_AXIS, null);
		assertEquals("growx",builder.toString());
	}

	@Test
	public void testResizeYAxis() {
		ControlConstraint c = new ControlConstraint("control1");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.Y_AXIS, null);
		assertEquals("growy",builder.toString());
	}

	@Test
	public void testNoResize() {
		ControlConstraint c = new ControlConstraint("control1");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.NONE, null);
		assertEquals("",builder.toString());
	}
	
	@Test
	public void testMinWidth() {
		ControlConstraint c = new ControlConstraint("control1<");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("width min!, growy",builder.toString());
	}

	@Test
	public void testPrefWidth() {
		ControlConstraint c = new ControlConstraint("control1|");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.X_AXIS, null);
		assertEquals("width pref!, height pref!",builder.toString());
	}

	@Test
	public void testPrefWidth2() {
		ControlConstraint c = new ControlConstraint("control1|");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("width pref!, growy",builder.toString());
	}

	@Test
	public void testPrefWidth3() {
		ControlConstraint c = new ControlConstraint("control1|");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.NONE, null);
		assertEquals("width pref!, height pref!",builder.toString());
	}

	@Test
	public void testMaxWidth() {
		ControlConstraint c = new ControlConstraint("control1>");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("width max!, growy",builder.toString());
	}

	@Test
	public void testMinHeight() {
		ControlConstraint c = new ControlConstraint("control1^");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("growx, height min!",builder.toString());
	}

	@Test
	public void testPrefHeight() {
		ControlConstraint c = new ControlConstraint("control1-");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.X_AXIS, null);
		assertEquals("growx, height pref!",builder.toString());
	}

	@Test
	public void testPrefHeight2() {
		ControlConstraint c = new ControlConstraint("control1-");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("growx, height pref!",builder.toString());
	}

	@Test
	public void testPrefHeight3() {
		ControlConstraint c = new ControlConstraint("control1-");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.NONE, null);
		assertEquals("width pref!, height pref!",builder.toString());
	}

	@Test
	public void testMaxHeight() {
		ControlConstraint c = new ControlConstraint("control1/");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("growx, height max!",builder.toString());
	}

	@Test
	public void testMinWidthAndMaxHeight() {
		ControlConstraint c = new ControlConstraint("control1</");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("width min!, height max!",builder.toString());
	}
	
	@Test
	public void testPrefWidthAndPrefHeight() {
		ControlConstraint c = new ControlConstraint("control1|-");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("width pref!, height pref!",builder.toString());
	}

	@Test
	public void testMaxWidthAndMaxHeight() {
		ControlConstraint c = new ControlConstraint("control1>/");
		MigLayoutCommon.handleResize(builder, c, DefaultResize.BOTH, null);
		assertEquals("width max!, height max!",builder.toString());
	}

}
