/**
 * 
 */
package org.javabuilders;

import java.util.Set;

/**
 * Indicates the build file wanted to create a class instance
 * under a parent type that is not allowed
 * @author Jacek Furmankiewicz
 *
 */
public class InvalidParentTypeException extends BuildException {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 5527212031302490576L;
	
	private final static String message = "Attempted to create a class of type '%s' under an invalid parent of type '%s'. Only allowed parent types are: %s.";
	
	/**
	 * Constructor
	 * @param typeClass Class that was supposed to be instantiated
	 * @param invalidParentClass Parent class that causes the exception
	 * @param allowedParentClasses List of class types that are actually allowed
	 */
	public InvalidParentTypeException(Class<?> typeClass, Class<?> invalidParentClass, Set<Class<?>> allowedParentClasses) {
		super(String.format(message,typeClass,invalidParentClass,allowedParentClasses));
	}

}
