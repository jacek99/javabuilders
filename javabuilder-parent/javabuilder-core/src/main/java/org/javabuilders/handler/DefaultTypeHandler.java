/**
 * 
 */
package org.javabuilders.handler;

import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidTypeException;
import org.javabuilders.Node;
import org.javabuilders.util.BuilderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The standard handler to take care of simple object instantiation
 * based on alias. This is automatically used for all defined types that do not
 * have a custom type handler
 * 
 * @author Jacek Furmankiewicz
 *
 */
public class DefaultTypeHandler extends AbstractTypeHandler {

	private final static Logger logger = LoggerFactory.getLogger(DefaultTypeHandler.class);
	
	/**
	 * @param consumedKeys
	 */
	public DefaultTypeHandler() {
		super();
	}


	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#init(org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess result, Node parent, String key, Map<String, Object> typeDefinition) throws InvalidTypeException {

		Object instance = null;
		try {
			Class<?> typeClass = BuilderUtils.getClassFromAlias(result, key, null);
			instance = typeClass.newInstance();
			
			if (logger.isDebugEnabled()) {
				logger.debug("Created object instance of type: %s", typeClass.getName());
			}
			
			return useExistingInstance(config, result, parent, key, typeDefinition, instance);
		} catch (BuildException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Failed to create class " + key + " : " + ex.getMessage(),ex);
			throw new InvalidTypeException(key,ex);
		}		
	}
	
	/**
	 * Creates a new node using an already instantiated object instance
	 * @throws BuildException 
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
