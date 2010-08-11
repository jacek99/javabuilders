package org.javabuilders.gtk;

import java.util.ResourceBundle;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;

public class GtkJavaBuilder {
	
	private static BuilderConfig config = new GtkJavaBuilderConfig(null,null,null, null);
	
	/**
	 * Returns the standard SwingBuilder configuration
	 * @return
	 */
	public static BuilderConfig getConfig() {
		return config;
	}
	
	/**
	 * Main Swing Builder methods
	 * @param caller The calling object (it automatically assumes a YAML file with the same name as the objects class (e.g. "MyFrame.java" needs "MyFrame.yaml" exists in the same package)
	 * @return Build result
	 */
	public static BuildResult build(Object caller)  {
		return Builder.build(getConfig(),caller);
	}
	
	/**
	 * Main Swing Builder methods
	 * @param caller The calling object (it automatically assumes a YAML file with the same name as the objects class (e.g. "MyFrame.java" needs "MyFrame.yaml" exists in the same package)
	 * @param bundles Optional list of bundles
	 * @return Build result
	 */
	public static BuildResult build(Object caller, ResourceBundle...bundles)  {
		return Builder.build(getConfig(),caller,bundles);
	}
	
	/**
	 * SwingBuilder method that accepts passed in YAML instead of looking for a separate file
	 * @param caller The calling object 
	 * @param bundles Optional list of bundles
	 * @param yaml Valid YAML content
	 * @return Buld result
	 */
	public static BuildResult build(Object caller, String yaml, ResourceBundle...bundles)  {
		return Builder.buildFromString(getConfig(),caller, yaml, bundles);
	}

}
