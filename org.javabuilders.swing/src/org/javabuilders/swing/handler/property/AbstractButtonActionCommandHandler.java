/**
 * 
 */
package org.javabuilders.swing.handler.property;

import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.AbstractButton;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Handler for the "actionCommand" property. Automatically wires the called to receive the action command
 * if it is of type ActionListener
 * @author Jacek Furmankiewicz
 */
public class AbstractButtonActionCommandHandler extends AbstractPropertyHandler {

	public final static String ACTION_COMMAND = "actionCommand";
	
	private final static Logger logger = Logger.getLogger(AbstractButtonActionCommandHandler.class.getSimpleName());
	private final static AbstractButtonActionCommandHandler singleton = new AbstractButtonActionCommandHandler();
	
	/**
	 * @return Singleton
	 */
	public static AbstractButtonActionCommandHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private AbstractButtonActionCommandHandler() {
		super(ACTION_COMMAND);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {
		AbstractButton button = (AbstractButton)node.getMainObject();
		String actionCommand = String.valueOf(node.getProperties().get(key));
		button.setActionCommand(actionCommand);
		
		//automatically wire the calling object to receive the action command
		if (result.getCaller() != null && result.getCaller() instanceof ActionListener) {
			button.addActionListener((ActionListener)result.getCaller());
			if (logger.isLoggable(Level.FINE)) {
				logger.fine(String.format("Added calling ActionListener for actionCommand '%s' on button '%s'", 
						actionCommand,button.getName()));
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return AbstractButton.class;
	}

}
