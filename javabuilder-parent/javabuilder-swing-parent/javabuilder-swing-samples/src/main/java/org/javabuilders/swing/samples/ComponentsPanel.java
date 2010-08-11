package org.javabuilders.swing.samples;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings("serial")
public class ComponentsPanel extends SamplePanel {

	@SuppressWarnings("unused")
	private BuildResult result = SwingJavaBuilder.build(this);
	
	public ComponentsPanel() throws Exception {		
		super();
	}
}
