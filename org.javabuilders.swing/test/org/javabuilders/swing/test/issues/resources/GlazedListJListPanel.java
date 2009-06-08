package org.javabuilders.swing.test.issues.resources;

import java.util.List;

import javax.swing.JList;
import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.swing.SwingJavaBuilder;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.swing.EventListModel;

//tests GlazedLists JList 
public class GlazedListJListPanel extends JPanel {

	private EventList<String> values = new BasicEventList<String>();
	
	private BuildResult result;
	
	public GlazedListJListPanel() {
		values.add("1");
		result = SwingJavaBuilder.build(this);
	}
	
	public List<String> getValues() {
		return values;
	}
	
	public JList getJList() {
		return (JList) result.get("list");
	}
	
	public EventListModel<String> getModel() {
		return (EventListModel<String>) result.get("model");
	}
	
}
