/**
 * 
 */
package org.javabuilders;

import java.util.List;

/**
 * Optional interface that can be implemented by property handlers to indicate the value
 * is a list of values. If a single value is present, it will automatically be converted to
 * a single item list
 * @author Jacek Furmankiewicz
 */
public interface IPropertyList {

	/**
	 * @param propertyName Property name
	 * @return True if expects list, false if not
	 */
	boolean isList(String propertyName);
	
	/**
	 * @param propertyName Property name
	 * @return List of potential value list definitions
	 */
	List<ValueListDefinition> getValueListDefinitions(String propertyName);
	
}
