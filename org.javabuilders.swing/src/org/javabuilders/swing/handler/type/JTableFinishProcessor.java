/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.util.Map;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.table.TableColumn;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;

/**
 * Handles property creating a JTable
 * 
 * @author Jacek Furmankiewicz
 * 
 */
public class JTableFinishProcessor implements ITypeHandlerFinishProcessor {

	private static final JTableFinishProcessor singleton = new JTableFinishProcessor();

	/**
	 * @return Singleton
	 */
	public static JTableFinishProcessor getInstance() {
		return singleton;
	}

	private JTableFinishProcessor() {
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process, Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		Node content = current.getChildNode(Builder.CONTENT);
		if (content != null) {

			JTable table = (JTable) current.getMainObject();
			Set<TableColumn> columns = current.getContentObjects(TableColumn.class);

			// some columns could be existing, some new, add only new ones
			for (TableColumn column : columns) {
				Object identifier = column.getIdentifier();

				boolean existing = false;
				if (identifier != null) {
					for (int i = 0; i < table.getColumnCount(); i++) {
						TableColumn temp = table.getColumnModel().getColumn(i);
						if (identifier.equals(temp.getIdentifier())) {
							existing = true;
							break;
						}
					}
				}

				if (!existing) {
					table.addColumn(column);
				}
			}
		}
	}

}
