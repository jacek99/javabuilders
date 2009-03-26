/**
 * 
 */
package org.javabuilders.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.javabuilders.Builder;

/**
 * Abstract class to make writing type handlers easier
 * @author Jacek Furmankiewicz
 */
public abstract class AbstractTypeHandler implements ITypeHandler {

	private Set<String> consumedKeys = new HashSet<String>();

	/**
	 * Constructor
	 * @param consumedKeys List of consumed keys
	 */
	public AbstractTypeHandler(String... consumedKeys) {
		this(new HashSet<String>(Arrays.asList(consumedKeys)));
	}
	
	/**
	 * Constructor
	 * @param consumedKeys List of consumed keys
	 */
	public AbstractTypeHandler(Set<String> consumedKeys) {
		this.consumedKeys = consumedKeys;
	}

	
	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getConsumedKeys()
	 */
	public Set<String> getConsumedKeys() {
		return consumedKeys;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#getCollectionPropertyName()
	 */
	public String getCollectionPropertyName() {
		return Builder.CONTENT;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandler#getSimpleValuePropertyName()
	 */
	public String getSimpleValuePropertyName() {
		return Builder.VALUE;
	}
}
