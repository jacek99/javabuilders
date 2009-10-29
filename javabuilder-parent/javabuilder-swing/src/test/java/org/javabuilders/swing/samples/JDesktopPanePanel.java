package org.javabuilders.swing.samples;

import javax.swing.JDesktopPane;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class JDesktopPanePanel extends SamplePanel {

	JDesktopPane desktop;
	
	public JDesktopPanePanel() throws Exception {
		super();
		BuildResult r = SwingJavaBuilder.build(this);
	}

}
