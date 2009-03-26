package org.javabuilders.swing.handler.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.Node;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;

public class JComboBoxActionListenerHandler extends AbstractActionListenerHandler {

	private final static JComboBoxActionListenerHandler singleton = new JComboBoxActionListenerHandler();
	
	/**
	 * @return Singleton
	 */
	public static JComboBoxActionListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private JComboBoxActionListenerHandler() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {

		final JComboBox target = (JComboBox)node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			target.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), target, values.values(), e);
				}
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return JComboBox.class;
	}
}
