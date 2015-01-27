/**
 * 
 */
package org.javabuilders.event;

/**
 * Build event adapter
 * @author Jacek Furmankiewicz
 */
public class BuildAdapter implements BuildListener {

	/* (non-Javadoc)
	 * @see org.javabuilders.event.BuildListener#buildEnded(org.javabuilders.event.BuildEvent)
	 */
	public void buildEnded(BuildEvent evt) {
		//override in descendant
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.event.BuildListener#buildStarted(org.javabuilders.event.BuildEvent)
	 */
	public void buildStarted(BuildEvent evt) {
		//override in descendant
	}

}
