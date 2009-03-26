package org.javabuilders.event;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;

/**
 * Standard event for methods that run in background
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BackgroundEvent extends EventObject {
	
	@SuppressWarnings("unused")
	private final static Logger logger = Logger.getLogger(BackgroundEvent.class.getSimpleName());

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
			BuilderConfig config) {
		super(source);
		this.originalEvent = originalEvent;
		this.support = config.createPropertyChangeSupport(this);
		
		ResourceBundle bundle = Builder.getResourceBundle();
		this.progressMessage = bundle.getString("label.processing");
		this.isBlocking = isBlocking;
		
		support.addPropertyChangeListener("test",null);
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

	
	/**
	 * @return
	 */
	public boolean isCancelable() {
		return isCancelable;
	}

	/**
	 * @param isCancelable
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

	/**
	 * @return
	 */
	public CancelStatus getCancelStatus() {
		return cancelStatus;
	}

	/**
	 * @param cancelStatus
	 */
	public synchronized void setCancelStatus(CancelStatus cancelStatus) {
		if (cancelStatus != CancelStatus.FORBIDDEN) {
			CancelStatus oldValue = this.cancelStatus;
			this.cancelStatus = cancelStatus;
			support.firePropertyChange("cancelStatus", oldValue, this.cancelStatus);
		}
	}

	/**
	 * @return
	 */
	public Integer getProgressStart() {
		return progressStart;
	}

	/**
	 * @param progressStart
	 */
	public synchronized void setProgressStart(Integer progressStart) {
		Integer oldValue = this.progressStart;
		this.progressStart = progressStart;
		support.firePropertyChange("progressStart", oldValue, this.progressStart);
	}

	/**
	 * @return
	 */
	public Integer getProgressEnd() {
		return progressEnd;
	}

	/**
	 * @param progressEnd
	 */
	public synchronized void setProgressEnd(Integer progressEnd) {
		Integer oldValue = this.progressEnd;
		this.progressEnd = progressEnd;
		support.firePropertyChange("progressEnd",oldValue,progressEnd);
	}

	/**
	 * @return
	 */
	public Integer getProgressValue() {
		return progressValue;
	}

	/**
	 * @param currentProgressValue
	 */
	public synchronized void setProgressValue(Integer currentProgressValue) {
		Integer oldValue = this.progressValue;
		this.progressValue = currentProgressValue;
		support.firePropertyChange("progressValue", oldValue, progressValue);
	}

	/**
	 * @return
	 */
	public String getProgressMessage() {
		return progressMessage;
	}

	/**
	 * @param progressMessage
	 */
	public synchronized void setProgressMessage(String progressMessage) {
		String oldValue = this.progressMessage;
		this.progressMessage = progressMessage;
		support.firePropertyChange("progressMessage",oldValue,progressMessage);
	}

	/**
	 * @return Original event object
	 */
	public Object getOriginalEvent() {
		return originalEvent;
	}

	/**
	 * @return
	 */
	public Boolean isProgressIndeterminate() {
		return isProgressIndeterminate;
	}

	/**
	 * @param isProgressIndeterminate
	 */
	public synchronized void setProgressIndeterminate(Boolean isProgressIndeterminate) {
		Boolean oldValue = this.isProgressIndeterminate;
		this.isProgressIndeterminate = isProgressIndeterminate;
		support.firePropertyChange("progressIndeterminate", oldValue, isProgressIndeterminate);		
	}

	/**
	 * @return If is blocking or not
	 */
	public boolean isBlocking() {
		return isBlocking;
	}

}
