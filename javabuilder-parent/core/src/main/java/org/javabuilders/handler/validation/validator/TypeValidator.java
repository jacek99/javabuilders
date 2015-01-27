/**
 * 
 */
package org.javabuilders.handler.validation.validator;

import java.util.ArrayList;
import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * Handles type validation
 * @author bmojzf0
 *
 */
public class TypeValidator extends AbstractValidator {

	public static final String INT = "int";
	public static final String LONG = "long";
	public static final String SHORT = "short";
	public static final String BYTE = "byte";
	public static final String DOUBLE="double";
	public static final String FLOAT = "float";
	
	private static List<String> types = new ArrayList<String>();
	
	private String type = LONG;

	static {
		types.add(INT);
		types.add(LONG);
		types.add(SHORT);
		types.add(BYTE);
		types.add(DOUBLE);
		types.add(FLOAT);
	}
	
	/**
	 * @param property
	 * @param label
	 * @param messageFormat
	 * @param result
	 */
	public TypeValidator(NamedObjectProperty property, String label,
			String messageFormat, BuildResult result, String type) {
		super(property, label, messageFormat, result);
	
		if (!types.contains(type)) {
			throw new BuildException("\"{0}\" is not a valid type. Allowed values are: {1}", type, types);
		}
		
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.validation.IPropertyValidator#validate(java.lang.Object, org.javabuilders.handler.validation.ValidationMessageList)
	 */
	public void validate(Object value, ValidationMessageList list) {

		String errorFormat = null;
		try {
			if (type.equals(INT)) {
				errorFormat = "message.error.int";
				Integer.parseInt(String.valueOf(value));
			} else if (type.equals(LONG)) {
				errorFormat = "message.error.long";
				Long.parseLong(String.valueOf(value));
			} else if (type.equals(SHORT)) {
				errorFormat = "message.error.short";
				Short.parseShort(String.valueOf(value));
			} else if (type.equals(BYTE)) {
				errorFormat = "message.error.byte";
				Byte.parseByte(String.valueOf(value));
			} else if (type.equals(DOUBLE)) {
				errorFormat = "message.error.double";
				Double.parseDouble(String.valueOf(value));
			} else if (type.equals(FLOAT)) {
				errorFormat = "message.error.float";
				Float.parseFloat(String.valueOf(value));
			} else {
				throw new BuildException("Unexpected type: {0}" + type);
			}
		} catch (NumberFormatException e) {
			list.add(new ValidationMessage(getProperty(),getMessageForFormat(errorFormat,getLabel())));
		}
	}

}
