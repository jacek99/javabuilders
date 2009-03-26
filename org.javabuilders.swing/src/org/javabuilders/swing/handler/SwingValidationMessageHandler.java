/**
 * 
 */
package org.javabuilders.swing.handler;

import java.awt.Component;

import javax.accessibility.AccessibleContext;
import javax.swing.JOptionPane;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;
import org.javabuilders.swing.SwingJavaBuilderUtils;

/**
 * Swing validation message handler
 * @author Jacek Furmankiewicz
 *
 */
public class SwingValidationMessageHandler implements IValidationMessageHandler {

	private static final SwingValidationMessageHandler singleton = new SwingValidationMessageHandler();
	
	/**
	 * @return Singleton
	 */
	public static SwingValidationMessageHandler getInstance() {return singleton;}
	
	private SwingValidationMessageHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IValidationMessageHandler#handleValidationMessages(org.javabuilders.handler.validation.ValidationMessageList, org.javabuilders.BuildResult)
	 */
	public void handleValidationMessages(ValidationMessageList list,
			BuildResult result) {
		
		if (list.size() > 0) {
			
			Component firstObject = null;
			
			StringBuilder builder = new StringBuilder();
			for(ValidationMessage msg : list) {
				if (builder.length() > 0) {
					builder.append("\n");
				}
				builder.append(msg.getMessage());
				
				if (firstObject == null &&  msg.getProperty() != null) {
					Object focusable = result.get(msg.getProperty().getName());
					if (focusable instanceof Component) {
						firstObject = (Component)focusable;
					}
				}
			}
			
			Component parent = null;
			if (result.getRoot() instanceof Component) {
				parent = SwingJavaBuilderUtils.getTopLevelParent(result.getRoot());
			}
			
			String title = (list.size() == 1) ? DEFAULT_VALIDATION_ERROR_TITLE : DEFAULT_VALIDATION_ERRORS_TITLE;
			
			JOptionPane.showMessageDialog(parent,builder.toString(), 
					Builder.getResourceBundle().getString(title), JOptionPane.ERROR_MESSAGE);
			
			//set the focus back to the first field in error
			if (firstObject != null) {
				firstObject.requestFocus();
			}
		} 

	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IValidationMessageHandler#getNamedObjectLabel(java.lang.Object)
	 */
	public String getNamedObjectLabel(Object namedObject) {
		//attempt to find the label from the accessibility info
		String label = null;
		
		
		
		if (namedObject instanceof Component) {
			Component c = (Component)namedObject;
			
			AccessibleContext ac = c.getAccessibleContext();
			if (ac != null) {
				label = ac.getAccessibleDescription();
				//remove the ":" that labels often have
				if (label != null && label.endsWith(":")) {
					label = label.substring(0,label.length() - 1);
				}
			}
		}
		
		return label;
	}

}
