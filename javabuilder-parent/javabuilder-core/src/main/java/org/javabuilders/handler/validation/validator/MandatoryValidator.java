/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Mandatory validator
 * @author Jacek Furmankiewicz
 *
 */
public class MandatoryValidator extends AbstractValidator {

	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public MandatoryValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result) {
		super(property, label, messageFormat, result);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.ICustomValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
		if (value == null) {
			//non-strings, just check for null
			list.add(new ValidationMessage(getProperty(),getMessage(getLabel())));
		} else if (value instanceof String && ((String)value).trim().length() == 0) {
			list.add(new ValidationMessage(getProperty(),getMessage(getLabel())));
		}
	}

}
