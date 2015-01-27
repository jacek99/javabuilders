/**
 * 
 */
package org.javabuilders.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to indicate a method should be run on a background thread
 * @author Jacek Furmankiewicz
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DoInBackground {
	/**
	 * @return Progress message
	 */
	String progressMessage() default "label.processing";

	/**
	 * @return If background task is cancelable or not
	 */
	boolean cancelable() default false;
	/**
	 * @return Default start value for progress bar
	 */
	int progressStart() default 1;
	/**
	 * @return Default end value for progress bar
	 */
	int progressEnd() default 100;
	/**
	 * @return Current progress value
	 */
	int progressValue() default 1;
	/**
	 * @return Indicates if task should block UI with a popup or not
	 */
	boolean blocking() default true;
	/**
	 * @return Indicates to show indeterminate progress indicator. Overrides the progress start/end/value if set to true
	 */
	boolean indeterminateProgress() default true;
}
