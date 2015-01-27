/**
 * 
 */
package org.javabuilders.handler.validation;

import org.javabuilders.NamedObjectProperty;

/**
 * Interface for property validators
 * @author Jacek Furmankiewicz
 *
 */
public interface IPropertyValidator extends IValidator {

	/**
	 * @return the property
	 */
	public NamedObjectProperty getProperty();
	
	/**
	 * @param list Validation message list
	 */
	void validate(Object value, ValidationMessageList list);
	
}
