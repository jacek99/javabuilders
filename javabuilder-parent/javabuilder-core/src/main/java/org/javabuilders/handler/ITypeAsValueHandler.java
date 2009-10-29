package org.javabuilders.handler;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.IApplicable;
import org.javabuilders.Node;

/**
 * Handler for scenarios where the type is not created on its own but is a parameter for a property
 * e.g. Icon, Color, Border, etc.
 * @author Jacek Furmankiewicz
 */
public interface ITypeAsValueHandler<T> extends IApplicable {

	/**
	 * Gets the value for a type
	 * @param process Process
	 * @param node Current node
	 * @param key Current key
	 * @param inputValue Input value
	 * @return The actual value
	 * @throws BuildException if anything goes wrong
	 */
	T getValue(BuildProcess process, Node node, String key, Object inputValue) throws BuildException;
	
	/**
	 * @return The regex string used for validating if the input value is correct
	 */
	String getRegex();
	
	/**
	 * @return Sample of what the input value should look like, for use in error messages
	 */
	String getInputValueSample();
	
}
