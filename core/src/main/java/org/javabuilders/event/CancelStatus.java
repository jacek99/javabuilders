package org.javabuilders.event;

/**
 * Enumerates the different types of cancel status
 * @author Jacek Furmankiewicz
 */
public enum CancelStatus {
	
	
	FORBIDDEN(-1), NONE(0), REQUESTED(1), PROCESSING(2), COMPLETED(3);
	
	private int status;
	
	private CancelStatus(int status) {
		this.status = status;
	}
	
	/**
	 * @param status Cancel status
	 * @return The numerical value of the cancel status
	 */
	public int getStatus() {
		return this.status;
	}
}
