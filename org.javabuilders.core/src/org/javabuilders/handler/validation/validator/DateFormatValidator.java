/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import java.util.Date;
import java.util.Locale;

import org.apache.commons.validator.routines.DateValidator;
import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Date format validator
 * @author Jacek Furmankiewicz
 */
public class DateFormatValidator extends AbstractValidator {

	private String dateFormat;
	private Locale locale;
	
	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public DateFormatValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, String dateFormat, Locale locale) {
		super(property, label, messageFormat, result);
		this.dateFormat = dateFormat;
		this.locale = locale;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.ICustomValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {
		String sValue = getStringValue(value); 
		Date date = DateValidator.getInstance().validate(sValue, dateFormat, locale);
		if (date == null) {
			list.add(new ValidationMessage(getProperty(),
					getMessage(getLabel(),dateFormat)));
		}
	}

	/**
	 * @return the dateFormat
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat the dateFormat to set
	 */
	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

}
