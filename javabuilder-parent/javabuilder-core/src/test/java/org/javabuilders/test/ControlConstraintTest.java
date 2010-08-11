/**
 * 
 */
package org.javabuilders.test;

import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.HAlign;
import org.javabuilders.layout.LayoutCell;
import org.javabuilders.layout.LayoutException;
import org.javabuilders.layout.Size;
import org.javabuilders.layout.VAlign;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.javabuilders.layout.ControlConstraint.*;

/**
 * JUnit tests for ControlConstraint
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("unused")
public class ControlConstraintTest {

	private final String control1="control1";
	
	@Test
	public void justName() throws Exception {
		ControlConstraint c = new ControlConstraint(control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void rightAligned() throws Exception {
		ControlConstraint c = new ControlConstraint(HALIGN_RIGHT + control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.RIGHT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void centered() throws Exception {
		ControlConstraint c = new ControlConstraint(HALIGN_CENTER + control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.CENTER, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void middle() throws Exception {
		ControlConstraint c = new ControlConstraint(VALIGN_MIDDLE + control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.MIDDLE, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void bottom() throws Exception {
		ControlConstraint c = new ControlConstraint(VALIGN_BOTTOM + control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.BOTTOM, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void middleAndCenter() throws Exception {
		ControlConstraint c = new ControlConstraint("" + HALIGN_CENTER + VALIGN_MIDDLE +  control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.CENTER, c.getHAlign());
		assertEquals(VAlign.MIDDLE, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void centerAndMiddle() throws Exception {
		ControlConstraint c = new ControlConstraint("" + HALIGN_CENTER + VALIGN_MIDDLE +  control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.CENTER, c.getHAlign());
		assertEquals(VAlign.MIDDLE, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void bottomAndCenter() throws Exception {
		ControlConstraint c = new ControlConstraint("" + VALIGN_BOTTOM + HALIGN_CENTER +  control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.CENTER, c.getHAlign());
		assertEquals(VAlign.BOTTOM, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void centerAndBottom() throws Exception {
		ControlConstraint c = new ControlConstraint("" + HALIGN_CENTER + VALIGN_BOTTOM +  control1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.CENTER, c.getHAlign());
		assertEquals(VAlign.BOTTOM, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	
	@Test
	public void nameAndHSpan1() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + 1);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}	
	
	@Test
	public void nameAndHSpan2() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + 2);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(2,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void nameAndHSpan1AndVSpan2() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + 1 + LayoutCell.SPAN_INDICATOR + 2);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(2,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}	
	
	@Test
	public void nameAndHSpan2AndVSpan2() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + 2 + LayoutCell.SPAN_INDICATOR + 2);

		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(2,c.getHSpan());
		assertEquals(2,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void testMinWidth() {
		ControlConstraint c = new ControlConstraint("control1<");
		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.MIN, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void testPrefWidth() {
		ControlConstraint c = new ControlConstraint("control1|");
		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		
		assertEquals(Size.PREF, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}
	
	@Test
	public void testMaxWidth() {
		ControlConstraint c = new ControlConstraint("control1>");
		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		
		assertEquals(Size.MAX, c.getHSize());
		assertEquals(Size.DEFAULT, c.getVSize());
	}

	@Test
	public void testMinHeight() {
		ControlConstraint c = new ControlConstraint("control1^");
		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.MIN, c.getVSize());
	}
	
	@Test
	public void testPrefHeight() {
		ControlConstraint c = new ControlConstraint("control1-");
		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.PREF, c.getVSize());
	}
	
	@Test
	public void testMaxHeight() {
		ControlConstraint c = new ControlConstraint("control1/");
		assertEquals(control1,c.getControlName());
		assertEquals(HAlign.DEFAULT, c.getHAlign());
		assertEquals(VAlign.DEFAULT, c.getVAlign());
		assertEquals(1,c.getHSpan());
		assertEquals(1,c.getVSpan());
		
		assertEquals(Size.DEFAULT, c.getHSize());
		assertEquals(Size.MAX, c.getVSize());
	}
	
	@Test 
	public void testAllSizeCombination() {
		ControlConstraint c = new ControlConstraint("control1+2+3=4</");
		assertEquals(control1,c.getControlName());
		assertEquals(2,c.getHSpan());
		assertEquals(3,c.getVSpan());
		assertEquals(new Integer(4),c.getSizeGroup());
		assertEquals(Size.MIN, c.getHSize());
		assertEquals(Size.MAX, c.getVSize());
	}

	
	@Test(expected=LayoutException.class)
	public void invalidSpanException() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR);
	}
	
	@Test(expected=LayoutException.class)
	public void invalidNumericHSpanException() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + "A");
	}
	
	@Test(expected=LayoutException.class)
	public void invalidNumericVSpanException() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + 1 + LayoutCell.SPAN_INDICATOR + "A");
	}
	
	@Test(expected=LayoutException.class)
	public void invalidNumberOfSpansException() throws Exception {
		ControlConstraint c = new ControlConstraint("" + control1 + LayoutCell.SPAN_INDICATOR + 1 + LayoutCell.SPAN_INDICATOR + "1" + LayoutCell.SPAN_INDICATOR + 4);
	}
	

	
}
