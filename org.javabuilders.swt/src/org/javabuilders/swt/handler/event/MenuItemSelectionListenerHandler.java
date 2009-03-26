package org.javabuilders.swt.handler.event;

import static org.javabuilders.swt.SWTBuilder.ON_SELECTION;

import java.util.List;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.MenuItem;
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
 * Handler for menu selection listener
 * @author Jacek Furmankiewicz
 *
 */
public class MenuItemSelectionListenerHandler extends AbstractPropertyHandler  implements IPropertyList {

	private final static MenuItemSelectionListenerHandler singleton = new MenuItemSelectionListenerHandler();
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(SelectionEvent.class);
	
	/**
	 * @return Singleton
	 */
	public static MenuItemSelectionListenerHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private MenuItemSelectionListenerHandler() {
		super(ON_SELECTION);
	}
	
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config, final BuildProcess process, Node node,
			String key) throws BuildException {
	
		final MenuItem target = (MenuItem)node.getMainObject();
		final Values<String,ObjectMethod> values = (Values<String, ObjectMethod>) node.getProperty(key);

		if (values.size() > 0) {
			target.addSelectionListener(new SelectionAdapter() {
				
				public void widgetSelected(SelectionEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), target, values.values(), e);
			      }
				
			});
		}
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

	
	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<MenuItem> getApplicableClass() {
		return MenuItem.class;
	}

}
