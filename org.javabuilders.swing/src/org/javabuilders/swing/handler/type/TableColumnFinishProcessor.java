package org.javabuilders.swing.handler.type;

import java.util.Map;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * TableColumn finish processor
 * @author Jacek Furmankiewicz
 *
 */
public class TableColumnFinishProcessor implements ITypeHandlerFinishProcessor {

	/**
	 * Defines custom property name that controls if a cell renderer should be for the cell (default) or for the header
	 */
	public static final String FOR_HEADER = "forHeader";

	
	private static final TableColumnFinishProcessor singleton = new TableColumnFinishProcessor();
	
	/**
	 * @return Singleton
	 */
	public static TableColumnFinishProcessor getInstance() {
		return singleton;
	}
	
	private TableColumnFinishProcessor() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
		TableColumn col = (TableColumn) current.getMainObject();
		
		//Cell renderers
		Set<Node> renderers = current.getContentNodes(TableCellRenderer.class);
		for(Node renderer : renderers) {
			TableCellRenderer r = (TableCellRenderer) renderer.getMainObject();
			if ("true".equals(renderer.getProperty(FOR_HEADER))) {
				col.setHeaderRenderer(r);
			} else {
				col.setCellRenderer(r);
			}
		}
		
		//cell editors
		Set<Node> editors = current.getContentNodes(TableCellEditor.class, JComboBox.class, JCheckBox.class, JTextField.class);
		for(Node editor : editors) {
			if (editor.getMainObject() instanceof TableCellEditor) {
				TableCellEditor e = (TableCellEditor) editor.getMainObject();
				col.setCellEditor(e);
			} else if (editor.getMainObject() instanceof JComboBox) {
				JComboBox e = (JComboBox) editor.getMainObject();
				col.setCellEditor(new DefaultCellEditor(e));
			} else if (editor.getMainObject() instanceof JCheckBox) {
				JCheckBox e = (JCheckBox) editor.getMainObject();
				col.setCellEditor(new DefaultCellEditor(e));
			} else if (editor.getMainObject() instanceof JTextField) {
				JTextField e = (JTextField) editor.getMainObject();
				col.setCellEditor(new DefaultCellEditor(e));
			}
		}
		
	}

}
