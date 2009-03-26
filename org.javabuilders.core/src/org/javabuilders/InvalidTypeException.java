/**
 * 
 */
package org.javabuilders;

/**
 * Thrown when an unknown type is encounted in a builder file
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class InvalidTypeException extends BuildException {

	private String key = "";
	
	/**
	 * @param key
	 */
	public InvalidTypeException(String key) {
		super(getKeyMessage(key,null));
		this.key = key;
	}
	
	/**
	 * @param key
	 */
	public InvalidTypeException(String key, Throwable cause) {
		super(cause,getKeyMessage(key,cause));
		this.key = key;
	}

	//basic error message
	private static String getKeyMessage(String key, Throwable cause) {
		if (cause != null) {
			return String.format("Unable to instantiate object described by alias '%s'. Cause: %s", key, cause);
		} else {
			return String.format("Unable to instantiate object described by alias '%s'. Missing type handler or invalid class name. ", key);
		}
	}

	/**
	 * @return The key
	 */
	public String getKey() {
		return key;
	}
}
