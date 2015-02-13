/**
 * 
 */
package org.javabuilders.fx.handler.event;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.TextField;
import org.javabuilders.*;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.util.BuilderUtils;

import java.util.List;

/**
 * Handles the onAction event (via an ActionListener) on all FX controls that support it
 * @author Jacek Furmankiewicz
 *
 */
public class CommonActionListenerHandler extends AbstractPropertyHandler implements IPropertyList {

    private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(ActionEvent.class);
	/**
	 * Constructor
	 */
	public CommonActionListenerHandler() {
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

           if (node.getMainObject() instanceof ButtonBase) {
               ((ButtonBase)node.getMainObject()).setOnAction((ActionEvent e) -> {
                   BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node.getMainObject(), values.values(), e);
               });
           } else if (node.getMainObject() instanceof TextField) {
               ((TextField)node.getMainObject()).setOnAction((ActionEvent e) -> {
                   BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node.getMainObject(), values.values(), e);
               });
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
