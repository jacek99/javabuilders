package org.javabuilders.test.resources;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.annotations.BuildFile;
import org.javabuilders.test.TestBuilderConfig;

@BuildFile("/org/javabuilders/test/resources/Common.yaml")
public class GlobalBuildFilePanel extends JPanel {

	private JLabel test;
	
	public GlobalBuildFilePanel() {
		BuilderConfig config = new TestBuilderConfig(JPanel.class,JLabel.class);
		Builder.build(config, this);
	}
	
	public JLabel getLabel() {
		return test;
	}
}
