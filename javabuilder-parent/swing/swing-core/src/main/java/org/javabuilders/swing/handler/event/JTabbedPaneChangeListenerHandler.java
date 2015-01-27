/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.util.List;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handles the JTabbedPane.addChangeListener() logic to be able to listen to tab
 * changes
 * @author Jacek Furmankiewicz
 *
 */
public class JTabbedPaneChangeListenerHandler extends AbstractPropertyHandler  implements IPropertyList {

	public static final String ON_CHANGE = "onChange";
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(ChangeEvent.class);
	
	private static final JTabbedPaneChangeListenerHandler singleton = new JTabbedPaneChangeListenerHandler();

	/**
	 * @return Singleton
	 */
	public static JTabbedPaneChangeListenerHandler getInstance() {return singleton;}

	
	/**
	 * Constructor
	 */
	private JTabbedPaneChangeListenerHandler() {
		super(ON_CHANGE);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {
		
		final Values<String,ObjectMethod> onChange = (Values<String,ObjectMethod>)node.getProperty(ON_CHANGE);
		JTabbedPane pane = (JTabbedPane) node.getMainObject();
		pane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onChange.values(), e);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return JTabbedPane.class;
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
