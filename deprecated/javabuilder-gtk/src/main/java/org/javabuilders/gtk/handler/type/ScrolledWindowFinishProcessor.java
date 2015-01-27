/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.Map;
import java.util.Set;

import org.gnome.gtk.ScrolledWindow;
import org.gnome.gtk.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * ScrolledWindow finish processor
 * @author Jacek Furmankiewicz
 *
 */
public class ScrolledWindowFinishProcessor implements
		ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		//add the child widgets
		Set<Node> nodes = current.getContentNodes(Widget.class);
		if (nodes.size() > 1) {
			throw new BuildException("Only a single widget can be specified under a ScrolledWindow: {0}",typeDefinition);
		}
		ScrolledWindow win = (ScrolledWindow) current.getMainObject();
		for(Node node : nodes) {
			Widget w = (Widget) node.getMainObject();
			win.addWithViewport(w);
		}
		
	}

}
