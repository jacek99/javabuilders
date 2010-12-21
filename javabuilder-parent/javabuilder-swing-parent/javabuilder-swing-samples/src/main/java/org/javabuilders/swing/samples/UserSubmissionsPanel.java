/**
 * 
 */
package org.javabuilders.swing.samples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextArea;

import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.samples.external.CardLayoutFrame;
import org.javabuilders.swing.samples.external.CenteredJFrame;
import org.javabuilders.util.BuilderUtils;

/**
 * User submissions
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings({"serial","unused"})
public class UserSubmissionsPanel extends SamplePanel implements ActionListener {

	private JTextArea source;
	
	public UserSubmissionsPanel() throws Exception {
		super();
		SwingJavaBuilder.build(this);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getActionCommand().equals("cardLayoutInFrame")) {
				CardLayoutFrame cardLayoutInFrame  = new CardLayoutFrame();
				cardLayoutInFrame.setVisible(true);
				source.setText(BuilderUtils.getYamlContent(SwingJavaBuilder.getConfig(), CardLayoutFrame.class));
			} else if (e.getActionCommand().equals("centeredJFrame")) {
				CenteredJFrame frame = new CenteredJFrame();
				source.setText(BuilderUtils.getYamlContent(SwingJavaBuilder.getConfig(), CenteredJFrame.class));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
