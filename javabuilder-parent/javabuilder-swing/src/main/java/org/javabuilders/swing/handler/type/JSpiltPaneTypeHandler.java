/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.awt.Component;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JSplitPane;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerAfterCreationProcessor;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Handles creating JSplitPane instances
 * @author Jacek Furmankiewicz
 */
public class JSpiltPaneTypeHandler implements ITypeHandlerAfterCreationProcessor, ITypeHandlerFinishProcessor {

	private static final JSpiltPaneTypeHandler singleton = new JSpiltPaneTypeHandler();
	private static final Logger logger = Logger.getLogger(JSpiltPaneTypeHandler.class.getSimpleName());
	
	/**
	 * @return Singleton
	 */
	public static JSpiltPaneTypeHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private JSpiltPaneTypeHandler() {}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerAfterCreationProcessor#afterCreation(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void afterCreation(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		//splt  pane does not need to be processed by the default layout manager handler
		current.getCustomProperties().put(SwingJavaBuilder.PROPERTY_IGNORE_LAYOUT_MANAGER, Boolean.TRUE);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		Node content = current.getChildNode(Builder.CONTENT);
		JSplitPane pane = (JSplitPane) current.getMainObject();
		
		int i = 0;
		if (content != null) {
			for(Node child : content.getChildNodes()) {
				if (child.getMainObject() instanceof Component) {
					Component c = (Component) child.getMainObject();
					//process just the first 2 components in the list, ignore the others
					if (i == 0) {
						pane.setTopComponent(c);
					} else if (i == 1) {
						pane.setBottomComponent(c);
					} else {
						//warning if more than 2 components defined
						if (logger.isLoggable(Level.WARNING)) {
							logger.warning(MessageFormat.format("Unable to add {0} to JScrollPane. Only first two Component instances are processed",child.getKey()));
						}
					}
					i++;
				}
			}
		}

	}

}
