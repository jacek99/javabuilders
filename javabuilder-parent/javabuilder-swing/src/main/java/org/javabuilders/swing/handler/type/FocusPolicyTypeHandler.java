package org.javabuilders.swing.handler.type;

import java.awt.Component;
import java.awt.Container;
import java.awt.FocusTraversalPolicy;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

@SuppressWarnings("unchecked")
public class FocusPolicyTypeHandler extends AbstractTypeHandler {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> properties) throws BuildException {
		//TODO: some code
		List<String> names = (List<String>) properties.get(Builder.CONTENT);
		if (names != null) {
			@SuppressWarnings("unused")
			List<Component> components = new LinkedList<Component>();
		}
		return null;
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> properties, Object instance) throws BuildException {
		Node node = new Node(parent, key, properties, instance);
		Container c = (Container) parent.getMainObject();
		c.setFocusTraversalPolicy((FocusTraversalPolicy) node.getMainObject());
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return FocusPolicy.class;
	}

}
