package org.javabuilders.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generic builder annotation to allow hooking java-side methods to components via auto-discovery
 * For future use
 * @author Jacek Furmankiewicz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface For {
	/**
	 * @return List of components that the method should be hooked up to. Accepts regex to allow wildcards
	 */
	String[] value();
}
