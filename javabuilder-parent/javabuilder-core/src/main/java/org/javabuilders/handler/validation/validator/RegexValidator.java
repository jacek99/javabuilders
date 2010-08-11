/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Regex validator
 * @author Jacek Furmankiewicz
 *
 */
public class RegexValidator extends AbstractValidator {

	private String regex = "";
	
	/**
	 * Constructor
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public RegexValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, String regex) {
		super(property, label, messageFormat, result);
		this.regex = regex;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IPropertyValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
		String sValue = getStringValue(value); 
		if (!sValue.matches(regex)) {
			list.add(new ValidationMessage(getProperty(), getMessage(getLabel())));
		}
	}

	/**
	 * @return Regex
	 */
	public String getRegex() {
		return regex;
	}

	/**
	 * @param regex Regex
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}

}
