/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.util.Map;
import java.util.Set;

import javax.swing.JList;
import javax.swing.ListModel;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * JList finish processor
 * @author Jacek Furmankiewicz
 *
 */
public class JListFinishProcessor implements ITypeHandlerFinishProcessor {

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		Set<ListModel> models = current.getChildObjects(ListModel.class);
		JList list = (JList) current.getMainObject();
		for(ListModel model : models) {
			list.setModel(model);
		}
	}

}
