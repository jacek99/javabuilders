package org.javabuilders.swing.handler.type;

import java.util.Map;
import java.util.Set;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

public class JComboBoxFinishProcessor  implements ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		Set<ComboBoxModel> models = current.getChildObjects(ComboBoxModel.class);
		JComboBox list = (JComboBox) current.getMainObject();
		for(ComboBoxModel model : models) {
			list.setModel(model);
		}
	}

}