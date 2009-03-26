/**
 * 
 */
package org.javabuilders.swing.handler.type.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.SwingJavaBuilderUtils;

/**
 * Handles FlowLayout
 * @author Jacek Furmankiewicz
 *
 */
public class FlowLayoutTypeHandler implements ITypeHandlerFinishProcessor {
	
	private final static FlowLayoutTypeHandler singleton = new FlowLayoutTypeHandler();
	
	/**
	 * @return Singleton
	 */
	public static FlowLayoutTypeHandler getInstance() {return singleton;}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		Container container = SwingJavaBuilderUtils.getParentContainer(current);
		if (container != null) {
			FlowLayout layout = (FlowLayout)current.getMainObject();
			container.setLayout(layout);
		
			for(Node child : current.getParent().getChildNodes(Component.class)) {
				Component c = (Component) child.getMainObject();
				container.add(c);
			}
		}
	}

}
