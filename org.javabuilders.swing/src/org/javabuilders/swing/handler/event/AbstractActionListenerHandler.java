/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.logging.Logger;

import org.javabuilders.Builder;
import org.javabuilders.IPropertyList;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Abstract handler for the onAction event (via an ActionListener). Extended for object-specific versions
 * @author Jacek Furmankiewicz
 *
 */
public abstract class AbstractActionListenerHandler extends AbstractPropertyHandler implements IPropertyList {

	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(AbstractButtonActionListenerHandler.class.getSimpleName());
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(ActionEvent.class);
	
	/**
	 * Constructor
	 */
	protected AbstractActionListenerHandler() {
		super(Builder.ON_ACTION);
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

}
