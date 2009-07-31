package org.javabuilders.swing.samples;

import java.awt.Cursor;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.samples.resources.Defect;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

public class GlazedListsSamplePanel extends SamplePanel {

	private final EventList<Defect> defects = GlazedLists.threadSafeList(new BasicEventList<Defect>());
	
	public GlazedListsSamplePanel() throws Exception {
		super();
		SwingJavaBuilder.build(this);
	}
	
	private void fillWithData() {
		final String value = JOptionPane.showInputDialog("How many rows of data?");
		if (value != null) {
			try {
				this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				
				defects.clear();
				
				Runnable r = new Runnable() {
					public void run() {
						Integer count = Integer.parseInt(value);
						for(int i = 0; i < count; i++) {
							Defect defect = new Defect();
							defect.setId(i);
							defect.setPriority(i % 10);
							
							String s = String.valueOf(i);
							//some simple randomization
							if (s.endsWith("0")) {
								defect.setState("FIXED");
								defect.setType("ENHANCEMENT");
								defect.setReporter("John Doe");
							} else if (s.endsWith("1")) {
								defect.setState("IN_PROGRESS");
								defect.setType("DOCUMENTATION");
								defect.setReporter("Jane Doe");
							} else if (s.endsWith("2")) {
								defect.setState("IN_PROGRESS");
								defect.setType("TASK");
								defect.setReporter("Ed Ted");
							} else if (s.endsWith("3")) {
								defect.setState("REJECTED");
								defect.setType("EXTERNAL");
								defect.setReporter("Grzegorz Kazmierczak");
							} else {
								defect.setState("OPEN");
								defect.setType("DEFECT");
								defect.setReporter("Humpty Dumpty");
							}
							defect.setSummary("Some defect, numbered as " + i);
							defects.add(defect);
						}
					}
				};
				new Thread(r).start();
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,"Not a valid number");
			} finally {
				this.setCursor(Cursor.getDefaultCursor());
			}
		}
	}
	
	//allows filtering by Reporter or State
	public class DefectTextFilterator implements TextFilterator<Defect> {
	    public void getFilterStrings(List<String> baseList, Defect defect) {
	        baseList.add(defect.getReporter());
	        baseList.add(defect.getState());
	    }
	}	


}
