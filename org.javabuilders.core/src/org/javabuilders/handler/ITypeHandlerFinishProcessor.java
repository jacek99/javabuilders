/**
 * 
 */
package org.javabuilders.handler;

import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;

/**
 * Interface used when a type handler wants to get called after all of its
 * children have been processed
 * @author Jacek Furmankiewicz
 *
 */
public interface ITypeHandlerFinishProcessor {

	/**
	 * Runs at the end, after all the children have been processed
	 * @param config Config
	 * @param process Build process
	 */
	void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String,Object> typeDefinition) throws BuildException;

}
