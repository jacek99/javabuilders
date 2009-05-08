/**
 * 
 */
package org.javabuilders;

import java.text.MessageFormat;

import org.javabuilders.util.BuilderUtils;

/**
 * Abstract build exception
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BuildException extends RuntimeException {
	
	
	/**
	 * @param messageFormat Message format in MessageFormat style ({0} {1})
	 * @param messageArguments Arguments
	 */
	public BuildException(String messageFormat, Object...messageArguments) {
		super(MessageFormat.format(messageFormat, messageArguments));
	}

	/**
	 * @param cause
	 */
	public BuildException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param cause
	 * @param messageFormat Message format in MessageFormat style ({0} {1})
	 * @param messageArguments Arguments
	 */
	public BuildException(Throwable cause,String messageFormat, Object...messageArguments) {
		super(MessageFormat.format(messageFormat, BuilderUtils.getMessageFormatSafeArguments(messageArguments)), cause);
	}
	

	
}
