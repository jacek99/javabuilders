/**
 * 
 */
package org.javabuilders.swing.handler.event.background;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.javabuilders.BuildException;
import org.javabuilders.Builder;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.SwingJavaBuilderUtils;
import org.javabuilders.util.BuilderUtils;

/**
 * Background dialog
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BackgroundDialog extends JDialog {
	
	@SuppressWarnings("unused")
	private JProgressBar progressBar = null;
	private BackgroundEvent event = null;
	
	private JPanel mainPanel;
	
	/**
	 * Constructor
	 * @throws BuildException 
	 * @throws IOException 
	 */
	public BackgroundDialog(BackgroundEvent event) throws IOException, BuildException {
		BuilderUtils.validateNotNullAndNotEmpty("event", event);
		this.event = event;
		SwingJavaBuilder.build(this, Builder.getResourceBundle());
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		setLocationRelativeTo((Component) SwingJavaBuilderUtils.getTopLevelParent(event.getSource())); //centers around source window
		pack();
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
		int response = JOptionPane.showConfirmDialog(this, Builder.getResourceBundle().getString("message.cancelConfirm"),
				Builder.getResourceBundle().getString("title.cancelTask"), JOptionPane.YES_NO_OPTION);
		
		if (response == JOptionPane.YES_OPTION) {
			getEvent().setCancelStatus(CancelStatus.REQUESTED);
		}
	}

}
