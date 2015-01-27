package org.javabuilders.test.resources;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.test.TestBuilderConfig;

public class Issue38Class {

	public static class View extends JPanel {
		
		private JLabel test;
		
		public View() {
			BuilderConfig c = new TestBuilderConfig(JPanel.class,JLabel.class);
			c.forType(JPanel.class).children(Component.class,0,Integer.MAX_VALUE);
			Builder.build(c, this);
		}
		
		public JLabel getLabel() {
			return test;
		}
		
		
	}
	
}
