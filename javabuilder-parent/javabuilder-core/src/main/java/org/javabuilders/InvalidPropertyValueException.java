/**
 * 
 */
package org.javabuilders;

import java.util.Collection;

/**
 * Indicates incorrect format of a property value
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class InvalidPropertyValueException extends BuildException {

	private final static String formatMessage = "Invalid value '%s' of property '%s' for type alias '%s'. Expected value in format '%s', e.g.'%s'";
	private final static String valueMessage = "Invalid value '%s' of property '%s' for type alias '%s'. Allowed values are: %s";
	
	/**
	 * @param formatMessage
	 */
	public InvalidPropertyValueException(String typeAlias, String property, Object value, Collection<? extends Object> allowedValues) {
		super(String.format(valueMessage, value, property, typeAlias, allowedValues));
	}

	/**
	 * @param formatMessage
	 * @param cause
	 */
	public InvalidPropertyValueException(String typeAlias, String property, Object value, String format, Collection<? extends Object> allowedValues, Throwable cause) {
		super(cause,String.format(valueMessage, value, property, typeAlias, allowedValues));
	}
	
	/**
	 * @param formatMessage
	 */
	public InvalidPropertyValueException(String typeAlias, String property, Object value, String format, String validFormatSample) {
		super(String.format(formatMessage, value, property, typeAlias, format, validFormatSample));
	}

	/**
	 * @param formatMessage
	 * @param cause
	 */
	public InvalidPropertyValueException(String typeAlias, String property, Object value, String format, String validFormatSample, Throwable cause) {
		super(cause, String.format(formatMessage, value, property, typeAlias, format, validFormatSample));
	}

}
