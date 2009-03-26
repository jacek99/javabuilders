package org.javabuilders;

@SuppressWarnings("serial")
public class MultipleRootsException extends BuildException {

	/**
	 * Constructor
	 * @param rootCount Number of root objects
	 */
	public MultipleRootsException(int rootCount) {
		super("Expected 1 root object, but encountered " + rootCount);
	}

}
