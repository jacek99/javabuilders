package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import javax.swing.JTextField;

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
			"      txtNormal txtAuto";
		
		BuildResult r = SwingJavaBuilder.build(this, yaml);

		assertNotNull(r.get("txtNormal"));
		assertEquals(JTextField.class,r.get("txtNormal").getClass());
		assertNotNull(r.get("txtAuto"));
		assertEquals(JTextField.class,r.get("txtAuto").getClass());
	}

	
}
