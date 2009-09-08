package org.javabuilders.swing.samples;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

public class JDesktopPanePanel extends SamplePanel {

	JDesktopPane desktop;
	
	public JDesktopPanePanel() throws Exception {
		super();
		BuildResult r = SwingJavaBuilder.build(this);
		
		//desktop.setVisible(true);
		//desktop.setPreferredSize(new Dimension(400,400));
		/*
		setLayout(new BorderLayout());
		JDesktopPane pane = new JDesktopPane();
		pane.setBackground(Color.white);

		this.add(pane,BorderLayout.CENTER);
		pane.setVisible(true);
		
		JInternalFrame frame1 = new JInternalFrame();
		frame1.setTitle("TEST");
		frame1.setLayout(new BorderLayout());
		frame1.add(new JButton("TEST BUTTON"));
		frame1.show();
		frame1.setEnabled(true);
		frame1.pack();
		
		
		pane.add(frame1);
		frame1.setVisible(true);
		frame1.setSelected(true);
		
		add(new JButton("TEST 2"),BorderLayout.SOUTH);
		//pane.setPreferredSize(new Dimension(500,500));
		 * */
		 
		
	}

}
