package org.javabuilders.handler.validation;

import org.javabuilders.Builder;
import org.javabuilders.NamedObjectProperty;

/**
 * Validation message for a property
 * @author Jacek Furmankiewicz
 */
public class ValidationMessage {

	private NamedObjectProperty property;
	private String message;
	
	/**
	 * @param message Message
	 */
	public ValidationMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @param propertyName Property name (on the caller object, i.e. "this")
	 * @param message Message
	 */
	public ValidationMessage(String propertyName, String message) {
		this.property  = new NamedObjectProperty(Builder.THIS,propertyName);
		this.message = message;
	}
	
	/**
	 * @param objectName Object name
	 * @param propertyName Property name
	 * @param message Message
	 */
	public ValidationMessage(String objectName, String propertyName, String message) {
		this.property  = new NamedObjectProperty(objectName,propertyName);
		this.message = message;
	}
	
	/**
	 * @param property Named object property
	 * @param message Message
	 */
	public ValidationMessage(NamedObjectProperty property, String message) {
		this.property  = property;
		this.message = message;
	}
	
	/**
	 * @return the property. Can be null in the case of custom validators
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
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s: %s",property,message);
	}
	
}
