package org.javabuilders.swing.test.issues.resources;

import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Proper error reporting if no YAML file found
 */
public class Issue20_NoYaml extends JPanel {
	private BuildResult result = SwingJavaBuilder.build(this);
}
