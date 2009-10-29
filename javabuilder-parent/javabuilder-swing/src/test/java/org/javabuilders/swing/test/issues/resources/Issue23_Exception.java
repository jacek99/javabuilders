package org.javabuilders.swing.test.issues.resources;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class Issue23_Exception extends JFrame {

	private BuildResult result = SwingJavaBuilder.build(this);
}
