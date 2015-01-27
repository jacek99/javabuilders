package org.javabuilders.swing.plugin.glazedlists.handler;

import java.util.ArrayList;
import java.util.List;

import ca.odell.glazedlists.gui.WritableTableFormat;

public class BaseWritableTableFormat extends BaseTableFormat implements WritableTableFormat<Object> {

	private List<Integer> editableColumns = new ArrayList<Integer>();
	
	/**
	 * Defines which columns are editable
	 * @param columnIndexes
	 */
	public void setEditableColumnIndexes(List<Integer> columnIndexes) {
		editableColumns = columnIndexes;
	}
	
	/* (non-Javadoc)
	 * @see ca.odell.glazedlists.gui.WritableTableFormat#isEditable(java.lang.Object, int)
	 */
	public boolean isEditable(Object baseObject, int column) {
		return editableColumns.contains(column);
	}

	/* (non-Javadoc)
	 * @see ca.odell.glazedlists.gui.WritableTableFormat#setColumnValue(java.lang.Object, java.lang.Object, int)
	 */
	public Object setColumnValue(Object baseObject, Object newValue, int column) {
		//Compiled at runtime by Janino to avoid reflection overhead
		return null;
	}

}
