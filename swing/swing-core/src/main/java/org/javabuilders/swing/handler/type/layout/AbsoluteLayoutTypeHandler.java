package org.javabuilders.swing.handler.type.layout;

import java.awt.Component;
import java.awt.Container;
import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.SwingJavaBuilderUtils;

/**
 * AbsoluteLayoutTypeHandler for absolute layout positioning.
 * Uses {@link AbsoluteLayout} marker class to indicate that the layout should be set as absolute
 * through method {@link java.awt.Container#setLayout(java.awt.LayoutManager)}.
 * 
 * @author Luca Domenichini
 */
public class AbsoluteLayoutTypeHandler implements ITypeHandlerFinishProcessor {
	
	private static final AbsoluteLayoutTypeHandler singleton = new AbsoluteLayoutTypeHandler();
	
	public static AbsoluteLayoutTypeHandler getInstance() {
		return singleton;
	}
	
	private AbsoluteLayoutTypeHandler() {
		
	}

	@Override
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key,
			Map<String, Object> typeDefinition) throws BuildException {

		Container container = SwingJavaBuilderUtils.getParentContainer(current);
		if (container != null) {
			container.setLayout(null);
			
			for(Node child : current.getParent().getChildNodes(Component.class)) {
				Component c = (Component) child.getMainObject();
				container.add(c);
			}
		} else {
			throw new BuildException("Unable to process AbolusteLayout since no parent JComponent was found: {0}",
					typeDefinition);
		}
	}
	
}
