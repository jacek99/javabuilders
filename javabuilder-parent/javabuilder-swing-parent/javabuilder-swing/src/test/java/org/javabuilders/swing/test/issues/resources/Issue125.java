package org.javabuilders.swing.test.issues.resources;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.annotations.BuildFile;
import org.javabuilders.swing.SwingJavaBuilder;

@BuildFile("/abs/location/Issue125.yml")
@SuppressWarnings("serial")
public class Issue125 extends JPanel {

	public JButton someButton = null;
	
	BuildResult result = SwingJavaBuilder.build(this);
	
	public Issue125()  {
		
	}
	
}
