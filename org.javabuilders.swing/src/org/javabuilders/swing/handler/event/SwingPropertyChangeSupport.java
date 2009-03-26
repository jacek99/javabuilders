package org.javabuilders.swing.handler.event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import javax.swing.SwingUtilities;

/**
 * PropertyChangeSupport that always fires the events on the Swing EDT thread
 * @author Jacek Furmankiewicz
 */
public class SwingPropertyChangeSupport extends PropertyChangeSupport {

	/**
	 * Serialization support
	 */
	private static final long serialVersionUID = -7281068955945686832L;

	/**
	 * Constructor
	 * @param sourceBean
	 */
	public SwingPropertyChangeSupport(Object sourceBean) {
		super(sourceBean);
	}

	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(java.beans.PropertyChangeEvent)
	 */
	public void firePropertyChange(final PropertyChangeEvent e) {
        //make sure the property change events always fire on the Swing EDT thread
		if (SwingUtilities.isEventDispatchThread()) {
            super.firePropertyChange(e);
        } else {
            Runnable edtPropertyChange = new Runnable() {
                public void run() {
                    firePropertyChange(e);
                }
            };
            SwingUtilities.invokeLater(edtPropertyChange);
        }
    }

}
