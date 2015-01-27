/**
 * 
 */
package org.javabuilders.swt.handler;

import java.lang.reflect.Method;

import org.javabuilders.BuildResult;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.IBackgroundCallback;
import org.javabuilders.event.IBackgroundProcessingHandler;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * SWT background processing handler
 * @author Jacek Furmankiewicz
 *
 */
public class DefaultBackgroundProcessingHandler implements
		IBackgroundProcessingHandler {

	private final static DefaultBackgroundProcessingHandler singleton = new DefaultBackgroundProcessingHandler();
	
	/**
	 * @return Singleton
	 */
	public static DefaultBackgroundProcessingHandler getInstance() {return singleton;}
	
	/**
	 * Private constructor
	 */
	private DefaultBackgroundProcessingHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundProcessingHandler#doInBackground(org.javabuilders.BuildResult, java.lang.Object, java.lang.reflect.Method, org.javabuilders.event.BackgroundEvent, org.javabuilders.event.IBackgroundCallback)
	 */
	public void doInBackground(BuildResult result, Object target,
			Method method, BackgroundEvent event,
			IBackgroundCallback callbackWhenFinished) throws Exception {
		
		throw new NotImplementedException();

	}

}
