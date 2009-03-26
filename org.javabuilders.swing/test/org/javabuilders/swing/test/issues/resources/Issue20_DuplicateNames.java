package org.javabuilders.swing.test.issues.resources;

import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class Issue20_DuplicateNames extends JPanel {

	private BuildResult result = SwingJavaBuilder.build(this);
	
	public Issue20_DuplicateNames() {
		int i = 1;
	}
}
