package org.javabuilders.swing.samples;

import javax.swing.JOptionPane;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings({"serial","unused"})
public class LayoutManagerShowdownChallenge extends SamplePanel {

	private BuildResult result = SwingJavaBuilder.build(this);
	
	public LayoutManagerShowdownChallenge() throws Exception {}
	
	private void doNew() {
		JOptionPane.showMessageDialog(this,"private void doNew() was called!");
	}
	
	private void delete() {
		JOptionPane.showMessageDialog(this,"private void delete() was called!");
	}
	
	private void edit() {
		JOptionPane.showMessageDialog(this,"private void edit() was called!");
	}
	
	private void save() {
		JOptionPane.showMessageDialog(this,"private void save() was called!");
	}
	
	private void cancel() {
		JOptionPane.showMessageDialog(this,"private void cancel() was called!");
	}
}
