package org.javabuilders.handler.validation;

/**
 * Custom validator interface
 * @author Jacek Furmankiewicz
 */
public interface ICustomValidator extends IValidator  {

	/**
	 * @param list Validation message list
	 */
	void validate(ValidationMessageList list);
}
