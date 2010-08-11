/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Email address validator
 * @author Jacek Furmankiewicz
 *
 */
public class EmailAddressValidator extends AbstractValidator {

	private static String REGEX = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
	
	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public EmailAddressValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result) {
		super(property, label, messageFormat, result);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
		String sValue = getStringValue(value);
		if (StringUtils.isNotEmpty(sValue)) {
			if (!Pattern.matches(REGEX, sValue)) {
				list.add(new ValidationMessage(getProperty(),
						getMessage(getLabel())));
			}
		}
	}

}
