package org.javabuilders.swing.handler.type.model;

import java.util.Map;

import javax.swing.DefaultComboBoxModel;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.handler.ITypeHandlerAfterCreationProcessor;

/**
 * Creates a default combo box model from a defined YAML list
 * @author Jacek Furmankiewicz
 */
public class DefaultComboBoxModelHandler implements ITypeHandlerAfterCreationProcessor, ITypeChildrenHandler {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerAfterCreationProcessor#afterCreation(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void afterCreation(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		@SuppressWarnings("unused")
		DefaultComboBoxModel model = (DefaultComboBoxModel) current.getMainObject();
		
		if (current.getContentNode() != null) {
			for(@SuppressWarnings("unused")
			String value : current.getContentNode().getChildValues().keySet()) {
				//TODO
			}
		}
	}

}
