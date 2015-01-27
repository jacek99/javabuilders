/**
 * 
 */
package org.javabuilders;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Type safe collection that holds a list of values and related information
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class Values<K,V> extends LinkedHashMap<K,V> {

	private Map<Object,String> errorMessages = new HashMap<Object, String>();
	private ValueListDefinition definition = null;
	
	/**
	 * Constructor
	 */
	public Values() {
		this(null);
	}
	
	/**
	 * Constructor
	 * @param def Value list definition for this value list
	 */
	public Values(ValueListDefinition def) {
		this.definition = def;
	}
	
	/**
	 * @param key Key
	 * @param value Value
	 * @param errorMessageFormat Error msg format (for String.format())
	 * @param messageArgs Format arguments
	 */
	public void put(K key, V value, String errorMessageFormat, Object... messageArgs) {
		put(key,value);
		errorMessages.put(key,String.format(errorMessageFormat,messageArgs));
	}
	
	
	/**
	 * @return True if a validation results is valid, false if not
	 */
	public boolean isValid(String key) {
		return errorMessages.containsKey(key);
	}
	
	/**
	 * @return True if a validation results is valid, false if not
	 */
	public boolean isValid() {
		return errorMessages.size() == 0;
	}
	
	/**
	 * @return All the errors from the invalid validations
	 */
	public String getErrors() {
		StringBuilder builder = new StringBuilder();
		for(String msg : errorMessages.values()) {
			builder.append(msg);
			builder.append("\n");
		}
		return builder.toString();
	}
	
	/**
	 * @param key Key
	 * @return Error msg
	 */
	public String getError(String key) {
		return errorMessages.get(key);
	}

	/**
	 * @return Value list definition (may be null if none defined)
	 */
	public ValueListDefinition getDefinition() {
		return definition;
	}

}
