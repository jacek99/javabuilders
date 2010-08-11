/**
 * 
 */
package org.javabuilders.handler;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IKeyValueConsumer;
import org.javabuilders.Node;

/**
 * Defines a class that handles key/values
 * @author Jacek Furmankiewicz
 */
public interface IPropertyHandler extends IKeyValueConsumer {

	/**
	 * Handles the defined key/value pairs on the current node
	 * @param config Config
	 * @param process Current build process
	 * @param node
	 * @throws BuildException 
	 */
	void handle(BuilderConfig config, BuildProcess process, Node node, String key) throws BuildException;
	
}
