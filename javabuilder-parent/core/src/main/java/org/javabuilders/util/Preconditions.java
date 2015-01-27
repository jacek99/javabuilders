package org.javabuilders.util;

import org.javabuilders.BuildException;

/**
 * Simple pre-conditions (we could have used the ones from Google Collections, but avoided them due to extra
 * dependencies)
 * @author Jacek Furmankiewicz
 */
public class Preconditions {

	/**
	 * Verifies an object is not null
	 * @param source
	 * @param messageFormat
	 * @param params
	 */
	public static void checkNotNull(Object source, String messageFormat, Object...params) {
		if (source == null) {
			throw new BuildException(messageFormat,params);
		}
	}
}
