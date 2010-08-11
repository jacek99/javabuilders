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

	private static final String message = "{0}.{1} : unable to set value \"{2}\".\n{3}.\n{4}";
	
	/**
	 * @param message
	 */
	public InvalidPropertyException(Throwable e,String typeAlias, String propertyName, Object value, String properties) {
		super(e, message, typeAlias,propertyName, value, e.getMessage(), properties);
	}

}
