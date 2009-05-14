/**
 * 
 */
package org.javabuilders.handler;

import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IApplicable;
import org.javabuilders.IKeyValueConsumer;
import org.javabuilders.InvalidTypeException;
import org.javabuilders.Node;

/**
 * Defines a class that handles types (and may optionally its consume
 * key/value pairs as well during creation as well)
 * @author Jacek Furmankiewicz
 *
 */
public interface ITypeHandler extends IKeyValueConsumer, IApplicable {

	/**
	 * Main method that handles types
	 * @param config Builder config
	 * @param process Current build process
	 * @param parent Parent (optional if root, may be null)
	 * @param key Key
	 * @param typeDefinition Key/value pairs that define this type instance
	 * @return Newly created node representing the type
	 * @throws InvalidTypeException 
	 */
	Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key, Map<String,Object> typeDefinition) 
	throws BuildException;

	/**
	 * Version that handles an instance that already exists and just needs to be
	 * used to populate all the properties and children
	 * @param config
	 * @param process
	 * @param parent
	 * @param key
	 * @param typeDefinition
	 * @param instance Object instance to use
	 * @return
	 */
	Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key, Map<String,Object> typeDefinition, Object instance)
	 throws BuildException;
	
	/**
	 * Returns the name of the property that should automatically be populated with values if the content of this
	 * type node is a list.<br/>
	 * Example:
	 * <pre>
	 *     MigLayout:
	 *         - control1
	 *         - control2
	 * </pre>
	 * can be automatically converted to a proper property list:
	 * <pre>
	 *    MigLayout:
	 *         constraints:   <- the collection property name
	 *             - control1
	 *             - control2
	 * </pre>
	 * Useful when wanting to provide a short-hand way of defining a type, mostly for layout managers
	 * @return
	 */
	String getCollectionPropertyName();
	
	/**
	 * Returns the name of the property that should automatically be populated with the value if the content of this
	 * type node is a straight String.<br/>
	 * Example:
	 * <pre>
	 *     MigLayout:
	 *         control1   control2
	 * </pre>
	 * can be automatically converted to a proper property list:
	 * <pre>
	 *    MigLayout:
	 *         layout:   <- the simple value property name
	 *             comtrol1 control 2
	 * </pre>
	 * Useful when wanting to provide a short-hand way of defining a type, mostly for layout managers
	 * @return
	 */
	String getSimpleValuePropertyName();
	
	/**
	 * @return True if should be used for subclasses, false if not
	 */
	boolean isApplicableToSubclasses();
	
}
