/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.swing.SwingAction;

/**
 * Menu/toolbar action handler
 * @author Jacek Furmankiewicz
 */
public class SwingActionHandler extends AbstractTypeHandler {

	private static final SwingActionHandler singleton = new SwingActionHandler();
	
	public static final String SHORT_DESCRIPTION ="shortDescription";
	public static final String SHORT_DESC ="shortDesc";
	public static final String LONG_DESCRIPTION ="longDescription";
	public static final String LONG_DESC ="longDesc";
	
	/**
	 * @return Singleton
	 */
	public static SwingActionHandler getInstance() {return singleton;}
	
	private SwingActionHandler() {
		super(Builder.NAME);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		return useExistingInstance(config, process, parent, key, typeDefinition, new SwingAction());
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {
		
		Node node = new Node(parent, key, typeDefinition);
		node.setMainObject(instance);
		
		String name = node.getStringProperty(Builder.NAME);
		if (name == null) {
			throw new BuildException("An Action requires a 'name' attribute: {0}",typeDefinition);
		}
		
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<SwingAction> getApplicableClass() {
		return SwingAction.class;
	}

}
