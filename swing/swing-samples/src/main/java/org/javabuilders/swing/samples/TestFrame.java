package org.javabuilders.swing.samples;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import org.javabuilders.BuildException;
import org.javabuilders.annotations.Alias;
import org.javabuilders.swing.SwingJavaBuilder;

public class TestFrame extends JFrame implements ActionListener {

	@Alias("fNameField") private JTextField  firstNameTextField;
	private JLabel statusLabel;
	
	public TestFrame() throws BuildException, IOException {
		
		ResourceBundle bundle = ResourceBundle.getBundle("Resources");
		SwingJavaBuilder.build(this, bundle);
		
		firstNameTextField.setText("My name is...");
		pack();
	}
	
	@Alias("save") private void saveTheWindow() {
		JOptionPane.showMessageDialog(this, "Save invoked!");
	}
	
	private void cancel() {
		JOptionPane.showMessageDialog(this, "Cancel invoked!");
	}
	
	private void edit(JButton sender, ActionEvent e) {
		JOptionPane.showMessageDialog(this, "Edit from sender: " + sender.getName() + " and action: "  + e.getActionCommand());
	}
	
	private void onFirstNameFocus(JTextComponent textField) {
		this.setTitle("First name gained focus: " + textField.getName());
	}
	
	private void onFirstNameFocusLost() {
		this.setTitle("First name lost focus");
	}
	
	private void onLastNameKeyPressed(KeyEvent evt) {
		this.setTitle("Last name key pressed: " + evt.getKeyChar());
	}
	
	private void onLastNameKeyReleased() {
		this.setTitle("Last name key released");
	}
	
	private void onTitleKeyTyped() {
		this.setTitle("Title key typed");
	}
	
	public void actionPerformed(ActionEvent arg0) {
		JOptionPane.showMessageDialog(this, arg0.getActionCommand());
	}
	
	private void onMouseClicked(Component component, MouseEvent e) {
		statusLabel.setText("onMouseClicked: " + component.getName());
	}
	
	private void onMouseDoubleClicked(Component component) {
		statusLabel.setText("onMouseDoubleClicked: " + component.getName());		
	}
	
	private void onMouseRightClicked(MouseEvent e) {
		statusLabel.setText("onMouseRightClicked: " + e.paramString());		
	}
	
	private void onMouseEvent(MouseEvent e) {
		statusLabel.setText("Mouse: " + e.getComponent().getName() + " : " + e.paramString());
	}
	
}
