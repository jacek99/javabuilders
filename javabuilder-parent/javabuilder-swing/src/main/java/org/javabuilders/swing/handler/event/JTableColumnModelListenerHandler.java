/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.util.Set;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Table column model listener handler
 * @author Jacek Furmankiewicz
 *
 */
public class JTableColumnModelListenerHandler extends AbstractPropertyHandler {

	/**
	 * @param consumedKeys
	 */
	public JTableColumnModelListenerHandler(String... consumedKeys) {
		super(consumedKeys);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param consumedKeys
	 */
	public JTableColumnModelListenerHandler(Set<String> consumedKeys) {
		super(consumedKeys);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node,
			String key) throws BuildException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		// TODO Auto-generated method stub
		return null;
	}

}
