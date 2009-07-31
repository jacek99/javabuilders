package org.javabuilders.swing.handler.type.glazedlists;

import java.util.LinkedHashMap;
import java.util.Map;

import ca.odell.glazedlists.gui.TableFormat;

public class BaseTableFormat implements TableFormat<Object> {

	private Map<String,String> columns;
	private String[] names;
	
	/**
	 * Map used for storing column/name combinations
	 * @param columns
	 */
	public void setColumns(LinkedHashMap<String,String> columns) {
		this.columns = columns;
		names  = columns.values().toArray(new String[columns.keySet().size()]);
	}
	
	/* (non-Javadoc)
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnCount()
	 */
	public int getColumnCount() {
		return columns.size();
	}

	/* (non-Javadoc)
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnName(int)
	 */
	public String getColumnName(int i) {
		return names[i];
	}

	/* (non-Javadoc)
	 * @see ca.odell.glazedlists.gui.TableFormat#getColumnValue(java.lang.Object, int)
	 */
	public Object getColumnValue(Object arg, int index) {
		//Compiled by Janino at runtime to avoid reflection overhead
		return null;
	}

}
