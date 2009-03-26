/**
 * 
 */
package org.javabuilders;


/**
 * Marker interface that property handlers can implement to indicate they expect
 * the value in a particular format. Used for automatic validation of input
 * @author Jacek Furmankiewicz
 */
public interface IAllowedPropertyFormat {

	/**
	 * Used for informative error messages to display sample formats that are correct
	 * @param Property name (needed since a property handler can consume multiple properties
	 *        and may have different validations for each of them)
	 * @return Valid sample
	 */
	String getValidSample(String propertyName);
	
	/**
	 * The regex pattern used for validating values
	 * @param Property name (needed since a property handler can consume multiple properties
	 *        and may have different validations for each of them)
	 * @return The regex pattern used for validation
	 */
	String getRegexPattern(String propertyName);
	
}
