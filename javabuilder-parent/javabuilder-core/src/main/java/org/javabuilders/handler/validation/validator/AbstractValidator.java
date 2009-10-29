/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import java.text.MessageFormat;

import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.IPropertyValidator;
import org.javabuilders.handler.validation.IValidator;

/**
 * Abstract ancestor for built-in validator classes
 * @author Jacek Furmankiewicz
 *
 */
public abstract class AbstractValidator implements IValidator, IPropertyValidator {
	
	private String label;
	private String messageFormat;
	private BuildResult result;
	private NamedObjectProperty property;
	
	
	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public AbstractValidator(NamedObjectProperty property, String label, String messageFormat, BuildResult result) {
		this.property = property;
		this.label = label;
		this.messageFormat = messageFormat;
		this.result = result;
	}

	/**
	 * @return the label Field label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the field label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @param message the error message to set
	 */
	public void setMessageFormat(String message) {
		this.messageFormat = message;
	}
	
	/**
	 * @return the error message format
	 */
	public String getMessageFormat() {
		return messageFormat;
	}
	
	
	/**
	 * Gets the formatted message
	 * @param arguments Arguments
	 * @return Error message text
	 */
	public String getMessage(Object...arguments) {
		String pattern = result.getResource(messageFormat);
		return MessageFormat.format(pattern, arguments);
	}

	/**
	 * Gets the formatted message
	 * @param pattern Message format pattern
	 * @param arguments Arguments
	 * @return Error message text
	 */
	protected String getMessageForFormat(String pattern, Object...arguments) {
		pattern = result.getResource(pattern);
		return MessageFormat.format(pattern, arguments);
	}

	
	/**
	 * @return the property
	 */
	public NamedObjectProperty getProperty() {
		return property;
	}

	/**
	 * @param property the property to set
	 */
	public void setProperty(NamedObjectProperty property) {
		this.property = property;
	}
	
	/**
	 * @param value Object value
	 * @return String value
	 */
	protected String getStringValue(Object value) {
		return (value instanceof String) ? (String)value : String.valueOf(value);
	}

}
