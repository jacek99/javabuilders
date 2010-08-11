package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;
import org.junit.Test;

/**
 * Tests logic for autocreation of controls based on name
 */
public class AutoCreationTest {

	@Test
	public void testPrefixCreation() {
		
		String yaml = 
			"JPanel: \n" +
			"  - JTextField(name=txtNormal)\n" +
			"  - MigLayout: |\n" +
			"      txtNormal txtAuto tglAuto btnAuto cbxAuto rbAuto cmbAuto lstAuto txaAuto tblAuto trAuto \n" +
			"      sldAuto   prgAuto pwdAuto spnAuto sepAuto ";
		
		BuildResult r = SwingJavaBuilder.build(this, yaml);

		assertControl(r,"txtNormal",JTextField.class);
		assertControl(r,"txtAuto",JTextField.class);
		assertControl(r,"tglAuto",JToggleButton.class);
		assertControl(r,"btnAuto",JButton.class);
		assertControl(r,"cbxAuto",JCheckBox.class);
		assertControl(r,"rbAuto", JRadioButton.class);
		assertControl(r,"cmbAuto",JComboBox.class);
		assertControl(r,"lstAuto",JList.class);
		assertControl(r,"txaAuto",JTextArea.class);
		assertControl(r,"tblAuto",JTable.class);
		assertControl(r,"trAuto",JTree.class);
		assertControl(r,"sldAuto",JSlider.class);
		assertControl(r,"prgAuto",JProgressBar.class);
		assertControl(r,"pwdAuto",JPasswordField.class);
		assertControl(r,"spnAuto",JSpinner.class);
		assertControl(r,"sepAuto",JSeparator.class);
	}

	
	private void assertControl(BuildResult r, String name, Class<?> type) {
		assertNotNull(r.get(name));
		assertEquals(type,r.get(name).getClass());
	}
	
	
	@SuppressWarnings("unused")
	private void auto() {
		//dummym method for btnAuto
	}
	
}
