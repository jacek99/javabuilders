package org.javabuilders.event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EventObject;

import org.javabuilders.BuildResult;

/**
 * Standard event for methods that run in background
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BackgroundEvent extends EventObject  {
	
	private Boolean isCancelable = false;
	private CancelStatus cancelStatus = CancelStatus.FORBIDDEN;
	private Integer progressStart = 0, progressEnd = 100, progressValue = 0;
	private String progressMessage = "";
	private Object originalEvent = null;
	private Boolean isProgressIndeterminate = false;
	private Boolean isBlocking = true;
	
	//needed for data binding to work
	private PropertyChangeSupport support = null;
	
	/**
	 * @param source Source object
	 */
	public BackgroundEvent(Object source, Object originalEvent, boolean isBlocking, 
			BuildResult result) {
		super(source);
		this.originalEvent = originalEvent;
		this.support = result.getConfig().createPropertyChangeSupport(this);
		
		this.progressMessage = result.getResource("label.processing");
		this.isBlocking = isBlocking;
	}
	
	/**
	 * For binding support
	 * @param listener 
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	
	/**
	 * For binding support
	 * @param propertyName
	 * @param listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}

	/**
	 * For binding support
	 * @param listener 
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	
	/**
	 * For binding support
	 * @param propertyName
	 * @param listener
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}

	
	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#isCancelable()
	 */
	public boolean isCancelable() {
		return isCancelable;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setCancelable(java.lang.Boolean)
	 */
	public synchronized void setCancelable(Boolean isCancelable) {
		Boolean oldValue = this.isCancelable;
		this.isCancelable = isCancelable;
		support.firePropertyChange("cancelable", oldValue, this.isCancelable);
		
		if (isCancelable) {
			if (cancelStatus == CancelStatus.NONE) {
				cancelStatus = CancelStatus.FORBIDDEN;
			}
		} else {
			cancelStatus = CancelStatus.FORBIDDEN;
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getCancelStatus()
	 */
	public CancelStatus getCancelStatus() {
		return cancelStatus;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setCancelStatus(org.javabuilders.event.CancelStatus)
	 */
	public synchronized void setCancelStatus(CancelStatus cancelStatus) {
		if (cancelStatus != CancelStatus.FORBIDDEN) {
			CancelStatus oldValue = this.cancelStatus;
			this.cancelStatus = cancelStatus;
			support.firePropertyChange("cancelStatus", oldValue, this.cancelStatus);
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getProgressStart()
	 */
	public Integer getProgressStart() {
		return progressStart;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setProgressStart(java.lang.Integer)
	 */
	public synchronized void setProgressStart(Integer progressStart) {
		Integer oldValue = this.progressStart;
		this.progressStart = progressStart;
		support.firePropertyChange("progressStart", oldValue, this.progressStart);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getProgressEnd()
	 */
	public Integer getProgressEnd() {
		return progressEnd;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setProgressEnd(java.lang.Integer)
	 */
	public synchronized void setProgressEnd(Integer progressEnd) {
		Integer oldValue = this.progressEnd;
		this.progressEnd = progressEnd;
		support.firePropertyChange("progressEnd",oldValue,progressEnd);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getProgressValue()
	 */
	public Integer getProgressValue() {
		return progressValue;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setProgressValue(java.lang.Integer)
	 */
	public synchronized void setProgressValue(Integer currentProgressValue) {
		Integer oldValue = this.progressValue;
		this.progressValue = currentProgressValue;
		support.firePropertyChange("progressValue", oldValue, progressValue);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getProgressMessage()
	 */
	public String getProgressMessage() {
		return progressMessage;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setProgressMessage(java.lang.String)
	 */
	public synchronized void setProgressMessage(String progressMessage) {
		String oldValue = this.progressMessage;
		this.progressMessage = progressMessage;
		support.firePropertyChange("progressMessage",oldValue,progressMessage);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getOriginalEvent()
	 */
	public Object getOriginalEvent() {
		return originalEvent;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#getProgressIndeterminate()
	 */
	public Boolean getProgressIndeterminate() {
		return isProgressIndeterminate;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#setProgressIndeterminate(java.lang.Boolean)
	 */
	public synchronized void setProgressIndeterminate(Boolean isProgressIndeterminate) {
		Boolean oldValue = this.isProgressIndeterminate;
		this.isProgressIndeterminate = isProgressIndeterminate;
		support.firePropertyChange("progressIndeterminate", oldValue, isProgressIndeterminate);		
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.IBackgroundEvent#isBlocking()
	 */
	public boolean isBlocking() {
		return isBlocking;
	}

}
