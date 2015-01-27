package org.javabuilders.event;

import java.util.EventListener;

/**
 * Build listener interface
 * @author Jacek Furmankiewicz
 */
public interface BuildListener extends EventListener {

	/**
	 * Fired before a build begins
	 * @param evt Event object
	 */
	public void buildStarted(BuildEvent evt);
	
	/**
	 * Fired after a build successfully ends
	 * @param evt Event object
	 */
	public void buildEnded(BuildEvent evt);
}
