package org.javabuilders.swing.samples;

import java.text.DateFormat;
import java.util.Calendar;

import javax.swing.JLabel;

import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings({"serial","unused"})
public class EventsPanel extends SamplePanel {

	private JLabel lbl1;
	private JLabel focusLabel;
	private JLabel focusLostLabel;
	private JLabel keyPressedLabel;
	private JLabel keyReleasedLabel;
	private JLabel keyTypedLabel;
	private JLabel mouseEnteredLabel;
	private JLabel mouseExitedLabel;
	private JLabel mouseClickedLabel;
	private JLabel mouseDoubleClickedLabel;
	private JLabel mouseRightClickedLabel;
	private JLabel wheelLabel;

	

	
	//private 

	public EventsPanel() throws Exception {
		super();
		SwingJavaBuilder.build(this);
	}
	
	private void onActionTest() {
		lbl1.setText("onAction: " + getTime() );
	}
	
	private void onFocusTest() {
		focusLabel.setText("onFocus: " + getTime());
	}
	
	private void onFocusLostTest() {
		focusLostLabel.setText("onFocusLost: " + getTime());
	}
	
	private void onKeyPressedTest() {
		keyPressedLabel.setText("onKeyPressed: " + getTime());
	}

	private void onKeyReleasedTest() {
		keyReleasedLabel.setText("onKeyReleased: " + getTime());
	}

	private void onKeyTypedTest() {
		keyTypedLabel.setText("onKeyTyped: " + getTime());
	}
	
	private void onMouseEnteredTest() {
		mouseEnteredLabel.setText("onMouseEntered: " + getTime());
	}
	
	private void onMouseExitedTest() {
		mouseExitedLabel.setText("onMouseExited: " + getTime());
	}

	private void onMouseClickedTest() {
		mouseClickedLabel.setText("onMouseClicked: " + getTime());
	}

	private void onMouseDoubleClickedTest() {
		mouseDoubleClickedLabel.setText("onMouseDoubleClicked: " + getTime());
	}

	private void onMouseRightClickedTest() {
		mouseRightClickedLabel.setText("onMouseRightClicked: " + getTime());
	}
	
	private void onMouseWheelMovedTest() {
		wheelLabel.setText("onMouseWheelMoved: " + getTime());
	}

	private String getTime() {
		return DateFormat.getTimeInstance(DateFormat.MEDIUM).format(Calendar.getInstance().getTimeInMillis());
	}
}
