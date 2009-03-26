/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

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
 * JTree.getSelectionModel().addTreeSelectionListener() handler, e.g.
 * <code>JTree(onSelection=treeSelectionChanged)</code>
 * @author Jacek Furmankiewicz
 *
 */
public class JTreeSelectionListenerHandler extends AbstractPropertyHandler implements IPropertyList{

	public static final String ON_SELECTION = "onSelection";
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(TreeSelectionEvent.class);
	
	private static final JTreeSelectionListenerHandler singleton = new JTreeSelectionListenerHandler();
	
	/**
	 * @return Singleton
	 */
	public static JTreeSelectionListenerHandler getInstance() {return singleton;}
	
	
	/**
	 * Constructor
	 */
	private JTreeSelectionListenerHandler(String... consumedKeys) {
		super(ON_SELECTION);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config, final BuildProcess process, Node node,
			String key) throws BuildException {
		
		final JTree target = (JTree)node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			target.getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {

				public void valueChanged(TreeSelectionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), target, values.values(), e);
				}
				
			});
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return JTree.class;
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
