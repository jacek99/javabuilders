/**
 * 
 */
package org.javabuilders.handler;

import lombok.extern.slf4j.Slf4j;
import org.javabuilders.*;
import org.javabuilders.util.BuilderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * An empty type handler that does not actually instantiate an object.
 * This is required sometimes if a parent node requires a child to be
 * passed in the constructor. In this case a ITypeHandlerFinishProcessor
 * will be required to create actual control and process it
 *
 * @author Jacek Furmankiewicz
 *
 */
@Slf4j
public class EmptyTypeHandler extends AbstractTypeHandler {


	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#init(org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess result, Node parent, String key, Map<String, Object> typeDefinition) throws InvalidTypeException {

		Object instance = null;
		try {
			//Class<?> typeClass = BuilderUtils.getClassFromAlias(result, key, null);
			//instance = typeClass.newInstance();

			//log.debug("Created object instance of type: {}", typeClass.getName());

            return new Node(parent, key, typeDefinition, null);
		} catch (BuildException ex) {
			throw ex;
		} catch (Exception ex) {
			log.error("Failed to create class {}:{}",key, ex.getMessage());
			throw new InvalidTypeException(key,ex);
		}
	}

	/**
	 * Creates a new node using an already instantiated object instance
	 * @throws org.javabuilders.BuildException
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess result, Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		if (instance == null) {
			throw new NullPointerException("instance cannot be null");
		}
		if (!getApplicableClass().isInstance(instance)) {
			throw new BuildException("instance is not of type: " + getApplicableClass().getName());
		}
		Node node = new Node(parent, key, typeDefinition, instance);
		return node;
	}


	/**
	 * Can handle any object
	 * @return Object class
	 */
	public Class<?> getApplicableClass() {
		return Object.class;
	}

}
