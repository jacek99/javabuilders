/**
 * 
 */
package org.javabuilders.swing.handler.type.glazedlists;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.PropertyUtils;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.swing.EventTableModel;

/**
 * GlazedLists EventTableModel support
 * @author Jacek Furmankiewicz
 *
 */
public class EventTableModelTypeHandler extends AbstractTypeHandler {

	public static final String SOURCE="source";
	
	public EventTableModelTypeHandler() {
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
			throw new BuildException("EventTableModel.source property must be specified: {0}",typeDefinition);
		} else {
			Field field = BuilderUtils.getField(process.getCaller(),source, EventList.class);
			if (field == null) {
				throw new BuildException("EventTableModel.source property does not point to a valid instance of GlazedLists EventList: {0}",typeDefinition);
			} else {
				try {
					EventList list = (EventList) field.get(process.getCaller());
					
					Class<?> type = BuilderUtils.getGenericsTypeFromCollectionField(field);
					if (type == null) {
						throw new BuildException("Unable to use generics to find type of object stored in source: {0}", source);
					}
					TableFormat f = createTableFormat(parent, type);
					EventTableModel instance = new EventTableModel<Object>(list, f);
					return useExistingInstance(config, process, parent, key, typeDefinition, instance);
				} catch (BuildException ex) {
					throw ex;
				} catch (Exception e) {
					throw new BuildException(e,"Unable to get instance of EventTableModel.source: {0}",typeDefinition);
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
	public Class<EventTableModel> getApplicableClass() {
		return EventTableModel.class;
	}
	
	//creates the TableFormat class
	@SuppressWarnings("unchecked")
	private TableFormat<?> createTableFormat(Node parent, Class<?> type) {
		
		final Set<String> props = PropertyUtils.getPropertyNames(type);
		List<String> sortedProps = new LinkedList<String>(props);
		Collections.sort(sortedProps);
		final String[] columns = sortedProps.toArray(new String[props.size()]);
		
		//TODO: temp version, quick and dirty
		TableFormat f = new TableFormat<Object>() {
			public int getColumnCount() {
				return columns.length;
			}
			public String getColumnName(int arg0) {
				return columns[arg0];
			}
			public Object getColumnValue(Object arg0,int arg1){
				String column = columns[arg1];
				return PropertyUtils.getProperty(arg0, column);
			}
		};
		
		return f;
	}

}
