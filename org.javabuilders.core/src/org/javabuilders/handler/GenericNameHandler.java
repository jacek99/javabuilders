package org.javabuilders.handler;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;

/**
 * Generic name handler (for objects that do not have a name property)
 * @author Jacek Furmankiewicz
 *
 */
public class GenericNameHandler extends AbstractPropertyHandler {

	private static final GenericNameHandler defaultInstance = new GenericNameHandler();
	
	/**
	 * @return Singleton
	 */
	public static final GenericNameHandler getDefaultInstance() {return defaultInstance;}
	
	/**
	 * Constructor
	 */
	public GenericNameHandler() {
		super(Builder.NAME);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node, String key) throws BuildException {
		String name = node.getStringProperty(Builder.NAME);
		if (name != null) {
			process.addNamedObject(name, node.getMainObject());
			
			//if the object happens to have a Name property, set it
			Object bean = node.getMainObject();
			
			try {
				if (String.class.equals(PropertyUtils.getPropertyType(bean, Builder.NAME))) {
					PropertyUtils.setSimpleProperty(bean, Builder.NAME, name);
				}
			} catch (Exception e) {
				//ignore.no Name property found
			} 
			
		}
	}

}
