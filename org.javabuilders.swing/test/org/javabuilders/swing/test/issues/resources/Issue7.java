package org.javabuilders.swing.test.issues.resources;

import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingBuilder;

@SuppressWarnings("serial")
public class Issue7 extends JPanel {

	BuildResult result = SwingBuilder.build(this);
	
	public Issue7() throws Exception {
		
	}
	
}
