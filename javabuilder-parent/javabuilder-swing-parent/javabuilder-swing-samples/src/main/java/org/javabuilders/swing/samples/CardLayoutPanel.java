package org.javabuilders.swing.samples;

import java.awt.CardLayout;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

@SuppressWarnings({ "unused", "serial" })
public class CardLayoutPanel extends SamplePanel {

	private BuildResult result = SwingJavaBuilder.build(this);
	private CardLayout cards;
	
	public CardLayoutPanel() throws Exception {
		super();
	}
	
	
	private void switch1() {
		cards.show(this,"panel1");
	}
	
	private void switch2() {
		cards.show(this,"panel2");
	}
	


}
