package org.javabuilders.handler.validation;

import lombok.Value;
import org.javabuilders.Builder;
import org.javabuilders.NamedObjectProperty;

import java.util.Optional;

/**
 * Validation message for a property
 * @author Jacek Furmankiewicz
 */
@Value
public class ValidationMessage {

	private final Optional<NamedObjectProperty> property;
	private final String message;
	
	/**
	 * @param message Message
	 */
	public ValidationMessage(String message) {
		this.property = Optional.empty();
        this.message = message;

	}
	
	/**
	 * @param propertyName Property name (on the caller object, i.e. "this")
	 * @param message Message
	 */
	public ValidationMessage(String propertyName, String message) {
		this.property  = Optional.of(new NamedObjectProperty(Builder.THIS,propertyName));
		this.message = message;
	}
	
	/**
	 * @param objectName Object name
	 * @param propertyName Property name
	 * @param message Message
	 */
	public ValidationMessage(String objectName, String propertyName, String message) {
		this.property  = Optional.of(new NamedObjectProperty(objectName,propertyName));
		this.message = message;
	}
	
	/**
	 * @param property Named object property
	 * @param message Message
	 */
	public ValidationMessage(NamedObjectProperty property, String message) {
		this.property  = Optional.of(property);
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
