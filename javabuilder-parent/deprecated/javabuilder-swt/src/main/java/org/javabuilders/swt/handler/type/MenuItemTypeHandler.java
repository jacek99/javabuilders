/**
 * 
 */
package org.javabuilders.swt.handler.type;

import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.swt.SwtJavaBuilder;
import org.javabuilders.swt.SwtBuilderUtils;

/**
 * Handler for creating menu items
 * @author Jacek Furmankiewicz
 */
public class MenuItemTypeHandler extends AbstractTypeHandler {

	private static final MenuItemTypeHandler singleton = new MenuItemTypeHandler();
	
	/**
	 * @return Singleton
	 */
	public static MenuItemTypeHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private MenuItemTypeHandler() {
		super(SwtJavaBuilder.STYLE);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		Object parentMenu = parent.getMainObject();
		MenuItem instance =null;
		int style = SwtBuilderUtils.getSWTStyle((String) typeDefinition.get(SwtJavaBuilder.STYLE));
		
		if (parentMenu instanceof Menu) {
			Menu menu = (Menu) parentMenu;
			instance = new MenuItem(menu,style);
		} else if (parentMenu instanceof MenuItem) {
			MenuItem item = (MenuItem) parentMenu;
			Menu menu = item.getMenu();
			
			if (menu == null) {
				menu = new Menu(SwtBuilderUtils.getShell(parent),SWT.DROP_DOWN);
				item.setMenu(menu);
			}
			instance = new MenuItem(menu,style);
		} else {
			throw new BuildException("MenuItem \"{0}\" can only be under a Menu or MenuItem",key);
		}
		
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
		
		
		//handle the accelerator automatically
		if (node.containsProperty(SwtJavaBuilder.TEXT)) {
			String[] parts = node.getStringProperty(SwtJavaBuilder.TEXT).split("\t");
			if (parts.length > 1) {
				String acc = parts[1];
				String[] components = acc.split("\\+");
				int accelerator = SWT.NONE;
				
				for(int i = 0; i < components.length; i++) {
					if (i < (components.length - 1)) {
						int metaKey = SwtBuilderUtils.getSWTConstantFromExactName(components[i].toUpperCase());
						accelerator = accelerator | metaKey;
					} else {
						//last one must be the letter
						accelerator = accelerator | components[i].charAt(0); 
					}
				}
				
				MenuItem item = (MenuItem) instance;
				item.setAccelerator(accelerator);
			}
		}
		
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return MenuItem.class;
	}
}
