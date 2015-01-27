package org.javabuilders.swing.test.issues.resources;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class Issue12_JPopupMenuException extends JFrame {
	
	private BuildResult result = SwingJavaBuilder.build(this);

	private void editCustomer() {}
	
	private void deleteCustomer() {}
}
