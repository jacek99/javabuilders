package org.javabuilders.test.resources;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.annotations.BuildFile;
import org.javabuilders.test.TestBuilderConfig;

@BuildFile("/org/javabuilders/test/resources/Common.yaml")
public class GlobalBuildFilePanel extends JPanel {

	private JLabel test;
	
	private Class testClass = null;
	
	public GlobalBuildFilePanel() {
		BuilderConfig config = new TestBuilderConfig(JPanel.class,JLabel.class);
		config.forType(JPanel.class).children(Component.class,0,Integer.MAX_VALUE);
		Builder.build(config, this);
	}
	
	public JLabel getLabel() {
		return test;
	}
	
	/**
	 * @return the testClass
	 */
	public Class getTestClass() {
		return testClass;
	}

	/**
	 * @param testClass the testClass to set
	 */
	public void setTestClass(Class testClass) {
		this.testClass = testClass;
	}
}
