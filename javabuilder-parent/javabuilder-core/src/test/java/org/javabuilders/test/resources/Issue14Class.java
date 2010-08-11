package org.javabuilders.test.resources;

import javax.swing.JComponent;

/**
 * Issue 14
 */
@SuppressWarnings("serial") 
public class Issue14Class extends JComponent {
	
	public Issue14Class() {}
	
	public Issue14Class(String constraint) {
		setConstraint(constraint);
	}
	
	private String constraint = "";

	/**
	 * @return the constraint
	 */
	public String getConstraint() {
		return constraint;
	}

	/**
	 * @param constraint the constraint to set
	 */
	public void setConstraint(String constraint) {
		this.constraint = constraint;
	}
	
}