package org.javabuilders.exception;

import org.javabuilders.BuildException;

/**
 * Thrown if an  unknown alias in encountered in the YAML
 * @author Jacek Furmankiewicx
 */
public class UnrecognizedAliasException extends BuildException {

	private static final long serialVersionUID = -4492413379928323054L;

	public UnrecognizedAliasException(String messageFormat,
			Object... messageArguments) {
		super(messageFormat, messageArguments);
	}

}
