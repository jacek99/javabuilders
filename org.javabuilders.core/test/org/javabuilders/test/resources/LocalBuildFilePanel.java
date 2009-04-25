package org.javabuilders.test.resources;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.annotations.BuildFile;
import org.javabuilders.test.TestBuilderConfig;

@BuildFile("Common.yaml")
public class LocalBuildFilePanel extends JPanel {

	private JLabel test;
	
	public LocalBuildFilePanel() {
		BuilderConfig config = new TestBuilderConfig(JPanel.class,JLabel.class);
		Builder.build(config, this);
	}
	
	public JLabel getLabel() {
		return test;
	}

}
