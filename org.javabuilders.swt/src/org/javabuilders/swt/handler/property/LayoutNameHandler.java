/**
 * 
 */
package org.javabuilders.swt.handler.property;

import org.eclipse.swt.widgets.Layout;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;


/**
 * Handler for the fake "name" property that does not exist in SWT
 * (unlike in Swing). Allows to keep track of objects by name
 * @author Jacek Furmankiewicz
 */
public class LayoutNameHandler extends AbstractPropertyHandler {

	private final static LayoutNameHandler singleton = new LayoutNameHandler();
	
	/**
	 * Returns the singleton
	 * @return Singleton
	 */
	public static LayoutNameHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private LayoutNameHandler() {
		super(Builder.NAME);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node,
			String key) throws BuildException {
		Layout layout = (Layout)node.getMainObject();
		String name = String.valueOf(node.getProperties().get(key));
		
		process.addNamedObject(name, layout);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Layout.class;
	}

}
