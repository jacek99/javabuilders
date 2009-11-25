package org.javabuilders.swing.handler.type;

import java.awt.Container;
import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.SwingJavaBuilderUtils;

/**
 * Handles implementing custom focus policy
 * @author Jacek Furmankiewicz
 */
public class FocusFinishProcessor implements ITypeHandlerFinishProcessor {

	@Override
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		Focus focus = (Focus) current.getMainObject();
		
		//get all the specified control names
		
		
		//attach focus policy to parent
		Container c = SwingJavaBuilderUtils.getParentContainer(current);
		if (c != null) {
			c.setFocusTraversalPolicy(focus);
		} else {
			throw new BuildException("Unable to find parent container for Focus: {0}", typeDefinition);
		}
	}

}
