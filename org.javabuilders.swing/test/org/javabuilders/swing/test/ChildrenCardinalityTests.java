package org.javabuilders.swing.test;

import org.javabuilders.ChildrenCardinalityException;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.junit.Test;

/**
 * Swing builder children cardinalities testing
 * @author Jacek Furmankiewicz
 *
 */
public class ChildrenCardinalityTests {

	@Test
	public void testJFrameSimple() {
		new SwingYamlBuilder("JFrame()") {{
		}}.build(this);
	}

	@Test(expected=ChildrenCardinalityException.class)
	public void testJFrameInvalidObject() {
		new SwingYamlBuilder("JFrame():") {{
			___("- TableColumn()");
		}}.build(this);
	}
	
	@Test
	public void testScrollPane() {
		new SwingYamlBuilder("JScrollPane():") {{
			___("JPanel()");
		}}.build(this);
	}

	@Test(expected=ChildrenCardinalityException.class)
	public void testScrollPaneInvalidList() {
		new SwingYamlBuilder("JScrollPane():") {{
			___("- JPanel()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testScrollPaneInvalidListMoreThanOne() {
		new SwingYamlBuilder("JScrollPane():") {{
			___("- JPanel()");
			___("- JPanel()");
		}}.build(this);
	}

	@Test
	public void testJSplitPane() {
		new SwingYamlBuilder("JSplitPane()") {{
		}}.build(this);
	}

	@Test
	public void testJSplitPaneWithOne() {
		new SwingYamlBuilder("JSplitPane():") {{
			___("- JPanel()");
		}}.build(this);
	}

	@Test
	public void testJSplitPaneWithTwo() {
		new SwingYamlBuilder("JSplitPane():") {{
			___("- JPanel()");
			___("- JPanel()");
		}}.build(this);
		
	}

	@Test(expected=ChildrenCardinalityException.class)	
	public void testJSplitPaneWithInvalidThree() {
		new SwingYamlBuilder("JSplitPane():") {{
			___("- JPanel()");
			___("- JPanel()");
			___("- JPanel()");
		}}.build(this);
		
	}

	@Test(expected=ChildrenCardinalityException.class)	
	public void testJSplitPaneWithInvalidChild() {
		new SwingYamlBuilder("JSplitPane():") {{
			___("JPanel()");
		}}.build(this);
	}
	
	@Test
	public void testJPanel() {
		new SwingYamlBuilder("JPanel()") {{
		}}.build(this);
	}

	@Test
	public void testJPanelWith1() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JButton()");
		}}.build(this);
	}

	@Test
	public void testJPanelWith2() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JButton()");
			___("- JButton()");
		}}.build(this);
	}
	
	@Test
	public void testJPanelWith3() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JButton()");
			___("- JButton()");
			___("- JButton()");
		}}.build(this);
	}

	@Test(expected=ChildrenCardinalityException.class)
	public void testJPanelWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("JButton()");
		}}.build(this);
	}

	@Test
	public void testJLabel() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JLabel()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJLabelWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JLabel():");
			_____("- JLabel():");
		}}.build(this);
	}
	
	@Test
	public void testJButton() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JButton()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJButtonWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JButton():");
			_____("- JLabel():");
		}}.build(this);
	}

	@Test
	public void testJTextField() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JTextField()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJTextFieldWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JTextField():");
			_____("- JLabel():");
		}}.build(this);
	}

	@Test
	public void testJRadioButton() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JRadioButton()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJRadioButtonWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JRadioButton():");
			_____("- JLabel():");
		}}.build(this);
	}

	@Test
	public void testJCheckBox() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JCheckBox()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJCheckBoxWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JCheckBox():");
			_____("- JLabel():");
		}}.build(this);
	}

	@Test
	public void testJList() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JList()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJListWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JList():");
			_____("- JLabel():");
		}}.build(this);
	}

	@Test
	public void testJProgressBar() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JProgressBar()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJProgressBarWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JProgressBar():");
			_____("- JLabel():");
		}}.build(this);
	}

	@Test
	public void testJSpinner() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JSpinner()");
		}}.build(this);
	}
	
	@Test(expected=ChildrenCardinalityException.class)
	public void testJSpinnerWithInvalidChild() {
		new SwingYamlBuilder("JPanel():") {{
			___("- JSpinner():");
			_____("- JLabel():");
		}}.build(this);
	}

}
