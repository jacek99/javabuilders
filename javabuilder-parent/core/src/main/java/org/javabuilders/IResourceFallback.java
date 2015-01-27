package org.javabuilders;

/**
 * Interface used to define how to get a value for a resource key if it is not found in the default ResourceBundles
 * @author jacek
 *
 */
public interface IResourceFallback {
	/**
	 * @param key Key
	 * @return Value
	 */
	String get(String key);
}
