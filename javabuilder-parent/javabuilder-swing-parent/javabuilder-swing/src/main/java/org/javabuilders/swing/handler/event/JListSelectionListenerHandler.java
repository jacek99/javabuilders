package org.javabuilders.swing.handler.event;

import java.util.List;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javabuilders.*;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * JList.getSelectionModel().addListSelectionListener() handler, e.g.
 * <code>JList(onSelection=tableSelectionChanged)</code>
 * 
 * @author Sebastien Gollion
 * @author Jacek Furmankiewicz
 */
public class JListSelectionListenerHandler extends AbstractPropertyHandler implements IPropertyList {
	private static final String ON_SELECTION = "onSelection";
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(ListSelectionEvent.class);

	private static final JListSelectionListenerHandler singleton = new JListSelectionListenerHandler();

	/**
	 * @return Singleton
	 */
	public static JListSelectionListenerHandler getInstance() {
		return singleton;
	}

	/**
	 * @param consumedKeys
	 */
	public JListSelectionListenerHandler() {
		super(ON_SELECTION);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig,
	 *      org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config, final BuildProcess process, Node node, String key) throws BuildException {
		final JList target = (JList) node.getMainObject();
		final Values<String, ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			target.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (e.getValueIsAdjusting() == false) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), target, values.values(), e);
					}
				}
			});
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}
}
