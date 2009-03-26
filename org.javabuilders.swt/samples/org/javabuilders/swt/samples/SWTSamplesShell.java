/**
 * 
 */
package org.javabuilders.swt.samples;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.javabuilders.BuildResult;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.swt.SWTBuilder;

/**
 * @author Jacek Furmankiewcz
 * 
 */
public class SWTSamplesShell  {
	
	private Shell shell;
	private MenuItem saveMenu;
	private String yaml;
	private Text source;
	
	private WidgetComposite widgets;
	private SashComposite sash;
	
	private PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
	private BuildResult result = SWTBuilder.build(this);
	
	private SWTSamplesShell() throws Exception {
		InputStream stream = SWTSamplesShell.class.getResourceAsStream(this.getClass().getSimpleName() + ".yaml");
		BufferedReader in = new BufferedReader(new InputStreamReader(stream));
		StringBuilder builder = new StringBuilder();
		String line = in.readLine();
		while (line != null) {
			builder.append(line).append("\n");
			line = in.readLine();
		}
		setYaml(builder.toString());
	}
	
	public void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}
	
	@DoInBackground
	private void save(BackgroundEvent evt) {
		evt.setProgressMessage("Saving some data from SWT on a background thread....");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {}
	}
	
	public void setYaml(String yaml) {
		String old = this.yaml;
		this.yaml = yaml;
		changeSupport.firePropertyChange("yaml",old, yaml);
	}

	public String getYaml() {
		return yaml;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			
			final Display display = Display.getDefault();
			Realm.runWithDefault(SWTObservables.getRealm(display), new Runnable() {
				public void run() {
					
					try {
						Shell shell = new SWTSamplesShell().shell;
						shell.open();
						
						while (!shell.isDisposed())
							if (!display.readAndDispatch())
								display.sleep();
						display.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					} 
				}
			});
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
