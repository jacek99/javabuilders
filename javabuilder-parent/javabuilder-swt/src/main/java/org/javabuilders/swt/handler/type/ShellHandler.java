/**
 * 
 */
package org.javabuilders.swt.handler.type;

import java.util.Map;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IAllowedPropertyFormat;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;


/**
 * Handles creating a SWT shell
 * @author Jacek Furmankiewicz
 *
 */
public class ShellHandler extends AbstractTypeHandler implements ITypeHandlerFinishProcessor, 
	IAllowedPropertyFormat {

	//properties
	public static final String SIZE="size";
	public static final String LOCATION="location";
	public static final String BOUNDS="bounds";
	public static final String X = "x";
	public static final String Y = "y";
	public static final String WIDTH = "width";
	public static final String HEIGHT = "height";
	
	//values
	public static final String PACKED = "packed";
	public static final String CENTER = "center";
	public static final String CENTER_PARENT = "centerParent";
	
	private static final ShellHandler singleton = new ShellHandler();
	
	/**
	 * Returns the singleton
	 * @return Singleton
	 */
	public static ShellHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private ShellHandler() {}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Shell.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		Display display = Display.getDefault();
		Shell instance = new Shell(display);
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}
	
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
	
		Shell shell = (Shell) current.getMainObject();
		Node content = current.getChildNode(Builder.CONTENT);
		if (content != null) {
			
			Set<Node> menus  = content.getChildNodes(Menu.class); 
			boolean menuBarSet = false;
			for(Node node : menus) {
				if (!menuBarSet) {
					shell.setMenuBar((Menu) node.getMainObject());
					menuBarSet = true;
				} else {
					shell.setMenu((Menu) node.getMainObject());
				}
			}
		}
		
		//handle size/bounds
		if (PACKED.equals(current.getStringProperty(SIZE))) {
			shell.pack();
		} 
		/*
		int x = shell.getLocation().x;
		int y = shell.getLocation().y;
		int width = shell.getSize().x;
		int height = shell.getSize().y;
		*/
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

	public String getRegexPattern(String propertyName) {
		return null;
	}

	public String getValidSample(String propertyName) {
		// TODO Auto-generated method stub
		return null;
	}



}
