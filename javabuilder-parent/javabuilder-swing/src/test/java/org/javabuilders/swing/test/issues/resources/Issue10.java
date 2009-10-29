package org.javabuilders.swing.test.issues.resources;

import javax.swing.JFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingBuilder;

public class Issue10 extends JFrame {

	BuildResult result = SwingBuilder.build(this);
	
}
