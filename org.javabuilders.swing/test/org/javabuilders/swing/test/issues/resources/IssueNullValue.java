package org.javabuilders.swing.test.issues.resources;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings("serial")
public class IssueNullValue extends JFrame {

	@SuppressWarnings("unused")
	private BuildResult result = SwingJavaBuilder.build(this);
}
