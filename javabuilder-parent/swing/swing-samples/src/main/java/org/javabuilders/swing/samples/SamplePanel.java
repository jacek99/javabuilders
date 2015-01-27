package org.javabuilders.swing.samples;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.JPanel;

import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.util.BuilderUtils;

/**
 * A JPanel with binding support integrated
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class SamplePanel extends JPanel {

	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private String yaml = BuilderUtils.getYamlContent(SwingJavaBuilder.getConfig(), this.getClass());

	public SamplePanel() throws Exception {}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (support != null) {
			support.addPropertyChangeListener(listener);
		}
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (support != null) {
			support.removePropertyChangeListener(listener);
		}
	}
	
	/**
	 * @return YAML content
	 */
	public String getYaml() {return yaml;}
	
}
