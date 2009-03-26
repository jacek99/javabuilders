/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Min length validator
 * @author Jacek Furmankiewiczs
 */
public class MinLengthValidator extends AbstractValidator {

	private Long minLength;
	
	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public MinLengthValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, Long minLength) {
		super(property, label, messageFormat, result);
		this.minLength = minLength;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.ICustomValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
		String sValue = getStringValue(value);
		
		if (sValue == null || sValue.length() < minLength) {
			//non-strings, just check for null
			list.add(new ValidationMessage(getProperty(),getMessage(getLabel(),minLength)));
		}
	}

	/**
	 * @return the minLength
	 */
	public Long getMinLength() {
		return minLength;
	}

	/**
	 * @param minLength the minLength to set
	 */
	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

}
