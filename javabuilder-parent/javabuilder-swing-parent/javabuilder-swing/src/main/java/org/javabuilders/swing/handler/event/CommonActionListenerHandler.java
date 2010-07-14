/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingAction;
import org.javabuilders.swing.SwingAction.IActionHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handles the onAction event (via an ActionListener) on all Swing controls that support it
 * @author Jacek Furmankiewicz
 *
 */
public class CommonActionListenerHandler extends AbstractPropertyHandler implements IPropertyList {

	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(ActionEvent.class);
	private final static CommonActionListenerHandler singleton = new CommonActionListenerHandler();	
	/**
	 * @return Singleton
	 */
	public static CommonActionListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private CommonActionListenerHandler() {
		super(Builder.ON_ACTION);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {

		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);
		
		if (values.size() > 0) {

			if (node.getMainObject() instanceof SwingAction) {
				
				//Our custom Swing Action
				SwingAction.IActionHandler handler = new IActionHandler() {
					public void onAction(ActionEvent e) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node.getMainObject(), values.values(), e);
					}
				};
				((SwingAction)node.getMainObject()).setActionHandler(handler);
				
			} else {
				
				//regular Swing controls
				ActionListener listener = new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node.getMainObject(), values.values(), e);
					}
				};
				
				if (node.getMainObject() instanceof AbstractButton) {
					((AbstractButton)node.getMainObject()).addActionListener(listener);
				} else if (node.getMainObject() instanceof JComboBox) {
					((JComboBox)node.getMainObject()).addActionListener(listener);
				} else if (node.getMainObject() instanceof JTextField) {
					((JTextField)node.getMainObject()).addActionListener(listener);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

}
