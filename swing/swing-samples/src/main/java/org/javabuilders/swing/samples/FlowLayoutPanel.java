package org.javabuilders.swing.samples;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings("serial")
public class FlowLayoutPanel extends SamplePanel {

	@SuppressWarnings("unused")
	private BuildResult result = SwingJavaBuilder.build(this);
	
	public FlowLayoutPanel() throws Exception {
		super();
	}
	
	@SuppressWarnings("unused")
	private void showButton(JButton button) {
		JOptionPane.showMessageDialog(this,"Button '" + button.getText() + "' was clicked");
	}

}
