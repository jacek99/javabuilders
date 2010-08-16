/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Max length validator
 * @author Jacek Furmankiewicz
 *
 */
public class MaxLengthValidator extends AbstractValidator {

	private Integer maxLength=0;
	
	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public MaxLengthValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, Integer maxLength) {
		super(property, label, messageFormat, result);
		this.maxLength = maxLength;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.ICustomValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
		String sValue = getStringValue(value);
		
		if (sValue != null && sValue.length() > maxLength) {
			//non-strings, just check for null
			list.add(new ValidationMessage(getProperty(),getMessage(getLabel(),maxLength)));
		}

	}

	/**
	 * @return the maxLength
	 */
	public Integer getMaxLength() {
		return maxLength;
	}

	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

}
