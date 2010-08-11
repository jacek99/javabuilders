/**
 * 
 */
package org.javabuilders.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Abstract class to make writing property handlers easier
 * @author Jacek Furmankiewicz
 *
 */
public abstract class AbstractPropertyHandler implements IPropertyHandler {

	private Set<String> consumedKeys = new HashSet<String>();
	
	/**
	 * Constructor
	 * @param consumedKeys The list of keys this handler consumes (one handler can consume multiple ones, if required)
	 */
	public AbstractPropertyHandler(String... consumedKeys) {
		this(new HashSet<String>(Arrays.asList(consumedKeys)));
	}
	
	/**
	 * Constructor
	 * @param consumedKeys The list of keys this handler consumes (one handler can consume multiple ones, if required)
	 */
	public AbstractPropertyHandler(Set<String> consumedKeys) {
		this.consumedKeys = consumedKeys;
	}


	
	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getConsumedKeys()
	 */
	public Set<String> getConsumedKeys() {
		return consumedKeys;
	}

}
