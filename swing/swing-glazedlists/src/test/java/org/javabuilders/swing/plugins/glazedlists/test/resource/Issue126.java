package org.javabuilders.swing.plugins.glazedlists.test.resource;

import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;

public class Issue126 extends JPanel {

	private EventList<Book> books = new BasicEventList<Book>();
	
	public Issue126() {
		SwingJavaBuilder.build(this);
	}
}
