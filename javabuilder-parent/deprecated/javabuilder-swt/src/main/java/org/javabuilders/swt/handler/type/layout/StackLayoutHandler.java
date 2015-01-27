/**
 * 
 */
package org.javabuilders.swt.handler.type.layout;

import java.util.Map;

import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * StackLayout handler
 * @author Jacek Furmankiewicz
 *
 */
public class StackLayoutHandler extends AbstractTypeHandler {

	private static final StackLayoutHandler singleton = new StackLayoutHandler();
	
	/**
	 * @return Singleton
	 */
	public static StackLayoutHandler getInstance() {return singleton;}
	
	private StackLayoutHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		StackLayout instance = new StackLayout();
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent,key,typeDefinition);
		node.setMainObject(instance);
		Composite composite = (Composite)parent.getMainObject();
		composite.setLayout((Layout) instance);
		
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<StackLayout> getApplicableClass() {
		return StackLayout.class;
	}

}
