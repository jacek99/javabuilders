/**
 * 
 */
package org.javabuilders.swing.handler.property;

import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Handler for the JTextField "actionCommand" property. Automatically wires the called to receive the action command
 * if it is of type ActionListener
 * @author Jacek Furmankiewicz
 */
public class JTextFieldActionCommandHandler extends AbstractPropertyHandler {

	private final static Logger logger = Logger.getLogger(JTextFieldActionCommandHandler.class.getSimpleName());
	private final static JTextFieldActionCommandHandler singleton = new JTextFieldActionCommandHandler();
	
	/**
	 * @return Singleton
	 */
	public static JTextFieldActionCommandHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private JTextFieldActionCommandHandler() {
		super(AbstractButtonActionCommandHandler.ACTION_COMMAND);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {
		JTextField field = (JTextField)node.getMainObject();
		String actionCommand = String.valueOf(node.getProperties().get(key));
		field.setActionCommand(actionCommand);
		
		//automatically wire the calling object to receive the action command
		if (result.getCaller() != null && result.getCaller() instanceof ActionListener) {
			field.addActionListener((ActionListener)result.getCaller());
			if (logger.isLoggable(Level.FINE)) {
				logger.fine(String.format("Added calling ActionListener for actionCommand '%s' on button '%s'", 
						actionCommand,field.getName()));
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<JTextField> getApplicableClass() {
		return JTextField.class;
	}

}
