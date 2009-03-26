/**
 * 
 */
package org.javabuilders.handler;

import java.util.Set;
import java.util.logging.Logger;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.NamedObjectPropertyValue;
import org.javabuilders.Node;

/**
 * Handler for those properties that were flagged to be references to named objects
 * @author Jacek Furmankiewicz
 *
 */
public class DefaultPropertyAsNamedObjectHandler extends
		AbstractPropertyHandler {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(DefaultPropertyAsNamedObjectHandler.class.getSimpleName());
	
	/**
	 * @param consumedKeys
	 */
	public DefaultPropertyAsNamedObjectHandler(String... consumedKeys) {
		super(consumedKeys);
	}

	/**
	 * @param consumedKeys
	 */
	public DefaultPropertyAsNamedObjectHandler(Set<String> consumedKeys) {
		super(consumedKeys);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {
		
		NamedObjectPropertyValue request = new NamedObjectPropertyValue(
				node.getMainObject(),key,node.getStringProperty(key));
		
		//these get procesed by the builder at the very end
		result.getPropertiesAsNamedObjects().add(request);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Object.class;
	}

}
