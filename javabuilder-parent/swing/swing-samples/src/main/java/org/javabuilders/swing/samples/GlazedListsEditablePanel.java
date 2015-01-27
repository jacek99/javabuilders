package org.javabuilders.swing.samples;

import java.awt.Cursor;
import java.util.List;
import java.util.Random;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.samples.resources.Defect;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;

public class GlazedListsEditablePanel extends SamplePanel implements ListSelectionListener {

	private final EventList<Defect> defects = GlazedLists.threadSafeList(new BasicEventList<Defect>());
	
	private String[] states = {"Fixed","New","In Progress","Rejected","Works for me"};
	private String[] types = {"Bug","Enhancement","Documentation","Task","Question"};
	private String[] reporters = {"John Doe","Jane Doe","Jimmy Doenowski","Arnold Doenator","Sylvester Doellone"};
	private Random random = new Random();
	
	public GlazedListsEditablePanel() throws Exception {
		super();
		SwingJavaBuilder.build(this);
		createData(100);
		
        //EventSelectionModel<String> userSelectionModel = new EventSelectionModel<String>(null);
        //reported.setSelectionModel(userSelectionModel);
        //EventList<String> selectedUsers = userSelectionModel.getSelected();
        //selectedUsers.addListEventListener(null);
	}
	
	private void fillWithData() {
		final String value = JOptionPane.showInputDialog("How many rows of data?");
		if (value != null) {
			try {
				Integer count = Integer.parseInt(value);
				createData(count);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this,"Not a valid number");
			} 
		}
	}
	
	private void createData(final int count) {
		try {
			this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			defects.clear();
			
			Runnable r = new Runnable() {
				public void run() {
					for(int i = 0; i < count; i++) {
						Defect defect = new Defect();
						defect.setId(i);
						defect.setPriority(i % 10);
						defect.setType(types[random.nextInt(types.length)]);
						defect.setState(states[random.nextInt(states.length)]);
						defect.setReporter(reporters[random.nextInt(reporters.length)]);
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
	
	//allows filtering by Reporter or State
	public class DefectTextFilterator implements TextFilterator<Defect> {
	    public void getFilterStrings(List<String> baseList, Defect defect) {
	        baseList.add(defect.getReporter());
	        baseList.add(defect.getState());
	    }
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		// TODO Auto-generated method stub
		
	}	


}
