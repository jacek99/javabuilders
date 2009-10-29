/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.Map;
import java.util.Set;

import org.gnome.gtk.Paned;
import org.gnome.gtk.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * Paned finish processor
 * @author Jacek Furmankiewicz
 *
 */
public class PanedFinishProcessor implements ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		//add children
		Set<Node> n = current.getContentNodes(Widget.class);
		Node[] nodes = n.toArray(new Node[n.size()]);

		//take first two nodes only
		if (nodes.length > 0) {
			Paned p = (Paned) current.getMainObject();
			p.add1((Widget) nodes[0].getMainObject());
			
			if (nodes.length > 1) {
				p.add2((Widget) nodes[1].getMainObject());
			}
			
			if (nodes.length > 2) {
				throw new BuildException("Only two widgets can be specified under a Panel objects: {0}",typeDefinition);
			}
		}
		
	}

}
