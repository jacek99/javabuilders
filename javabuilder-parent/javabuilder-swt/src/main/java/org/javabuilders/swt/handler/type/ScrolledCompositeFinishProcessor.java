package org.javabuilders.swt.handler.type;

import java.util.Map;
import java.util.Set;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Control;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * Scrolled Composite handler
 * @author Jacek Furmankiewicz
 *
 */
public class ScrolledCompositeFinishProcessor implements
		ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		Set<Node> controls = current.getChildNodes(Control.class);
		if (controls.size() > 1) {
			throw new BuildException("You can only specify one Control under ScrolledComposite: {0}",typeDefinition);
		} else {
			for(Node control : controls) {
				final ScrolledComposite sc = (ScrolledComposite) current.getMainObject();
				final Control cn = (Control) control.getMainObject();
				sc.setContent(cn);
				
				sc.setMinHeight(1);
				sc.setMinWidth(1);
				sc.setExpandVertical(true);
				sc.setExpandHorizontal(true);
				
				break;
			}
		}

	}

}
