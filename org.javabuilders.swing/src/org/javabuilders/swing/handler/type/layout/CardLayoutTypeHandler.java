/**
 * 
 */
package org.javabuilders.swing.handler.type.layout;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JComponent;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.SwingJavaBuilderUtils;

/**
 * CardLayout handler
 * @author Jacek Furmankiewicz
 *
 */
public class CardLayoutTypeHandler implements ITypeHandlerFinishProcessor  {

	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(CardLayoutTypeHandler.class.getName());
	private static final CardLayoutTypeHandler singleton = new CardLayoutTypeHandler();
	
	/**
	 * @return Singleton
	 */
	public static CardLayoutTypeHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private CardLayoutTypeHandler() {}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		Container container = SwingJavaBuilderUtils.getParentContainer(current);
		if (container != null) {
			CardLayout layout = (CardLayout) current.getMainObject();
			container.setLayout(layout);
			
			Node content = current.getParent();
			if (content != null) {
				for(Node child : content.getChildNodes(JComponent.class)) {
					Component c = (Component) child.getMainObject();
					container.add(c,c.getName());
				}
			}
			
			//handle CardLayout name
			String name = current.getStringProperty(Builder.NAME);
			if (name != null) {
				process.addNamedObject(name,layout);
			}

		} else {
			throw new BuildException("Unable to process CardLayout since no parent JComponent was found: {0}",
					typeDefinition);
		}
	}


}
