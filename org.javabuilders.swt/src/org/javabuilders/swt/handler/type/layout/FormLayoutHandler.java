/**
 * 
 */
package org.javabuilders.swt.handler.type.layout;

import static org.javabuilders.swt.SWTBuilder.MARGIN_BOTTOM;
import static org.javabuilders.swt.SWTBuilder.MARGIN_HEIGHT;
import static org.javabuilders.swt.SWTBuilder.MARGIN_LEFT;
import static org.javabuilders.swt.SWTBuilder.MARGIN_RIGHT;
import static org.javabuilders.swt.SWTBuilder.MARGIN_TOP;
import static org.javabuilders.swt.SWTBuilder.MARGIN_WIDTH;
import static org.javabuilders.swt.SWTBuilder.SPACING;

import java.util.Map;

import org.eclipse.swt.layout.FormLayout;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidPropertyFormatException;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * FormLayout handler
 * @author Jacek Furmankiewicz
 *
 */
public class FormLayoutHandler extends AbstractTypeHandler {

	/**
	 * Constructor
	 */
	public FormLayoutHandler() {
		super(MARGIN_BOTTOM,MARGIN_HEIGHT,MARGIN_LEFT,MARGIN_RIGHT,
				MARGIN_TOP, MARGIN_WIDTH, SPACING);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		FormLayout instance = new FormLayout();
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
		
		//process all the child properties by hand - SWT API does not use
		//JavaBean standard for layout objects, so we can't use default logic...sad.
		for(String property : typeDefinition.keySet()) {
			String value = String.valueOf(typeDefinition.get(property));
			
			try {
				@SuppressWarnings("unused")
				Integer intValue = Integer.parseInt(value);
				
//				TODO: add code
				
				
			} catch (NumberFormatException ex) {
				throw new InvalidPropertyFormatException(key,property,value,
						property + ": int", property + ": 8");
			}
			
		}
		
		return node;

	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<FormLayout> getApplicableClass() {
		return FormLayout.class;
	}

}
