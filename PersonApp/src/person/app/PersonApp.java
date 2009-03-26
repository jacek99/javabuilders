package person.app;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.javabuilders.BuildResult;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.swing.SwingJavaBuilder;

public class PersonApp extends JFrame {

	private Person person;

	private BuildResult result;
	
	public PersonApp() {
		person = new Person();
		person.setFirstName("John");
		person.setLastName("Smith");
		result = SwingJavaBuilder.build(this);
	}

	public Person getPerson() {
		return person;
	}
	
	private void cancel() {
		setVisible(false);
	}
	
	@DoInBackground(cancelable=true, indeterminateProgress=false, progressStart=1, progressEnd=100)
	private void save(BackgroundEvent evt) {
		//simulate a long running save to a database
		for(int i = 0; i < 100; i++) {
			//progress indicator
			evt.setProgressValue(i + 1);
			evt.setProgressMessage("" + i + "% done...");
			
			//check if cancel was requested
			if (evt.getCancelStatus() != CancelStatus.REQUESTED) {
				
				//sleep
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				
			} else {
				//cancel requested, let's abort
				evt.setCancelStatus(CancelStatus.COMPLETED);
				break;
			}
		}
	}
	
	//runs after successful save
	private void done() {
		JOptionPane.showMessageDialog(this, "Person data: " + person.toString());
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//activate internationalization
				SwingJavaBuilder.getConfig().addResourceBundle("PersonApp");
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					new PersonApp().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
