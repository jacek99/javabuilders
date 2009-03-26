/**
 * 
 */
package org.javabuilders.layout;

import org.javabuilders.BuildException;


/**
 * Indicates an issue with processing layouts
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class LayoutException extends BuildException {

	/**
	 * @param message
	 */
	public LayoutException(String message, Object...args) {
		super(message, args);
	}

	/**
	 * @param cause
	 */
	public LayoutException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public LayoutException(String message, Throwable cause) {
		super(cause, message);
	}

}
