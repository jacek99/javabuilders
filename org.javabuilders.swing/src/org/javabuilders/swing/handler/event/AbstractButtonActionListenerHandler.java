/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.AbstractButton;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;

/**
 * Handles the JTextField onAction event (via an ActionListener)
 * @author Jacek Furmankiewicz
 *
 */
public class AbstractButtonActionListenerHandler extends AbstractActionListenerHandler implements IPropertyList {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(AbstractButtonActionListenerHandler.class.getSimpleName());
	private final static AbstractButtonActionListenerHandler singleton = new AbstractButtonActionListenerHandler();
	
	/**
	 * @return Singleton
	 */
	public static AbstractButtonActionListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private AbstractButtonActionListenerHandler() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {

		final AbstractButton button = (AbstractButton)node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), button, values.values(), e);
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return AbstractButton.class;
	}



}
