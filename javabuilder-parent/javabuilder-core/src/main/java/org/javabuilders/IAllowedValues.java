/**
 * 
 */
package org.javabuilders;

import java.util.Collection;

/**
 * Optional interface that returns what the allowed values for a property.
 * Usually implemented by the property handler
 */
public interface IAllowedValues {

	/**
	 * @return Set of allowed values
	 */
	Collection<? extends Object> getAllowedValues();
}
