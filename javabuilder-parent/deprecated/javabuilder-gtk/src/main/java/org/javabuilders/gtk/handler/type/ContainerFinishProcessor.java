/**
 * 
 */
package org.javabuilders.gtk.handler.type;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gnome.gtk.Container;
import org.gnome.gtk.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.TypeDefinition;
import org.javabuilders.gtk.GtkConstants;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * Container type handler
 * @author Jacek Furmankiewicz
 */
public class ContainerFinishProcessor implements ITypeHandlerFinishProcessor {

	
	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<Container> getApplicableClass() {
		return Container.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		//did the subclass already handle this layout?
		boolean handled = false;
		TypeDefinition def = config.getTypeDefinition(current.getMainObject().getClass());
		if (def != null && def.getFinishProcessor() != null) {
			handled = true;
		}
		if (current.getCustomProperties().containsKey(GtkConstants.INTERNAL_LAYOUT_HANDLED)) {
			handled = true;
		}
		
		//descendants can indicate they took care of everything, so parent container will skip its logic
		if (!handled) {
		
			Container c = (Container) current.getMainObject();
			
			Set<Node> widgetNodes = current.getContentNodes(Widget.class);
			List<Widget> widgets = Arrays.asList(c.getChildren());
			
			for(Node node : widgetNodes) {
				Widget w = (Widget) node.getMainObject();
				if (!w.equals(c) && !widgets.contains(w) && w.getParent() == null)  {
					c.add(w);
				}
			}
		}
	}
}
