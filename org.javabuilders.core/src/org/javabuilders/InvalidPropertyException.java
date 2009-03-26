/**
 * 
 */
package org.javabuilders;

/**
 * Indicates an invalid property was specified
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class InvalidPropertyException extends BuildException {

	private static final String message = "Type alias '%s' does not have a property '%s'";
	
	/**
	 * @param message
	 */
	public InvalidPropertyException(String typeAlias, String propertyName) {
		super(String.format(message, typeAlias,propertyName));
	}

	/**
	 * @param cause
	 */
	public InvalidPropertyException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidPropertyException(String typeAlias, String propertyName, Throwable cause) {
		super(cause, String.format(message, typeAlias,propertyName));
	}

}
