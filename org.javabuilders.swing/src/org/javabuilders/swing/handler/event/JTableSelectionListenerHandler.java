/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.util.List;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * JTable.getSelectionModel().addListSelectionListener() handler, e.g.
 * <code>JTable(onSelection=tableSelectionChanged)</code>
 * @author Jacek Furmankiewicz
 *
 */
public class JTableSelectionListenerHandler extends AbstractPropertyHandler implements IPropertyList {

	public static final String ON_SELECTION = "onSelection";
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(ListSelectionEvent.class);
	
	private static final JTableSelectionListenerHandler singleton = new JTableSelectionListenerHandler();

	/**
	 * @return Singleton
	 */
	public static JTableSelectionListenerHandler getInstance() {return singleton;}

	
	/**
	 * @param consumedKeys
	 */
	public JTableSelectionListenerHandler() {
		super(ON_SELECTION);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config, final BuildProcess process, Node node,
			String key) throws BuildException {

		final JTable target = (JTable)node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			target.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), target, values.values(), e);
				}
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}


	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}

}
