package org.javabuilders.swing.handler.property;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingAction;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.SwingJavaBuilderUtils;
import org.javabuilders.swing.SwingJavaBuilderUtils.ActionDefinition;

/**
 * Handler for SwingAction.text
 * @author Jacek Furmankiewicz
 *
 */
public class SwingActionTextHandler extends AbstractPropertyHandler {

	private static final SwingActionTextHandler singleton = new SwingActionTextHandler();
	
	/**
	 * @return Singleton
	 */
	public static SwingActionTextHandler getInstance() {return singleton;}
	
	private SwingActionTextHandler() {
		super(SwingJavaBuilder.TEXT);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node,
			String key) throws BuildException {

		String text = node.getStringProperty(SwingJavaBuilder.TEXT);

		SwingAction action = (SwingAction) node.getMainObject();
		
		ActionDefinition def = SwingJavaBuilderUtils.getActionDefintion(text);
		action.setText(def.getText());
		if (def.getAccelerator() != null) {
			action.setAccelerator(def.getAccelerator());
		}
		if (def.getMnemonic() != null) {
			action.setMnemonic(def.getMnemonic());
		}

	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<SwingAction> getApplicableClass() {
		return SwingAction.class;
	}

}
