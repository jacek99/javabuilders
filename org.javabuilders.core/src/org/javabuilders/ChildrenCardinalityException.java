/**
 * 
 */
package org.javabuilders;

/**
 * Children cardinality exception
 * @author Jacek Furmankiewicz
 */
public class ChildrenCardinalityException extends BuildException {

	private static final long serialVersionUID = -3361737994936544730L;

	/**
	 * @param messageFormat
	 * @param messageArguments
	 */
	public ChildrenCardinalityException(String messageFormat, Object... messageArguments) {
		super(messageFormat, messageArguments);
	}

	/**
	 * @param cause
	 */
	public ChildrenCardinalityException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param cause
	 * @param messageFormat
	 * @param messageArguments
	 */
	public ChildrenCardinalityException(Throwable cause, String messageFormat, Object... messageArguments) {
		super(cause, messageFormat, messageArguments);
	}

}
