/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.event.ActionEvent;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.Node;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.swing.SwingAction;
import org.javabuilders.swing.SwingAction.IActionHandler;

/**
 * Handler for SwingAction.onAction
 * @author JAcek Furmankiewicz
 *
 */
public class SwingActionOnActionHandler extends AbstractActionListenerHandler {

	private static final SwingActionOnActionHandler singleton = new SwingActionOnActionHandler();
	
	/**
	 * @return Singleton
	 */
	public static SwingActionOnActionHandler getInstance() {return singleton;}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config,final  BuildProcess process, Node node,
			String key) throws BuildException {
		
		final SwingAction action = (SwingAction) node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			SwingAction.IActionHandler handler = new IActionHandler() {
				public void onAction(ActionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), action, values.values(), e);
				}
			};
			action.setActionHandler(handler);
		}

	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<SwingAction> getApplicableClass() {
		return SwingAction.class;
	}

}
