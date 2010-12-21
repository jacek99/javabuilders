/**
 * 
 */
package org.javabuilders.swing.plugin.glazedlists.handler;

import static org.javabuilders.swing.handler.type.TableColumnTypeHandler.*;
import static org.javabuilders.util.Preconditions.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.swing.handler.type.TableColumnTypeHandler;
import org.javabuilders.swing.plugin.glazedlists.compiler.CompilerUtils;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.JBStringUtils;
import org.javabuilders.util.PropertyUtils;

import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.gui.TableFormat;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

/**
 * GlazedLists EventTableModel support
 * 
 * @author Jacek Furmankiewicz
 * 
 */
public class EventTableModelTypeHandler extends AbstractTypeHandler implements ITypeChildrenHandler {

	public static final String SOURCE = "source";
	public static final String HEADER_VALUE = TableColumnTypeHandler.HEADER_VALUE;
	public static final String SORT = "sort";
	public static final String SORT_SINGLE = "single";
	public static final String SORT_MULTI = "multi";
	public static final String SORT_BY = "sortBy";
	public static final String COLUMNS = "columns";
	
	private static final String TEXT_FILTERATOR = "TextFilterator";
	private static final Pattern TEXT_FILTERATOR_REGEX = Pattern.compile("(?:[a-zA-Z]+\\()([a-zA-Z0-9]+)=\\[(.*)\\]");

	public EventTableModelTypeHandler() {
		super(SOURCE, SORT,EDITABLE, COLUMNS, SORT_BY);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders
	 * .BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node,
	 * java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			final Map<String, Object> typeDefinition) throws BuildException {

		String source = (String) typeDefinition.get(SOURCE);
		checkNotNull(source, "EventTableModel.source property must be specified: {0}", typeDefinition);

		List<Map<String, Object>> cols = parent.getParent().getContentData(TableColumn.class);
		JTable table = (JTable) parent.getParentObject(JTable.class);

		Field field = BuilderUtils.getField(process.getCaller(), source, EventList.class);
		checkNotNull(field,"EventTableModel.source property does not point to a valid instance of GlazedLists EventList: {0}", typeDefinition);
		
		try {
			EventList list = GlazedListsUtils.getSource(process.getCaller(), typeDefinition).get0();
			Class<?> type = BuilderUtils.getGenericsTypeFromCollectionField(field);
			if (type == null) {
				throw new BuildException("Unable to use generics to find type of object stored in source: {0}", source);
			}
			LinkedHashMap<String, String> columnNames = getColumnNamesAndHeaders(process, typeDefinition, cols, type);
			TableFormat tableFormat = createTableFormat(parent, type, typeDefinition, cols, columnNames);
			EventTableModel instance =  setupModel(process, typeDefinition, table, list, cols, type,tableFormat);
			return useExistingInstance(config, process, parent, key, typeDefinition, instance);
		} catch (BuildException ex) {
			throw ex;
		} catch (Exception e) {
			throw new BuildException(e, "Unable to create instance of EventTableModel: {0}.\n{1}", typeDefinition,e.getMessage());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders
	 * .BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node,
	 * java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {
		Node node = new Node(parent, key, typeDefinition, instance);
		return node;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	@SuppressWarnings("unchecked")
	public Class<EventTableModel> getApplicableClass() {
		return EventTableModel.class;
	}

	// creates the TableFormat class
	private TableFormat<?> createTableFormat(Node parent, Class<?> type, Map<String,Object> typeDefinition, 
			List<Map<String, Object>> cols, final LinkedHashMap<String, String> columnNames) {

		final String[] names = columnNames.keySet().toArray(new String[columnNames.keySet().size()]);
		Integer[] editable = getEditableColumnIndexes(typeDefinition, cols);
		
		Class<?> formatClass = (editable.length == 0) ? BaseTableFormat.class : BaseWritableTableFormat.class;

		StringBuilder bld = new StringBuilder();
		
		//create the getColumnValue method body
		String className = CompilerUtils.generateClassName(formatClass);
		
		//bld.append(CompilerUtils.generateClassSignature(className, formatClass));
		bld.append("public Object getColumnValue(Object instance, int index) {\n");
		bld.append(type.getName()).append(" object = (").append(type.getName()).append(")instance;\n");
		bld.append("   switch(index) {\n");
		for(int i = 0; i < names.length; i++) {
			bld.append("   case ").append(i).append(": return object.").append(PropertyUtils.getGetterName(names[i])).append("();\n");
		}
		bld.append("   default: return null;"); //should never be reached
		bld.append("   }\n");
		bld.append("}");

		if (formatClass.equals(BaseWritableTableFormat.class)) {
			//writable format - need to define setColumnValue method as well
			bld.append("public Object setColumnValue(").append(type.getName()).append(" baseObject, Object newValue, int column) {");
			//Method setter = type.getMethod(PropertyUtils, parameterTypes)
			bld.append("   switch(column) {");
			for(Integer column : editable) {
				String getterName = PropertyUtils.getGetterName(names[column]);
				Method getter;
				try {
					getter = type.getMethod(getterName);
				} catch (Exception e) {
					throw new BuildException("Unable to get getter method {0} for {1}: {2}", getterName,type,typeDefinition);
				} 
				Class<?> columnType = getter.getReturnType();
				bld.append("   case ").append(column).append(": baseObject.")
					.append(PropertyUtils.getSetterName(names[column])).append("((").append(columnType.getName())
					.append(")newValue);");
			}
			bld.append("   }");
			bld.append("}\n}");
		}
		
		try {
			BaseTableFormat f = (BaseTableFormat) CompilerUtils.compile(className, bld.toString(),BaseTableFormat.class).newInstance();
			f.setColumns(columnNames);
			if (f instanceof BaseWritableTableFormat) {
				BaseWritableTableFormat wf = (BaseWritableTableFormat) f;
				wf.setEditableColumnIndexes(Arrays.asList(editable));
			}
			return f;
		} catch (Exception e) {
			throw new BuildException("Failed to compile TableFormat for GlazedLists filtering: {0}\n{1}",e.getMessage(),bld.toString());
		}
	}

	// looks at raw data to figure out which column it goes to
	@SuppressWarnings("unchecked")
	private LinkedHashMap<String, String> getColumnNamesAndHeaders(BuildProcess process, Map<String,Object> typeDefinition, 
			List<Map<String, Object>> cols, Class<?> type) {
		LinkedHashMap<String, String> columns = new LinkedHashMap<String, String>();
		final Set<String> props = PropertyUtils.getPropertyNames(type);

		//take initial list from what's defined in the columns=[] collection
		List<String> modelColumns = (List<String>) typeDefinition.get(COLUMNS);
		if (modelColumns != null) {
			for(String modelColumn : modelColumns) {
				columns.put(modelColumn, JBStringUtils.getDisplayLabel(process, type, modelColumn));
			}
		}
		
		//add/override to it via whatever TableColumns have been defined
		if (cols.size() > 0) {
			for (Map<String, Object> map : cols) {
				String name = getColumnName(map);
				if (props.contains(name)) {
					String headerValue = (String) map.get(HEADER_VALUE);
					if (headerValue == null) {
						headerValue = JBStringUtils.getDisplayLabel(process, type, name);
					}
					// flag internally the column to map it to a particular
					// index of the model
					map.put(TableColumnTypeHandler.INTERNAL_MODEL_INDEX, columns.size());
					columns.put(name, headerValue);
				} else {
					throw new BuildException("Unable to map column ''{0}'' to any property of type {1}", name, type);
				}
			}
		} 
		
		//if no columns have been defined at all, just default to displaying all of them
		if (columns.size() == 0) {
			// columns not defined explicitly - show them all, sorted by name
			List<String> orderedProps = new ArrayList<String>(props);
			Collections.sort(orderedProps);
			for (String name : orderedProps) {
				columns.put(name, JBStringUtils.getDisplayLabel(process, type, name));
			}
		}
		return columns;
	}

	// common logic to find the property name within the column definition
	private String getColumnName(Map<String, Object> map) {
		String name = null;
		if (map.containsKey(SOURCE)) {
			name = String.valueOf(map.get(SOURCE));
		} else if (map.containsKey(Builder.NAME)) {
			name = String.valueOf(map.get(Builder.NAME));
		} else {
			throw new BuildException(
					"TableColumn data does not contain 'source' or 'name' property. Unable to map it to the model: {0}", map);
		}
		return name;
	}

	// figures out if the actual source is the raw data, SortedList or a FilterList wrapper
	@SuppressWarnings({ "unchecked", "unused" })
	private EventTableModel setupModel(BuildProcess process, Map<String, Object> typeDefinition, 
			JTable table, EventList source, List<Map<String, Object>> cols, Class<?> type, TableFormat format) {
		EventList actualSource = source;
		SortedList sortedList = null;
		Object sortedChooser = null;
		EventList filterList = null;
		
		String sort = (String) typeDefinition.get(SORT);
		List<String> sortedColumns = (List<String>) typeDefinition.get(SORT_BY);
		// SORTING
		if (SORT_SINGLE.equals(sort) || SORT_MULTI.equals(sort)) {
			sortedList = new SortedList(actualSource);
			//handle sorted columns, if specified
			if (sortedColumns != null && sortedColumns.size() > 0) {
				Comparator c = CompilerUtils.newComparator(type, sortedColumns);
				sortedList.setComparator(c);
			}
			actualSource = sortedList;
			sortedChooser = (SORT_SINGLE.equals(sort)) ? TableComparatorChooser.SINGLE_COLUMN : TableComparatorChooser.MULTIPLE_COLUMN_MOUSE;
		} else if (sort != null) {
			throw new BuildException("Unknown value of EventTableModel.sort: {0}\n{1}", sort, typeDefinition);
		}
		//FILTERING
		Map<JComponent,List<String>> filterInfo = getModelFilters(process, typeDefinition);
		if (filterInfo.size() > 0) {
			for(JComponent filterField : filterInfo.keySet()) {
				List<String> filterColumns = filterInfo.get(filterField);
				if (filterField instanceof JTextComponent) {
					JTextComponent c = (JTextComponent) filterField;
					TextFilterator filterator = createTextFilterator(type, filterColumns);
					MatcherEditor textMatcherEditor = new TextComponentMatcherEditor(c, filterator);
					filterList = new FilterList<Object>(actualSource, textMatcherEditor);
					actualSource = filterList;
				} else {
					//throw new BuildException("EventTableModel.filterField does not point to a JTextComponent: {0}",
						//	typeDefinition);
				}
			}
		}
		
		//put it all together, in the right order for GlazedLists to work
		EventTableModel model = new EventTableModel(actualSource, format);
		table.setModel(model);
		if (sortedList != null) {
			TableComparatorChooser tableSorter = TableComparatorChooser.install(table, (SortedList)sortedList, sortedChooser);
		}
		return model;
	}
	
	//uses Janino to compile an im-memory text filterator
	//we don't want to use Reflection for this due to performance overhead on filtering potentially thousands of rows
	@SuppressWarnings("unchecked")
	private TextFilterator<?> createTextFilterator(Class<?> type, List<String> columns) {
		StringBuilder bld = new StringBuilder();
		
		String fullTypeName = type.getName();
		Map<String,String> getters = new HashMap<String, String>();
		Map<String,Class<?>> types = new HashMap<String, Class<?>>();
		for(String column : columns) {
			String getter = "get" + column.substring(0,1).toUpperCase() + column.substring(1); 
			getters.put(column, getter);
			try {
				Class<?> returnType = type.getMethod(getter).getReturnType();
				types.put(column, returnType);
			} catch (Exception e) {
				throw new BuildException("Unable to get setter return value: {0}",e.getMessage());
			} 
		}
		
		String fullName = CompilerUtils.generateClassName(TextFilterator.class);
		
		bld.append("public void getFilterStrings(java.util.List baseList, Object target) {\n");
		bld.append("    ").append(fullTypeName).append(" row = (").append(fullTypeName).append(")target;\n");
		for(String column : columns) {
			if (types.get(column).equals(String.class)) {
				bld.append("    baseList.add(row.").append(getters.get(column)).append("());\n");
			} else {
				//non-String type - wrap it
				bld.append("    baseList.add(String.valueOf(row.").append(getters.get(column)).append("());\n");
			}
		}
		bld.append("}");
		
		try {
			TextFilterator f = (TextFilterator) CompilerUtils.compile(fullName, bld.toString(),Object.class,TextFilterator.class).newInstance();
			return f;
		} catch (Exception e) {
			throw new BuildException("Failed to compile TextFilterator for GlazedLists filtering: {0}\n{1}",e.getMessage(),bld.toString());
		} 
	}
	
	//find out which columns are editable
	private Integer[] getEditableColumnIndexes(Map<String, Object> typeDefinition, List<Map<String, Object>> cols) {
		Set<Integer> indexes = new LinkedHashSet<Integer>();
		
		Boolean modelEditable = (Boolean) BuilderUtils.getValue(typeDefinition, EDITABLE, Boolean.FALSE);
		
		for(int i = 0; i < cols.size(); i++) {
			Map<String,Object> col = cols.get(i);
			Boolean colEditable = (Boolean) BuilderUtils.getValue(col, EDITABLE, modelEditable);
			if (colEditable) {
				indexes.add(i);
			}
		}
		
		return indexes.toArray(new Integer[indexes.size()]);
	}
	
	//scans the Model Filter element looking for filter info
	@SuppressWarnings("unchecked")
	private Map<JComponent,List<String>> getModelFilters(BuildProcess process, Map<String, Object> typeDefinition) {
		Map<JComponent,List<String>> map = new LinkedHashMap<JComponent, List<String>>();
		ArrayList<String> content = (ArrayList<String>) typeDefinition.get(Builder.CONTENT);
		if (content != null && content.size() > 0) {
			for(String filter : content) {
				if (filter.startsWith(TEXT_FILTERATOR)) {
					Matcher m = TEXT_FILTERATOR_REGEX.matcher(filter);
					if (m.find()) {
						String field = m.group(1);
						String[] columns = m.group(2).split(",");
						JComponent c = (JComponent) process.getBuildResult().get(field);
						if (c != null) {
							List<String> cols = Arrays.asList(columns);
							if (cols.size() > 0) {
								map.put(c,cols);
							} else {
								throw new BuildException("TextFilterator field {0} needs to have at least 1 column", field);
							}
							
						} else {
							throw new BuildException("TextFilterator field {0} cannot be found", field);
						}
					}
				}
			}
		}
		
		return map;
	}
	

}
