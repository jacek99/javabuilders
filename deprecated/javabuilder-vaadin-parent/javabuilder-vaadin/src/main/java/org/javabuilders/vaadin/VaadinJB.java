package org.javabuilders.vaadin;

import java.util.ResourceBundle;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;

public class VaadinJB {

	private static VaadinBuilderConfig config = new VaadinBuilderConfig(null,null,null);

	/**
	 * Returns the standard VaadinBuilder configuration
	 * @return
	 */
	public static VaadinBuilderConfig getConfig() {
		return config;
	}
	
	/**
	 * Main Vaadin Builder methods
	 * @param caller The calling object (it automatically assumes a YAML file with the same name as the objects class (e.g. "MyFrame.java" needs "MyFrame.yaml" exists in the same package)
	 * @return Build result
	 */
	public static BuildResult build(Object caller)  {
		return Builder.build(getConfig(),caller);
	}
	
	/**
	 * Main Vaadin Builder methods
	 * @param caller The calling object (it automatically assumes a YAML file with the same name as the objects class (e.g. "MyFrame.java" needs "MyFrame.yaml" exists in the same package)
	 * @param bundles Optional list of bundles
	 * @return Build result
	 */
	public static BuildResult build(Object caller, ResourceBundle...bundles)  {
		return Builder.build(getConfig(),caller,bundles);
	}
	
	/**
	 * Vaadin Builder method that accepts passed in YAML instead of looking for a separate file
	 * @param caller The calling object 
	 * @param bundles Optional list of bundles
	 * @param yaml Valid YAML content
	 * @return Buld result
	 */
	public static BuildResult build(Object caller, String yaml, ResourceBundle...bundles)  {
		return Builder.buildFromString(getConfig(),caller, yaml, bundles);
	}
}
