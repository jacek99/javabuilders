/**
 * 
 */
package org.javabuilders.swing.handler.property;

import javax.swing.JMenuItem;

import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidPropertyValueException;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingJavaBuilderUtils;
import org.javabuilders.swing.SwingJavaBuilderUtils.ActionDefinition;

/**
 * Handles the text property of menu items, including handling of
 * accelerators and mnemonics
 * @author Jacek Furmankiewicz
 *
 */
public class JMenuItemTextHandler extends AbstractPropertyHandler {

	private static final JMenuItemTextHandler singleton = new JMenuItemTextHandler();
	
	/**
	 * Get the singleton
	 * @return Singleton
	 */
	public static JMenuItemTextHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	public JMenuItemTextHandler() {
		super("text");
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws InvalidPropertyValueException {
		String text = String.valueOf(node.getProperties().get(key));
		JMenuItem menuItem = (JMenuItem)node.getMainObject();
		
		ActionDefinition def = SwingJavaBuilderUtils.getActionDefintion(text);
		menuItem.setText(def.getText());
		if (def.getAccelerator() != null) {
			menuItem.setAccelerator(def.getAccelerator());
		}
		if (def.getMnemonic() != null) {
			menuItem.setMnemonic(def.getMnemonic());
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return JMenuItem.class;
	}

}
