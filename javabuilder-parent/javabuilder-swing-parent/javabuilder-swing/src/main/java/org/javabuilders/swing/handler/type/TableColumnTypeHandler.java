package org.javabuilders.swing.handler.type;

import java.util.Map;
import java.util.Set;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractTypeHandler;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.util.JBStringUtils;
/**
 * TableColumn handler
 * @author Jacek Furmankiewicz
 *
 */
public class TableColumnTypeHandler extends AbstractTypeHandler implements ITypeHandlerFinishProcessor {

	/**
	 * Defines custom property name that controls if a cell renderer should be for the cell (default) or for the header
	 */
	public static final String FOR_HEADER = "forHeader";
	public static final String IDENTIFIER = "identifier";
	public static final String HEADER_VALUE = "headerValue";
	public static final String FILTER = "filter"; //handled by table model handlers
	public static final String EDITABLE = "editable"; //handled by table model handlers
	
	public static final String INTERNAL_MODEL_INDEX = Builder.INTERNAL_FIELD_PREFIX + "modelIndex";

	
	private static final TableColumnTypeHandler singleton = new TableColumnTypeHandler();
	
	/**	
	 * @return Singleton
	 */
	public static TableColumnTypeHandler getInstance() {
		return singleton;
	}
	
	//constructor
	private TableColumnTypeHandler() {
		super(FOR_HEADER,FILTER,EDITABLE);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#createNewInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public Node createNewInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition) throws BuildException {
		TableColumn instance = null;
		JTable table = (JTable) parent.getParentObject(JTable.class);

		if (table != null) {
			
			//try to see if a column with this identifier or name already exists
			//If it was already processed by a separate model or binding it should have the
			//INTERNAL_MODEL_INDEX field in it.
			Integer index = (Integer) typeDefinition.get(INTERNAL_MODEL_INDEX);
			if (index != null) {
				int count = table.getColumnModel().getColumnCount();
				for(int i = 0; i < count; i++) {
					TableColumn col = table.getColumnModel().getColumn(i);
					//attempt to find the column by model index
					if (col.getModelIndex() == index) {
						instance = col;
						break;
					} 
				}
			}
		} 
		if (instance == null) {
			instance = new TableColumn();
		}
		return useExistingInstance(config, process, parent, key, typeDefinition, instance);
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#useExistingInstance(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map, java.lang.Object)
	 */
	public Node useExistingInstance(BuilderConfig config, BuildProcess process, Node parent, String key,
			Map<String, Object> typeDefinition, Object instance) throws BuildException {
		
		Node node = new Node(parent,key, typeDefinition,instance);
		return node;
	}
	
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
			if (Boolean.TRUE.equals(renderer.getProperty(FOR_HEADER))) {
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
		
		//table column header
		String headerValue = current.getStringProperty(HEADER_VALUE);
		if (headerValue == null) {
			//create default header value if none specified
			headerValue = String.valueOf(col.getHeaderValue());
			col.setHeaderValue(JBStringUtils.getDisplayName(headerValue));
		}
		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<TableColumn> getApplicableClass() {
		return TableColumn.class;
	}

}
