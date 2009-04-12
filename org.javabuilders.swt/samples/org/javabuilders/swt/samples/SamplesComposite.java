package org.javabuilders.swt.samples;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.javabuilders.BuilderUtils;
import org.javabuilders.swt.worker.SWTPropertyChangeSupport;

public class SamplesComposite extends Composite {

	private String yaml = null;
	private PropertyChangeSupport support;
	
	public SamplesComposite(Composite c, int style) {
		super(c,style);
		try {
			setYaml(BuilderUtils.getYamlContent(getClass()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		support = new SWTPropertyChangeSupport(this,getDisplay());
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	/**
	 * @return the yaml
	 */
	public String getYaml() {
		return yaml;
	}
	
	public void setYaml(String yaml) {
		this.yaml = yaml;
	}


}
