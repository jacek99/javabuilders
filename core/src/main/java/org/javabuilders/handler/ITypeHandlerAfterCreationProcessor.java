package org.javabuilders.handler;

import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;

/**
 * Interface that is used when a node for a new object is created and some logic needs to be executed on it *before* all the properties
 * are processed. This is different than the ITypeHandlerFinishProcessor which gets executed *after* all properties are processed
 * @author jacek
 *
 */
public interface ITypeHandlerAfterCreationProcessor {

	/**
	 * Runs right after the node is created, before all the children have been processed
	 * @param config Config
	 * @param process Build process
	 */
	void afterCreation(BuilderConfig config, BuildProcess process, Node current, String key, Map<String,Object> typeDefinition) throws BuildException;
	
}
