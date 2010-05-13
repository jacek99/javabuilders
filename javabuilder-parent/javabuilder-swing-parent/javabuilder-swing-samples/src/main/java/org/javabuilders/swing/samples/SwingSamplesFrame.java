package org.javabuilders.swing.samples;

import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;

import org.javabuilders.BuildResult;
import org.javabuilders.event.BuildAdapter;
import org.javabuilders.event.BuildEvent;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.plugin.glazedlists.SwingGlazedListsConfig;

@SuppressWarnings({ "unused", "serial" })
public class SwingSamplesFrame extends JFrame {
	
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private String yaml = getFileContent(this.getClass(),"yaml");
	
	private ComponentsPanel componentsPanel;
	private FlowLayoutPanel flowLayoutPanel;
	private CardLayoutPanel cardLayoutPanel;
	private MigLayoutPanel1 migLayoutPanel1;
	private MigLayoutPanel2 migLayoutPanel2;
	private MigLayoutPanel3 migLayoutPanel3;
	private EventsPanel eventsPanel;
	private BorderPanel borderPanel;
	private LayoutManagerShowdownChallenge layoutChallengePanel;
	private ValidatorsPanel validatorsPanel;
	private UserSubmissionsPanel usersPanel;
	private FormattedInputPanel formattedInputPanel;
	private ListBindingPanel listPanel;
	private JTextArea eventsLog;
	private GlazedListsSamplePanel glazedListsPanel;
	private JDesktopPanePanel desktopPanePanel;
	private AlignmentAndSizesPanel alignmentSizesPanel;
	
	//usually the last variable in the list of instance variables...
	private BuildResult result;
	
	public SwingSamplesFrame() throws Exception {
		long start = System.currentTimeMillis(); 
		result = SwingJavaBuilder.build(this);
		long end = System.currentTimeMillis();
		
		System.out.println("BUILD TIME: " + (end - start));
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	
	private void onFileNew() {
		JOptionPane.showMessageDialog(this,"File.New 'onAction=onFileNew' was invoked and executed the frame's private onFileNew() method");
	}
	
	private void onFileOpen() {
		JOptionPane.showInputDialog("Open a file:");
	}

	
	private void onHelpAbout() {
		JOptionPane.showMessageDialog(this,"Help.About 'onAction=onHelpAbout' was invoked and executed the frame's private onHelpAbout() method");
	}
	
	private void onSave() {
		JOptionPane.showMessageDialog(this,"Save!");
	}
	
	private void exit() {
		setVisible(false);
		dispose();
	}
	
	private void option1(ActionEvent evt) {
		JOptionPane.showMessageDialog(this,"Option1 'onAction=option1' was invoked and executed the frame's private void option1() method");
	}
	
	private void onWindowEvent(WindowEvent evt) {
		if (eventsLog != null) {
			eventsLog.setText(evt.paramString() + "\n" + eventsLog.getText());
		}
	}
	
	private void onTabChanged(ChangeEvent evt) {
		if (eventsLog != null) {
			eventsLog.setText("TAB CHANGE: " + evt.getSource() + "\n" + eventsLog.getText());
		}
	}
	
	private void copy(ActionEvent evt) {
		JOptionPane.showMessageDialog(this, "Copy invoked!");
	}
	
	private void paste(ActionEvent evt) {
		JOptionPane.showMessageDialog(this, "Paste invoked!");
	}
	
	
    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		try {
            			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            			//UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            		} catch (Exception ex) {
            			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            		}
            		
            		//register global resource bundle
            		SwingGlazedListsConfig.init();
            		SwingJavaBuilder.getConfig().addResourceBundle("org.javabuilders.swing.samples.SwingSamplesResources");
            		
            		//event listeners
            		SwingJavaBuilder.getConfig().addBuildListener(new BuildAdapter() {
            			@Override
            			public void buildStarted(BuildEvent evt) {
            				//System.out.println(("Build started from caller: " + evt.getSource()));
            			}
            			@Override
            			public void buildEnded(BuildEvent evt) {
            				//System.out.println(("Build ended for root object: " + evt.getResult().getRoot()));
            			}
            		});
            		
            		//set up global formats
            		//SwingJavaBuilder.getConfig().addGlobalVariable("$${date}", DateFormat.getInstance());
            		//SwingJavaBuilder.getConfig().addGlobalVariable("$${time}", DateFormat.getTimeInstance());
            		
            		new SwingSamplesFrame().setVisible(true);
            	} catch (Exception ex) {
            		ex.printStackTrace(System.err);
            	}
            }
        });
    }
    
    public static String getFileContent(Class<?> baseClass, String extension) throws IOException {
    	StringBuilder builder = new StringBuilder();
    	
    	InputStream is = baseClass.getResourceAsStream(baseClass.getSimpleName() + "." + extension);
    	InputStreamReader isr = new InputStreamReader(is);
    	BufferedReader rdr = new BufferedReader(isr);
    	
    	String line = "";
    	while ((line = rdr.readLine()) != null) {
    		builder.append(line).append("\n");
    	}
    	
    	return builder.toString();
    }

	public String getYaml() {
		return yaml;
	}


}
