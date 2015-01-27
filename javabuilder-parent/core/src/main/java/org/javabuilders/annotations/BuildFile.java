/**
 * 
 */
package org.javabuilders.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to explicitly specify which YAML build file should be used
 * @author Jacek Furmankiewicz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface BuildFile {
	String value();
}
