/**
 * 
 */
package org.javabuilders.swing.plugin.glazedlists.handler;

import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventListModel;

/**
 * GlazedLists EventListModel support
 * @author Jacek Furmankiewicz
 *
 */
public class EventListModelTypeHandler extends AbstractTypeHandler {

	public static final String SOURCE="source";
	
	public EventListModelTypeHandler() {
		super(SOURCE);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition) throws BuildException {
		EventList list = GlazedListsUtils.getSource(process.getCaller(), typeDefinition).get0(); 
		EventListModel instance = new EventListModel<Object>(list);
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {
		Node node = new Node(parent,key,typeDefinition,instance);
		return node;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	@SuppressWarnings("unchecked")
	public Class<EventListModel> getApplicableClass() {
		return EventListModel.class;
	}

}
