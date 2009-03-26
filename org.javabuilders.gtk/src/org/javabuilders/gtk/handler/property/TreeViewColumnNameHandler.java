/**
 * 
 */
package org.javabuilders.gtk.handler.property;

import org.gnome.gtk.TreeViewColumn;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * TreeViewColumnn.name handler
 * @author Jacek Furmankiewicz
 *
 */
public class TreeViewColumnNameHandler extends AbstractPropertyHandler {

	/**
	 * Constructor
	 */
	public TreeViewColumnNameHandler() {
		super(Builder.NAME);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node,
			String key) throws BuildException {

		TreeViewColumn c = (TreeViewColumn)node.getMainObject();
		String name = String.valueOf(node.getProperties().get(key));
		process.addNamedObject(name, c);
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<TreeViewColumn> getApplicableClass() {
		return TreeViewColumn.class;
	}

}
