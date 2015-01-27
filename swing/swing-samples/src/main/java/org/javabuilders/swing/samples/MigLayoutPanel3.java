package org.javabuilders.swing.samples;

import javax.swing.JOptionPane;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings({"serial","unused"})
public class MigLayoutPanel3 extends SamplePanel {

	private BuildResult result = SwingJavaBuilder.build(this);
	
	public MigLayoutPanel3() throws Exception {
		super();
	}
	
	private void addNew() {
		JOptionPane.showMessageDialog(this,"addNew() from prototype button $btnAdd");
	}
	
	private void edit() {
		JOptionPane.showMessageDialog(this,"edit() from prototype button $btnEdit");
	}
	
	private void delete() {
		JOptionPane.showMessageDialog(this,"delete() from prototype button $btnDelete");
	}

}
