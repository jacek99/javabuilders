/**
 * 
 */
package org.javabuilders.swt.handler.type;

import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;


/**
 * Handles creating a SWT shell
 * @author Jacek Furmankiewicz
 *
 */
public class DialogHandler extends AbstractTypeHandler {

	private static final DialogHandler singleton = new DialogHandler();
	
	/**
	 * Returns the singleton
	 * @return Singleton
	 */
	public static DialogHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private DialogHandler() {}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<Dialog> getApplicableClass() {
		return Dialog.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		Display display = Display.getDefault();
		
		Dialog instance = new Dialog(display.getActiveShell()) {};
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

		return node;
	}



}
