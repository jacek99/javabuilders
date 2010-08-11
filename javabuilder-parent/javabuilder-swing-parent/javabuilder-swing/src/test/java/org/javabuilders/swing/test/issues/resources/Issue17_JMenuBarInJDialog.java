package org.javabuilders.swing.test.issues.resources;

import javax.swing.JDialog;
import javax.swing.JMenuBar;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Issue 17 - JMenuBar does not work in JDialog
 * @author Jacek Furmankiewicz
 *
 */
public class Issue17_JMenuBarInJDialog extends JDialog {

	public JMenuBar menuBar;
	private BuildResult result = SwingJavaBuilder.build(this);
	
	private void buttonSave() {}
	private void buttonLoad() {}
	private void buttonExcel() {}
	
	
}
