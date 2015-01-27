/**
 * 
 */
package org.javabuilders;

/**
 * Indicates a configuration problem
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class ConfigurationException extends BuildException {

	/**
	 * @param message
	 */
	public ConfigurationException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public ConfigurationException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ConfigurationException(String message, Throwable cause) {
		super(cause, message);
	}

}
