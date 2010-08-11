/**
 * 
 */
package org.javabuilders.event;

import java.lang.reflect.Method;

import org.javabuilders.BuildResult;

/**
 * Interface for the domain-specifc pluggable background processing handlers
 * @author Jacek Furmankiewicz 
 */
public interface IBackgroundProcessingHandler {

	/**
	 * @param result Current build result
	 * @param target Target object on which the background method should be run
	 * @param method Method to run
	 * @param event Background event that can be passed to the method
	 * @param callbackWhenFinished Callback to call when the background method finishes execution
	 */
	void doInBackground( 
			BuildResult result, 
			Object target, 
			Method method, 
			BackgroundEvent event,
			IBackgroundCallback callbackWhenFinished) 
		throws Exception;
	
	
}
