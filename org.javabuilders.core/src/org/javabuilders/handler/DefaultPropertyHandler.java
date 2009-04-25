/**
 * 
 */
package org.javabuilders.handler;

import java.beans.PropertyDescriptor;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.ITypeAsValueSupport;
import org.javabuilders.InvalidPropertyException;
import org.javabuilders.Node;

/**
 * Default class for handling type properties
 * @author Jacek Furmankiewicz
 */
public class DefaultPropertyHandler extends AbstractPropertyHandler implements ITypeAsValueSupport {

	private static final Logger logger = Logger.getLogger(DefaultPropertyHandler.class.getSimpleName());
	
	private static final DefaultPropertyHandler singleton = new DefaultPropertyHandler();
	
	/**
	 * @return Singleton
	 */
	public static DefaultPropertyHandler getInstance() {return singleton;}
	
	/**
	 * List of consumed keys
	 * @param consumedKeys
	 */
	private DefaultPropertyHandler(Set<String> consumedKeys) {
		super(consumedKeys);
	}
	
	/**
	 * Constructor
	 * @param consumedKeys List of consumed keys
	 */
	private DefaultPropertyHandler(String... consumedKeys) {
		super(consumedKeys);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws InvalidPropertyException {
		
		if (key != Builder.CONTENT) {
			
			Object mainObject = node.getMainObject();
			Object value = node.getProperties().get(key);
		
			try {
				PropertyUtils.setProperty(mainObject, key, value);
			} catch (Exception e) {
				
				//try to display more info on which properties are allowed
				StringBuilder builder = new StringBuilder();
				try {
					builder.append("The known property names for " + mainObject.getClass().getSimpleName() + " are:");
					PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(mainObject.getClass());
					for (PropertyDescriptor pd : pds) {
						if (builder.length() > 0) {
							builder.append("\n");
						}
						builder.append(pd.getName());
					}
				} catch (Exception ex) {}
				
				logger.log(Level.SEVERE, e.getMessage() + "\n" + builder.toString(),e);
				
				throw new InvalidPropertyException(e, node.getKey(),key, value, builder.toString());
			}
			
		}
	}

	/**
	 * Can handle any object
	 */
	public Class<?> getApplicableClass() {
		return Object.class;
	}
	
}
