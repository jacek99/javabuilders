package org.javabuilders.swing.test;

import static org.junit.Assert.*;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.junit.Before;
import org.junit.Test;

/**
 * JTable tests
 */
public class JTableTests {

	private TestModel model = null;
	private TestModel modelInstantiated = new TestModel();
	private TableTestCellEditor cellEditor = null;
	private TableTestCellRenderer cellRenderer = null;
	
	@Before
	public void setUp() {
		model = null;
		modelInstantiated = new TestModel();
		cellEditor = null;
		cellRenderer = null;
		
	}
	
	@Test
	public void testTableModel() {

		BuildResult r = new SwingYamlBuilder("JScrollPane(name=pane):") {{
			___("JTable(name=table):");
			_____("- TestModel(name=model)");
		}}.build(this);
		
		JTable table = (JTable) r.get("table");
		assertNotNull(table);
		
		TestModel buildModel = (TestModel) r.get("model");
		assertNotNull(buildModel);
		assertEquals(buildModel, table.getModel());
		assertEquals(buildModel, model);
		
		TableModel tm = table.getModel();
		assertTrue(tm instanceof TestModel);
		assertEquals(model, tm);
	}
	
	@Test
	public void testTableModelInstantiated_Issue57() {

		BuildResult r = new SwingYamlBuilder("JScrollPane(name=pane):") {{
			___("JTable(name=table):");
			_____("- TestModel(name=modelInstantiated)");
		}}.build(this);
		
		JTable table = (JTable) r.get("table");
		assertNotNull(table);
		
		TestModel buildModel = (TestModel) r.get("modelInstantiated");
		assertNotNull(buildModel);
		assertEquals(buildModel, table.getModel());
		assertEquals(buildModel, modelInstantiated);
		
		TableModel tm = table.getModel();
		assertTrue(tm instanceof TestModel);
		assertEquals(modelInstantiated, tm);
		
	}
	
	@Test
	public void testJComboBoxCellEditor() {
		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TableColumn(name=comboColumn):");
			_____("- JComboBox(name=combobox)");
		}}.build(this);
		
		JComboBox combobox = (JComboBox) r.get("combobox");
		TableColumn comboColumn = (TableColumn) r.get("comboColumn");
		TableCellEditor editor = comboColumn.getCellEditor();
		assertNotNull(editor);
		assertTrue(editor instanceof DefaultCellEditor);
		
		DefaultCellEditor dcEditor = (DefaultCellEditor) editor;
		Component c = dcEditor.getComponent();
		assertEquals(combobox, c);
		
	}
	
	@Test
	public void testJCheckBoxCellEditor() {
		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TableColumn(name=column):");
			_____("- JCheckBox(name=checkbox)");
		}}.build(this);
		
		JCheckBox checkbox = (JCheckBox) r.get("checkbox");
		TableColumn comboColumn = (TableColumn) r.get("column");
		TableCellEditor editor = comboColumn.getCellEditor();
		assertNotNull(editor);
		assertTrue(editor instanceof DefaultCellEditor);
		
		DefaultCellEditor dcEditor = (DefaultCellEditor) editor;
		Component c = dcEditor.getComponent();
		assertEquals(checkbox, c);
		
	}
	
	@Test
	public void testJTextFieldEditor() {
		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TableColumn(name=column):");
			_____("- JTextField(name=textfield)");
		}}.build(this);
		
		JTextField textfield = (JTextField) r.get("textfield");
		TableColumn comboColumn = (TableColumn) r.get("column");
		TableCellEditor editor = comboColumn.getCellEditor();
		assertNotNull(editor);
		assertTrue(editor instanceof DefaultCellEditor);
		
		DefaultCellEditor dcEditor = (DefaultCellEditor) editor;
		Component c = dcEditor.getComponent();
		assertEquals(textfield, c);
	}
	
	@Test
	public void testCellEditor() {
		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TableColumn(name=column):");
			_____("- TableTestCellEditor(name=cellEditor)");
		}}.build(this);
		
		TableColumn comboColumn = (TableColumn) r.get("column");
		TableCellEditor editor = comboColumn.getCellEditor();
		assertNotNull(editor);
		assertTrue(editor instanceof TableTestCellEditor);
		
		TableTestCellEditor cellEditor = (TableTestCellEditor) r.get("cellEditor");
		assertEquals(cellEditor,editor);
		
	}
	
	@Test
	public void testCellRenderer() {
		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TableColumn(name=column):");
			_____("- TableTestCellRenderer(name=cellRenderer)");
		}}.build(this);
		
		TableColumn comboColumn = (TableColumn) r.get("column");
		TableCellRenderer renderer = comboColumn.getCellRenderer();
		assertNotNull(renderer);
		assertTrue(renderer instanceof TableTestCellRenderer);
		
		TableTestCellRenderer cellRenderer = (TableTestCellRenderer) r.get("cellRenderer");
		assertEquals(renderer,cellRenderer);
	}
	
	@Test
	public void testCellHeaderRenderer() {
		BuildResult r = new SwingYamlBuilder("JTable(name=table):") {{
			___("- TableColumn(name=column):");
			_____("- TableTestCellRenderer(name=cellRenderer,forHeader=true)");
		}}.build(this);
		
		TableColumn comboColumn = (TableColumn) r.get("column");
		TableCellRenderer renderer = comboColumn.getHeaderRenderer();
		assertNotNull(renderer);
		assertTrue(renderer instanceof TableTestCellRenderer);
		
		TableTestCellRenderer cellRenderer = (TableTestCellRenderer) r.get("cellRenderer");
		assertEquals(renderer,cellRenderer);
	}
	

	
	public static class TableTestCellRenderer implements TableCellRenderer {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			return new JButton("Test");
		}
	}
	
	public static class TableTestCellEditor implements TableCellEditor {

		private String name;
		
		public void setName(String name) {
			this.name = name; 
		}
		
		public String getName() {
			return this.name;
		}
		
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			// TODO Auto-generated method stub
			return null;
		}

		public void addCellEditorListener(CellEditorListener l) {
			// TODO Auto-generated method stub
			
		}

		public void cancelCellEditing() {
			// TODO Auto-generated method stub
			
		}

		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean isCellEditable(EventObject anEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		public void removeCellEditorListener(CellEditorListener l) {
			// TODO Auto-generated method stub
			
		}

		public boolean shouldSelectCell(EventObject anEvent) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean stopCellEditing() {
			// TODO Auto-generated method stub
			return false;
		}
		
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
