/**
 * 
 */
package org.javabuilders;

import java.util.Set;

/**
 * Indicates an object that consumes key values from a node in 
 * a build document
 * @author Jacek Furmankiewicz
 */
public interface IKeyValueConsumer extends IApplicable {
	
	/**
	 * @return The list of keys that the object <b>can</b> consume
	 */
	Set<String> getConsumedKeys();
	
}
