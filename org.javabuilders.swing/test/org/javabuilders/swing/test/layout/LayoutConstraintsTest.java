package org.javabuilders.swing.test.layout;

import static org.junit.Assert.assertEquals;

import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.Flow;
import org.javabuilders.layout.HAlign;
import org.javabuilders.layout.LayoutCell;
import org.javabuilders.layout.LayoutConstraints;
import org.javabuilders.layout.mig.MigLayoutCommon;
import org.junit.Test;

/**
 * Tests for parsing the layout constraints
 * @author Jacek Furmankiewicz
 */
public class LayoutConstraintsTest {

	
	@Test
	public void layoutOptions() throws Exception {
		String layout =
			"[[gap 5px 10px]]\n" + 
			">findLabel  /textField+2,/textField2  findButton+1+3 [fill] 20\n" +
			"            checkBox1     checkBox2   cancelButton   [top] 20\n" +
			"            checkBox3     checkBox4                  [bottom]\n" +
		    "[fill]      [grow]                    [gap 30]"; //+
		    //"(checkBox1,checkBox2),(checkBox3,checkBox4)\n" +
			//"|findButton,cancelButton|";
		
		LayoutConstraints c = LayoutConstraints.getParsedLayoutConstraints(layout, MigLayoutCommon.DEFAULT_ROW_COLUMN_CONSTRAINT,
				MigLayoutCommon.DEFAULT_ROW_COLUMN_CONSTRAINT);
		
		System.out.println(c);
		
		assertEquals("gap 5px 10px", c.getLayoutConstraints());
		assertEquals("[fill] 20",c.getRowConstraints().get(0));
		assertEquals("[top] 20",c.getRowConstraints().get(1));
		assertEquals("[bottom]",c.getRowConstraints().get(2));
		
		for(LayoutCell cell : c.getCells()) {
			for(ControlConstraint cc : cell.getControls()) {
				
				//cell with multiple controls
				if (cell.getRowIndex() == 0 && cell.getColumnIndex() == 1) {
					//this cell has the two text fields
					assertEquals(2,cell.getControls().size());
					ControlConstraint[] controls = new ControlConstraint[2];
					cell.getControls().toArray(controls);
					
					assertEquals("textField",controls[0].getControlName());
					assertEquals("textField2",controls[1].getControlName());
				} else if (cell.getRowIndex() == 0 && cell.getColumnIndex() == 3) {
					//this cell has the two buttons, spanned vertically
					assertEquals(2,cell.getControls().size());
					assertEquals(Flow.VERTICAL, cell.getFlow());
					ControlConstraint[] controls = new ControlConstraint[2];
					cell.getControls().toArray(controls);
					
					assertEquals("findButton",controls[0].getControlName());
					assertEquals("cancelButton",controls[1].getControlName());
				} else {
					assertEquals(1,cell.getControls().size());
				}
				
				//controls
				String control = cc.getControlName();
				if ("findLabel".equals(control)) {
					assertEquals(0,cell.getRowIndex());
					assertEquals(0,cell.getColumnIndex());
					assertEquals(HAlign.RIGHT,cc.getHAlign());
				} else if ("textField".equals(control)) {
					assertEquals(0,cell.getRowIndex());
					assertEquals(1,cell.getColumnIndex());
					assertEquals(2, cc.getHSpan());
				} else if ("textField2".equals(control)) {
					assertEquals(0,cell.getRowIndex());
					assertEquals(1,cell.getColumnIndex());
				} else if ("findButton".equals(control)) {
					assertEquals(0,cell.getRowIndex());
					assertEquals(3,cell.getColumnIndex());
				} else if ("cancelButton".equals(control)) {
					assertEquals(0,cell.getRowIndex());
					assertEquals(3,cell.getColumnIndex());
				} else if ("checkBox1".equals(control)) {
					assertEquals(1,cell.getRowIndex());
					assertEquals(1,cell.getColumnIndex());
				} else if ("checkBox2".equals(control)) {
					assertEquals(1,cell.getRowIndex());
					assertEquals(2,cell.getColumnIndex());
				} else if ("checkBox3".equals(control)) {
					assertEquals(2,cell.getRowIndex());
					assertEquals(1,cell.getColumnIndex());
				} else if ("checkBox4".equals(control)) {
					assertEquals(2,cell.getRowIndex());
					assertEquals(2,cell.getColumnIndex());
				} else {
					throw new Exception("Unknown control name: " + cc.getControlName());
				}
				
			}
		}
	}
	
}
