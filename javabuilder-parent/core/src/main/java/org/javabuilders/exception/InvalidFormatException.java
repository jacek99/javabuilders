package org.javabuilders.exception;

import org.javabuilders.BuildException;

/**
 * Thrown if an invalid character is found in the YAML
 * @author Jacek Furmankiewicz
 *
 */
public class InvalidFormatException extends BuildException {

	private static final long serialVersionUID = -7414063354223404642L;

	public InvalidFormatException(String messageFormat,
			Object... messageArguments) {
		super(messageFormat, messageArguments);
	}

}
