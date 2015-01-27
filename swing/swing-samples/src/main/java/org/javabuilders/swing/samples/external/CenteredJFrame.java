package org.javabuilders.swing.samples.external;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings("serial")
public class CenteredJFrame extends JFrame {

	@SuppressWarnings("unused")
	private BuildResult result = SwingJavaBuilder.build(this);
}
