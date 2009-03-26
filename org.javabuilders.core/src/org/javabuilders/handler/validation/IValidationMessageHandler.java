package org.javabuilders.handler.validation;

import org.javabuilders.BuildResult;

/**
 * Interface for domain-specific handler of error messages
 * @author Jacek Furmankiewicz
 *
 */
public interface IValidationMessageHandler {

	String DEFAULT_VALIDATION_ERROR_TITLE = "title.validationError";
	String DEFAULT_VALIDATION_ERRORS_TITLE = "title.validationErrors";
	
	/**
	 * @param list List of validation messages
	 * @param result The current build result
	 */
	void handleValidationMessages(ValidationMessageList list, BuildResult result);
	
	/**
	 * Gets a label for the object, if one is available
	 * @param namedObject Named object
	 * @return Label for the object or null if not known
	 */
	String getNamedObjectLabel(Object namedObject);
	
}
