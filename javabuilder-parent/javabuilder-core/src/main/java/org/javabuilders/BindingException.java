/**
 * 
 */
package org.javabuilders;

/**
 * Indicates a binding exception
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BindingException extends BuildException {

	private final static String beanBindingError = "Unable to bind caller '%s' property to '%s.%s'. Error: %s";
	
	
	/**
	 * Constructor
	 * @param source Source name
	 * @param sourceProperty Source property name
	 * @param destinationProperty Destination/caller property name
	 * @param errorDetails Details of the error
	 */
	public BindingException(String source, String sourceProperty, String destinationProperty, String errorDetails) {
		super(String.format(beanBindingError, destinationProperty, source, sourceProperty, errorDetails));
	}

}
