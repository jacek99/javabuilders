package org.javabuilders.swing.samples;

import javax.swing.JOptionPane;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings({"serial","unused"})
public class LayoutManagerShowdownChallenge extends SamplePanel {

	private BuildResult result = SwingJavaBuilder.build(this);
	
	public LayoutManagerShowdownChallenge() throws Exception {}
	
	private void newPerformed() {
		JOptionPane.showMessageDialog(this,"private void newPerformed() was called!");
	}
	
	private void deletePerformed() {
		JOptionPane.showMessageDialog(this,"private void deletePerformed() was called!");
	}
	
	private void editPerformed() {
		JOptionPane.showMessageDialog(this,"private void editPerformed() was called!");
	}
	
	private void savePerformed() {
		JOptionPane.showMessageDialog(this,"private void savePerformed() was called!");
	}
	
	private void cancelPerformed() {
		JOptionPane.showMessageDialog(this,"private void cancelPerformed() was called!");
	}
}
