/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.JTextField;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;

/**
 * Handles the onAction event (via an ActionListener)
 * @author Jacek Furmankiewicz
 *
 */
public class JTextFieldActionListenerHandler extends AbstractActionListenerHandler implements IPropertyList {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(JTextFieldActionListenerHandler.class.getSimpleName());
	private final static JTextFieldActionListenerHandler singleton = new JTextFieldActionListenerHandler();
	
	/**
	 * @return Singleton
	 */
	public static JTextFieldActionListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private JTextFieldActionListenerHandler() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {

		final JTextField field = (JTextField)node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			field.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), field, values.values(), e);
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<JTextField> getApplicableClass() {
		return JTextField.class;
	}


}
