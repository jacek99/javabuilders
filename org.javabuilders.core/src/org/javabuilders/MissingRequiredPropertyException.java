/**
 * 
 */
package org.javabuilders;

/**
 * Indicates a required property was missing
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class MissingRequiredPropertyException extends BuildException {

	private final static String message = "Entry for alias '%s' is missing required property '%s'.\nCurrent document node:\n%s";
	
	/**
	 * Constructor
	 * @param alias type alias
	 * @param requiredPropertyName Required property name
	 * @param documentNode Current document node
	 */
	public MissingRequiredPropertyException(String alias, String requiredPropertyName, Object documentNode) {
		super(String.format(message, alias,requiredPropertyName, documentNode));
	}

}
