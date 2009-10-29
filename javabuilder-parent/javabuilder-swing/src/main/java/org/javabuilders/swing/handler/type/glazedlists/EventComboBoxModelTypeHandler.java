package org.javabuilders.swing.handler.type.glazedlists;

import java.lang.reflect.Field;
import java.util.Map;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.util.BuilderUtils;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventComboBoxModel;

/**
 * Glazed lists EventComboBoxModel handler
 * @author jacek
 *
 */
public class EventComboBoxModelTypeHandler extends AbstractTypeHandler {

	public static final String SOURCE="source";
	
	public EventComboBoxModelTypeHandler() {
		super(SOURCE);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition) throws BuildException {

		String source = (String) typeDefinition.get(SOURCE);
		if (source == null) {
			throw new BuildException("EventComboBoxModel.source property must be specified: {0}",typeDefinition);
		} else {
			Field field = BuilderUtils.getField(process.getCaller(),source, EventList.class);
			if (field == null) {
				throw new BuildException("EventComboBoxModel.source property does not point to a valid instance of GlazedLists EventList: {0}",typeDefinition);
			} else {
				try {
					EventList list = (EventList) field.get(process.getCaller());
					EventComboBoxModel instance = new EventComboBoxModel<Object>(list);
					return useExistingInstance(config, process, parent, key, typeDefinition, instance);
				} catch (Exception e) {
					throw new BuildException(e,"Unable to get instance of EventComboBoxModel.source: {0}",typeDefinition);
				}
			}
		}
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
	public Class<EventComboBoxModel> getApplicableClass() {
		return EventComboBoxModel.class;
	}

}

