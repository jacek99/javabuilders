package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.junit.Test;

/**
 * JTable tests
 */
public class JTableTests {

	private TestModel model;
	
	@Test
	public void testTableModel() {

		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TestModel(name=model)");
		}}.build(this);
		
		JTable table = (JTable) r.get("table");
		assertNotNull(table);
		
		TableModel tm = table.getModel();
		assertTrue(tm instanceof TestModel);
		assertEquals(model, tm);
		
	}
	
	//used for testing
	public static class TestModel implements TableModel {
		
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
		}

		public Class<?> getColumnClass(int columnIndex) {
			return String.class;
		}

		public int getColumnCount() {
			return 1;
		}

		public String getColumnName(int columnIndex) {
			return "test";
		}

		public int getRowCount() {
			return 0;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			return null;
		}

		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return false;
		}

		public void removeTableModelListener(TableModelListener l) {
		}

		public void setValueAt(Object value, int rowIndex, int columnIndex) {
		}

		
	}
}
