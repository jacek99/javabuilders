/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.Map;
import java.util.Set;

import org.gnome.gtk.Menu;
import org.gnome.gtk.MenuItem;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.gtk.GtkConstants;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * MenuItem handler
 * @author Jacek Furmankiewicz
 *
 */
public class MenuItemTypeHandler extends AbstractTypeHandler implements ITypeHandlerFinishProcessor{

	/**
	 * Constructor
	 */
	public MenuItemTypeHandler() {
		super(GtkConstants.LABEL, GtkConstants.MNEMONIC_LABEL);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		MenuItem instance = null;
		
		String label = (String) typeDefinition.get(GtkConstants.MNEMONIC_LABEL);
		if (label == null) {
			label = (String) typeDefinition.get(GtkConstants.LABEL);
		}
		instance = new MenuItem(label);
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition,
			Object instance) throws BuildException {

		Node node = new Node(parent,key,typeDefinition,instance);
		return node;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		if (1==1)
			return;
		
		MenuItem item = (MenuItem) current.getMainObject();
		Set<Node> nodes = current.getContentNodes(Menu.class);

		if (nodes.size() > 1) {
			throw new BuildException("Only 1 Menu can be specified underneath a MenuItem: {0}", typeDefinition);
		}
		
		for(Node node : nodes){
			Menu menu = (Menu) node.getMainObject();
			//item.setSubmenu(menu);
			break;
		}
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<MenuItem> getApplicableClass() {
		return MenuItem.class;
	}
	

}
