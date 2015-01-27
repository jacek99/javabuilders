package org.javabuilders.swing.util;

import java.util.ResourceBundle;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.util.YamlBuilder;

public class SwingYamlBuilder extends YamlBuilder {

	/**
	 * @param root
	 */
	public SwingYamlBuilder(String root) {
		super(root);
	}
	
	/**
	 * Executes a build 
	 * @param caller
	 * @param bundles
	 * @return Result
	 */
	public BuildResult build(Object caller, ResourceBundle...bundles) {
		return build(SwingJavaBuilder.getConfig(),caller,bundles);
	}

}
