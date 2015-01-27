/**
 * 
 */
package org.javabuilders.swing.handler.property;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidPropertyValueException;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.SwingJavaBuilderUtils;

/**
 * JMenuItem.accelerator handler
 * @author Jacek Furmankiewicz
 */
public class JMenuItemAcceleratorHandler extends AbstractPropertyHandler {

	private static final JMenuItemAcceleratorHandler singleton = new JMenuItemAcceleratorHandler();
	
	/**
	 * Get singleton instance
	 * @return Singleton
	 */
	public static JMenuItemAcceleratorHandler getInstance() {
		return singleton;
	}
	
	
	/**
	 * Constructor
	 */
	public JMenuItemAcceleratorHandler() {
		super(SwingJavaBuilder.ACCELERATOR);
	}
	
	/**
	 * Internal common method -> called from other places as well (like menu.text)
	 * @param menuItem Menu item
	 * @param text Accelerator text
	 * @throws InvalidPropertyValueException 
	 */
	void setAccelerator(Node node, String key, JMenuItem item, String accelerator) throws InvalidPropertyValueException {
		
		KeyStroke acc = SwingJavaBuilderUtils.getAccelerator(accelerator);
		if (acc != null) {
			item.setAccelerator(acc);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {
		
		String accelerator = node.getStringProperty(key);
		JMenuItem item = (JMenuItem)node.getMainObject();
		setAccelerator(node, key, item, accelerator);

	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return JMenuItem.class;
	}

}
