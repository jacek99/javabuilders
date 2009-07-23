/**
 * 
 */
package org.javabuilders.swing.handler.type.glazedlists;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.table.TableColumn;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.JBStringUtils;
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
		
		List<Map<String,Object>> cols = parent.getParent().getContentData(TableColumn.class);
		
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
					List<String> columns = getColumnNames(cols,type);
					TableFormat f = createTableFormat(parent, type,columns);
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
	private TableFormat<?> createTableFormat(Node parent, Class<?> type, final List<String> columns) {
		
		TableFormat f = new TableFormat<Object>() {
			public int getColumnCount() {
				return columns.size();
			}
			public String getColumnName(int index) {
				//TODO: add internationalization logic
				return JBStringUtils.getDisplayName(columns.get(index));
			}
			
			public Object getColumnValue(Object instance,int index){
				String column = columns.get(index);
				return PropertyUtils.getProperty(instance, column);
			}
		};
		
		return f;
	}
	
	//looks at raw data to figure out which column it goes to
	private List<String> getColumnNames(List<Map<String,Object>> cols, Class<?> type) {
		List<String> columns = new LinkedList<String>();
		final Set<String> props = PropertyUtils.getPropertyNames(type);
		
		if (cols.size() > 0) {
			for(Map<String,Object> map : cols) {
				String name = null;
				if (map.containsKey(SOURCE)) {
					name = String.valueOf(map.get(SOURCE));
				} else if (map.containsKey(Builder.NAME)) {
					name = String.valueOf(map.get(Builder.NAME));
				} else {
					throw new BuildException("TableColumn data does not contain 'source' or 'name' property. Unable to map it to the model: {0}",
							map);
				}
				if (props.contains(name)) {
					columns.add(name);
				} else {
					throw new BuildException("Unable to map column '{0}' to any property of type {1}",name,type);
				}
			}
		} else {
			//columns not defined explicitly - show them all, sorted by name
			List<String> orderedProps = new ArrayList<String>(props); 
			Collections.sort(orderedProps);
			for(String name : orderedProps) {
				columns.add(name);
			}
		}
		return columns;
	}

}
