package org.javabuilders.event;

import java.util.EventListener;

import org.javabuilders.BuildResult;

public interface BackgroundEventListener extends EventListener {

	/**
	 * Fired before a background task starts
	 * @param evt
	 */
	public void backgroundTaskStarted(BuildResult r, BackgroundEvent evt);
	
	/**
	 * Fired after a background task ends
	 * @param evt Event object
	 */
	public void backgroundTaskEnded(BuildResult r, BackgroundEvent evt);
	
}
