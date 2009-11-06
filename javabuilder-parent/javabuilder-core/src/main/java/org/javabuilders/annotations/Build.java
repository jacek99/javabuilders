package org.javabuilders.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Generic builder annotation to allow moving build info from YAML at field or method level
 * For future use
 * @author Jacek Furmankiewicz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Build {
	String value();
}
