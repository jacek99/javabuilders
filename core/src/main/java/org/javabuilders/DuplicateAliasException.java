/**
 * 
 */
package org.javabuilders;

/**
 * Indicates that a duplicate alias was encountered when attempting to register
 * a type with a builder
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class DuplicateAliasException extends RuntimeException {

	/**
	 * @param message Message
	 */
	public DuplicateAliasException(String message) {
		super(message);
	}

}
