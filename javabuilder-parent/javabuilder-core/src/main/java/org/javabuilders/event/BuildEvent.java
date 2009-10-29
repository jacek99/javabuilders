/**
 * 
 */
package org.javabuilders.event;

import java.util.EventObject;

import org.javabuilders.BuildResult;

/**
 * Event object for build events
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class BuildEvent extends EventObject {

	private BuildResult result;
	
	/**
	 * @param source Build source
	 * @param result Build result
	 */
	public BuildEvent(Object source, BuildResult result) {
		super(source);
		this.result = result;
	}

	/**
	 * @return the build result
	 */
	public BuildResult getResult() {
		return result;
	}

}
