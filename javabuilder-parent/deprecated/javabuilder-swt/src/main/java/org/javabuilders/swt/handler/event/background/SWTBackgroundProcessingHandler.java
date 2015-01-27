/**
 * 
 */
package org.javabuilders.swt.handler.event.background;

import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.Method;

import org.eclipse.swt.widgets.Display;
import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.IBackgroundCallback;
import org.javabuilders.event.IBackgroundProcessingHandler;
import org.javabuilders.swt.worker.SWTWorker;

/**
 * Background processing handler for the Swing domain
 * @author Jacek Furmankiewicz
 */
public class SWTBackgroundProcessingHandler implements
		IBackgroundProcessingHandler {
	
	private static final SWTBackgroundProcessingHandler singleton = new SWTBackgroundProcessingHandler();
	
	/**
	 * @return Singleton
	 */
	public static SWTBackgroundProcessingHandler getInstance() {return singleton;}
	
	private SWTBackgroundProcessingHandler() {}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundProcessingHandler#doInBackground(org.javabuilders.BuildResult, java.lang.Object, java.lang.reflect.Method, org.javabuilders.event.BackgroundEvent, org.javabuilders.event.IBackgroundCallback)
	 */
	public void doInBackground(final BuildResult result, final Object target,
			final Method method, final BackgroundEvent event, 
			final IBackgroundCallback callbackWhenFinished) 
			throws IOException, BuildException {

		Component component = null;
		
		if (event.getSource() != null && event.getSource() instanceof Component) {
			component = (Component)event.getSource();
		}
		final Component componentForEvent = component;
		final boolean enabled = (componentForEvent == null) ? true : componentForEvent.isEnabled();
		
		//show progress dialog only if task is blocking
		final BackgroundDialog progressDialog = event.isBlocking() ? new BackgroundDialog(event) : null;
		
		SWTWorker<Object,Object> worker = new SWTWorker<Object, Object> (Display.getDefault()) {
			@Override
			protected Object doInBackground() throws Exception {
				Object value = method.invoke(target, event);
				return value;
			}
			
		   @Override
	       protected void done() {
			   if (progressDialog != null && !progressDialog.getShell().isDisposed()) {
				   progressDialog.getShell().close();
			   }
			   
			   //restore enabled state on source
			   if (componentForEvent != null) {
				   componentForEvent.setEnabled(enabled);
			   }
			   
			   //execute outstanding methods
			   try {
				callbackWhenFinished.done(get());
			   } catch (Exception e) {
				   throw new BuildException(e,"Unexpected exception when getting back background method return value");
			   }
	       }
		};
		
		if (componentForEvent != null) {
			componentForEvent.setEnabled(false);
		}
		worker.execute();
		
		if (progressDialog != null) {
			progressDialog.getShell().open();
		}
		
		
		
		
	}

}
