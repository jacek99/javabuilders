package org.javabuilders.swing.handler.property;

import javax.swing.AbstractButton;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingJavaBuilderUtils;
import org.javabuilders.swing.SwingJavaBuilderUtils.ActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Text handler for all buttons (takes care of mnemonics and acelerators as well)
 * @author Jacek Furmankiewicz
 *
 */
public class AbstractButtonTextHandler extends AbstractPropertyHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractButtonTextHandler.class);
	
	public static final String TEXT = "text";
	
	public AbstractButtonTextHandler() {
		super(TEXT);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node, String key) throws BuildException {
		String text = String.valueOf(node.getProperties().get(key));
		AbstractButton button = (AbstractButton)node.getMainObject();
		
		ActionDefinition def = SwingJavaBuilderUtils.getActionDefintion(text);
		button.setText(def.getText());
		if (def.getMnemonic() != null) {
			button.setMnemonic(def.getMnemonic());
		}
		//accelerators can be called on JMenuItems only, but on JMenu (throws Swing exception)
		if (def.getAccelerator() != null) {
			if (button instanceof JMenuItem && !(button instanceof JMenu)) {
				JMenuItem menuItem = (JMenuItem) button;
				menuItem.setAccelerator(def.getAccelerator());
			} else {
				LOG.warn("Ignored accelerator: can only be set on JMenuItem(s): " + text);
			}
		}
	}

}
