/**
 * 
 */
package org.javabuilders;

/**
 * Indicates incorrect format of a property value
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class InvalidPropertyFormatException extends BuildException {

	private final static String message = "Invalid value '%s' of property '%s' for type alias '%s'. Expected value in format '%s', e.g.'%s'";
	
	/**
	 * @param message
	 */
	public InvalidPropertyFormatException(String typeAlias, String property, String value, String format, String validFormatSample) {
		super(String.format(message, value, property, typeAlias, format, validFormatSample));
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidPropertyFormatException(String typeAlias, String property, String value, String format, String validFormatSample, Throwable cause) {
		super(cause, String.format(message, value, property, typeAlias, format, validFormatSample));
	}

}
