/**
 * 
 */
package org.javabuilders.swing.handler.property;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.TitledBorder;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * A handler for the virtual JComponent "title" property that automatically creates a titled border around the
 * component
 * @author Jacek Furmankiewicz
 *
 */
public class JComponentGroupTitleHandler extends AbstractPropertyHandler {

	public final static String GROUP_TITLE = "groupTitle";
	
	private final static JComponentGroupTitleHandler singleton = new JComponentGroupTitleHandler();
	
	/**
	 * @return Singleton
	 */
	public static JComponentGroupTitleHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	public JComponentGroupTitleHandler() {
		super(GROUP_TITLE);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {
		TitledBorder border = BorderFactory.createTitledBorder(String.valueOf(node.getProperties().get(key)));
		JComponent component = (JComponent)node.getMainObject();
		component.setBorder(border);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return JComponent.class;
	}

}
