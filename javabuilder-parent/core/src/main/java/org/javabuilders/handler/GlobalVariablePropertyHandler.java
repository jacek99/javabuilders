package org.javabuilders.handler;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.util.PropertyUtils;

/**
 * Generic handler for those cases where the property value is defined as a reference to a
 * global variable, instead of a value in the YAML file
 * @author Jacek Furmankiewicz
 *
 */
public class GlobalVariablePropertyHandler extends AbstractPropertyHandler {

	private static final GlobalVariablePropertyHandler instance = new GlobalVariablePropertyHandler();
	
	/**
	 * @return Singleton
	 */
	public static GlobalVariablePropertyHandler getInstance() {return instance;}
	
	private GlobalVariablePropertyHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node,
			String key) throws BuildException {
		
		Object main = node.getMainObject();
		String name = node.getStringProperty(key);
		
		try {
			Class<?> type = PropertyUtils.getPropertyType(main, key);
			Object value = config.getGlobalVariable(name, type);
			PropertyUtils.setProperty(main, key, value);
		} catch (BuildException ex) {
			throw ex;
		} catch (Exception e) {
			throw new BuildException("Unable to access or set property \"{0}\" : {1}", key, e);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Object> getApplicableClass() {
		return Object.class;
	}

}
