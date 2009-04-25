/**
 * 
 */
package org.javabuilders.swt.handler.type.layout;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidPropertyFormatException;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * FillLayout handler
 * @author Jacek Furmankiewicz
 *
 */
public class FillLayoutHandler extends AbstractTypeHandler implements ITypeChildrenHandler {

	private static final FillLayoutHandler singleton = new FillLayoutHandler();
	
	private Map<String,Integer> styleMap = new HashMap<String,Integer>();
	
	/**
	 * Returns singleton
	 * @return Singleton
	 */
	public static FillLayoutHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	public FillLayoutHandler() {
		super(SwtJavaBuilder.MARGIN_HEIGHT,SwtJavaBuilder.MARGIN_WIDTH,SwtJavaBuilder.SPACING,SwtJavaBuilder.STYLE);
		
		styleMap.put("horizontal", SWT.HORIZONTAL);
		styleMap.put("vertical", SWT.VERTICAL);
	}


	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		FillLayout instance = new FillLayout();
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
		FillLayout layout = (FillLayout)instance;
		
		//handle all 4 potential properties
		if (typeDefinition.containsKey(SwtJavaBuilder.MARGIN_HEIGHT)) {
			String value = String.valueOf(typeDefinition.get(SwtJavaBuilder.MARGIN_HEIGHT));
			try {
				int intValue = Integer.parseInt(value);
				layout.marginHeight = intValue;
			} catch (NumberFormatException ex) {
				throw new InvalidPropertyFormatException(key,SwtJavaBuilder.MARGIN_HEIGHT,value,SwtJavaBuilder.MARGIN_HEIGHT + ": int",SwtJavaBuilder.MARGIN_HEIGHT + ": 8",ex);
			}
		}
		
		if (typeDefinition.containsKey(SwtJavaBuilder.MARGIN_WIDTH)) {
			String value = String.valueOf(typeDefinition.get(SwtJavaBuilder.MARGIN_WIDTH));
			try {
				int intValue = Integer.parseInt(value);
				layout.marginWidth = intValue;
			} catch (NumberFormatException ex) {
				throw new InvalidPropertyFormatException(key,SwtJavaBuilder.MARGIN_WIDTH,value,SwtJavaBuilder.MARGIN_WIDTH + ": int",SwtJavaBuilder.MARGIN_WIDTH + ": 8",ex);
			}
		}
		
		if (typeDefinition.containsKey(SwtJavaBuilder.SPACING)) {
			String value = String.valueOf(typeDefinition.get(SwtJavaBuilder.SPACING));
			try {
				int intValue = Integer.parseInt(value);
				layout.spacing = intValue;
			} catch (NumberFormatException ex) {
				throw new InvalidPropertyFormatException(key,SwtJavaBuilder.SPACING,value,SwtJavaBuilder.SPACING + ": int",SwtJavaBuilder.SPACING + ": 8",ex);
			}
		}
		
		if (typeDefinition.containsKey(SwtJavaBuilder.STYLE)) {
			String value = String.valueOf(typeDefinition.get(SwtJavaBuilder.STYLE)).toLowerCase();
			
			if (styleMap.containsKey(value)) {
				Integer type = styleMap.get(value);
				layout.type = type;
			} else {
				throw new InvalidPropertyFormatException(key,SwtJavaBuilder.STYLE,value,SwtJavaBuilder.STYLE + ": horizontal|vertical",SwtJavaBuilder.STYLE + ": vertical");
			}
		}
		
		
		Composite composite = (Composite)parent.getMainObject();
		composite.setLayout(layout);
		
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<FillLayout> getApplicableClass() {
		return FillLayout.class;
	}

}
