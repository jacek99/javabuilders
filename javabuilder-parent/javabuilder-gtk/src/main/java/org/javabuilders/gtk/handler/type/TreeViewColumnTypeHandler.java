/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.Map;

import org.gnome.gtk.TreeView;
import org.gnome.gtk.TreeViewColumn;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.gtk.GtkConstants;
import org.javabuilders.handler.AbstractTypeHandler;

/**
 * TreeView column type handler
 * @author Jacek Furmankiewicz
 */
public class TreeViewColumnTypeHandler extends AbstractTypeHandler {

	/**
	 * Constructor
	 */
	public TreeViewColumnTypeHandler() {
		super(GtkConstants.TITLE);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process,
			Node parent, String key, Map<String, Object> typeDefinition)
			throws BuildException {
	
		Node tvNode = parent.getParent();
		if (tvNode != null && tvNode.getMainObject() instanceof TreeView) {
			TreeView tv = (TreeView) tvNode.getMainObject();
			TreeViewColumn instance = tv.appendColumn();
			return useExistingInstance(config, process, parent, key, typeDefinition, instance);
		} else {
			throw new BuildException("TreeViewColumn must be created under a TreeView object: {0}. Parent node: {1}", typeDefinition, tvNode);
		}
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
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<TreeViewColumn> getApplicableClass() {
		return TreeViewColumn.class;
	}

}
