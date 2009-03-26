package org.javabuilders.event;

/**
 * Interface used by the background handler to inform the calling object that it has completed
 * running the background task
 * @author Jacek Furmankiewicz
 *
 */
public interface IBackgroundCallback {

	
	/**
	 * @param returnValue Returned from the background method
	 */
	void done(Object returnValue);
	
}
