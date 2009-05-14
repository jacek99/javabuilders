/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.util.Map;

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
 * @author Jacek Furmankiewicz
 *
 */
public class JTableFinishProcessor implements ITypeHandlerFinishProcessor {

	private static final JTableFinishProcessor singleton = new JTableFinishProcessor();
	
	/**
	 * @return Singleton
	 */
	public static JTableFinishProcessor getInstance() {return singleton;}
	
	private JTableFinishProcessor() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {
		
			Node content = current.getChildNode(Builder.CONTENT);
			if (content != null) {
				
				JTable table = (JTable) current.getMainObject(); 
				
				for(Node child : content.getChildNodes()) {
					if (child.getMainObject() instanceof TableColumn) {
						TableColumn tc = (TableColumn) child.getMainObject();
						table.addColumn(tc);
					}
				}
			}
	}

}
