/**
 * 
 */
package org.javabuilders.swing.handler.event.background;

import java.awt.Color;
import java.awt.Component;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.annotations.BuildFile;
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
@BuildFile("BackgroundDialog.yml")
public class BackgroundDialog extends JDialog {
	
	@SuppressWarnings("unused")
	private JProgressBar progressBar = null;
	private BackgroundEvent event = null;
	private BuildResult result = null;
	private JPanel mainPanel;
	
	/**
	 * Constructor
	 * @throws BuildException 
	 * @throws IOException 
	 */
	public BackgroundDialog(BackgroundEvent event, BuildResult result) throws IOException, BuildException {
		BuilderUtils.validateNotNullAndNotEmpty("event", event);
		this.event = event;
		this.result = result;
		SwingJavaBuilder.build(this);
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
		int response = JOptionPane.showConfirmDialog(this, result.getResource("message.cancelConfirm"),
				result.getResource("title.cancelTask"), JOptionPane.YES_NO_OPTION);
		
		if (response == JOptionPane.YES_OPTION) {
			getEvent().setCancelStatus(CancelStatus.REQUESTED);
		}
	}

}
