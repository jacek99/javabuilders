/**
 * 
 */
package org.javabuilders.swing.handler.event.background;

import java.awt.Component;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.swing.JDialog;
import javax.swing.SwingWorker;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.IBackgroundCallback;
import org.javabuilders.event.IBackgroundProcessingHandler;

/**
 * Background processing handler for the Swing domain
 * 
 * @author Jacek Furmankiewicz
 */
public class SwingBackgroundProcessingHandler implements IBackgroundProcessingHandler {

	private static final SwingBackgroundProcessingHandler singleton = new SwingBackgroundProcessingHandler();

	/**
	 * @return Singleton
	 */
	public static SwingBackgroundProcessingHandler getInstance() {
		return singleton;
	}

	private SwingBackgroundProcessingHandler() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.javabuilders.event.IBackgroundProcessingHandler#doInBackground(org
	 * .javabuilders.BuildResult, java.lang.Object, java.lang.reflect.Method,
	 * org.javabuilders.event.BackgroundEvent,
	 * org.javabuilders.event.IBackgroundCallback)
	 */
	public void doInBackground(final BuildResult result, final Object target, final Method method, final BackgroundEvent event,
			final IBackgroundCallback callbackWhenFinished) throws IOException, BuildException {

		Component component = null;

		if (event.getSource() != null && event.getSource() instanceof Component) {
			component = (Component) event.getSource();
		}
		final Component componentForEvent = component;
		final boolean enabled = (componentForEvent == null) ? true : componentForEvent.isEnabled();

		// show progress dialog only if task is blocking
		final JDialog progressDialog = event.isBlocking() ? new BackgroundDialog(event) : null;

		SwingWorker<Object, Object> worker = new SwingWorker<Object, Object>() {
			@Override
			protected Object doInBackground() throws Exception {
				Object value = method.invoke(target, event);
				return value;
			}

			@Override
			protected void done() {
				if (progressDialog != null) {
					progressDialog.setVisible(false);
					progressDialog.dispose();
				}

				// restore enabled state on source
				if (componentForEvent != null) {
					componentForEvent.setEnabled(enabled);
				}

				// execute outstanding methods
				try {
					callbackWhenFinished.done(get());
				} catch (Exception e) {
					throw new BuildException(e, "Unexpected exception when getting back background method return value");
				}
			}
		};

		if (componentForEvent != null) {
			componentForEvent.setEnabled(false);
		}
		worker.execute();

		if (progressDialog != null) {
			progressDialog.setVisible(true);
		}

	}

}
