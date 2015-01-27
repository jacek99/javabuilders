package org.javabuilders;

/**
 * Interface for custom commands
 * @author Jacek Furmankiewicz
 *
 */
public interface ICustomCommand<T> {

	/**
	 * Custom command implementation
	 * @param result Current build result
	 * @param  source The source that generated the event
	 * @return Return value
	 */
	T  process(BuildResult result, Object source);
	
}
