/**
 * 
 */
package org.javabuilders.swt.handler.event.background;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.logging.Logger;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.javabuilders.BuildException;
import org.javabuilders.Builder;
import org.javabuilders.BuilderUtils;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.swt.SwtJavaBuilder;

/**
 * Background dialog
 * @author Jacek Furmankiewicz
 */
public class BackgroundDialog  {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BackgroundDialog.class.getSimpleName());

	private Shell shell;
	@SuppressWarnings("unused")
	private Label progressLabel;
	@SuppressWarnings("unused")
	private ProgressBar progressBar;

	private BackgroundEvent event = null;
	
	/**
	 * Constructor
	 * @throws BuildException 
	 * @throws IOException 
	 */
	public BackgroundDialog(BackgroundEvent event) throws IOException, BuildException {
		BuilderUtils.validateNotNullAndNotEmpty("event", event);
		this.event = event;
		this.event.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("progressMessage")) {
					progressLabel.setText((String) evt.getNewValue());
				}
			}
		});
		SwtJavaBuilder.build(this, Builder.getResourceBundle());
	}

	/**
	 * @return The current background event
	 */
	public BackgroundEvent getEvent() {
		return event;
	}
	
	/**
	 * Requests task to be cancelled.
	 */
	@SuppressWarnings("unused")
	private void requestCancel() {
		
		boolean answer = MessageDialog.openQuestion(null, Builder.getResourceBundle().getString("message.cancelConfirm"), 
				Builder.getResourceBundle().getString("title.cancelTask"));
		
		if (answer) {
			getEvent().setCancelStatus(CancelStatus.REQUESTED);
		}
	}

	/**
	 * @return Created dialog
	 */
	public Shell getShell() {
		return shell;
	}

}
