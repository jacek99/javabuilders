/**
 * 
 */
package org.javabuilders;

/**
 * Indicates a required property was missing
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class MissingRequiredTypeException extends BuildException {

	private final static String message = "Entry for alias '%s' is missing required type '%s'.\nCurrent document node:\n%s";
	
	/**
	 * Constructor
	 * @param alias type alias
	 * @param requiredType Required type name
	 * @param documentNode Current document node
	 */
	public MissingRequiredTypeException(String alias, Class<?> requiredType, Object documentNode) {
		super(String.format(message, alias,requiredType.getName(), documentNode));
	}

}
