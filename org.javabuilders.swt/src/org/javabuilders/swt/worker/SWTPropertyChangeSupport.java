package org.javabuilders.swt.worker;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

import org.eclipse.swt.widgets.Display;

/**
 * SWT Property change support that fires events on the event dispatching thread only
 * @author Jacek Furmankiewicz
 *
 */
public class SWTPropertyChangeSupport extends PropertyChangeSupport {

	/**
	 * Serialization 
	 */
	private static final long serialVersionUID = -1913653933006243847L;

	private Display display;
	
	/**
	 * @param sourceBean
	 */
	public SWTPropertyChangeSupport(Object sourceBean, Display display) {
		super(sourceBean);
		this.display = display;
	}
	
	/* (non-Javadoc)
	 * @see java.beans.PropertyChangeSupport#firePropertyChange(java.beans.PropertyChangeEvent)
	 */
	@Override
	public void firePropertyChange(final PropertyChangeEvent evt) {
		if (Thread.currentThread().equals(this.display.getThread())) {
            //already on EDT thread
            super.firePropertyChange(evt);
        } else {
            Runnable doFirePropertyChange = new Runnable() {
                public void run() {
                    firePropertyChange(evt);
                }
            };
            //fire on EDT thread
            this.display.asyncExec(doFirePropertyChange);
        }
	}

}
