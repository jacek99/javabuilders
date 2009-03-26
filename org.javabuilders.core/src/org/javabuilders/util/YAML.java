/**
 * 
 */
package org.javabuilders.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * Annotation that indicates the Javadoc for the flagged variable contains free-form Javadoc that needs to be
 * extracted into a separate run-time file
 * @author Jacek Furmankiewicz
 */
@Target({ElementType.FIELD})
public @interface YAML {}
