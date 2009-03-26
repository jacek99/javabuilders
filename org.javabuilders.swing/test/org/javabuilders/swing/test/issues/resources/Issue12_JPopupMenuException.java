package org.javabuilders.swing.test.issues.resources;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingBuilder;

public class Issue12_JPopupMenuException extends JFrame {
	
	private BuildResult result = SwingBuilder.build(this);

	private void editCustomer() {}
	
	private void deleteCustomer() {}
}
