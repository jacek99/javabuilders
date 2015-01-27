/**
 * 
 */
package org.javabuilders;


/**
 * Optional interfaces that property handlers can implement to indicate what combinations of
 * properties are mandatory. E.g. the frame size handlers expects "size" or ("height" and "width").
 * Only those two cominations are valid 
 * @author Jacek Furmankiewicz
 */
public interface IAllowedPropertyCombinations {

	/**
	 * Returns set of allowed combinations, e.g.
	 * <ul>
	 * <li>size</li>
	 * <li>width,height</li>
	 * </ul>
	 * @return Set of allowed combinations
	 */
	PropertyCombination getAllowedCombinations();
	
}
