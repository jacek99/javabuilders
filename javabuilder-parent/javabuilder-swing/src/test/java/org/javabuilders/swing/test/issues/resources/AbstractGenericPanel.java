package org.javabuilders.swing.test.issues.resources;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class AbstractGenericPanel extends JPanel {
	protected JTextField genericTextField;

	private Dimension dimension = null;
	
	public JTextField getGenericTextField() {
		return genericTextField;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(Dimension dimension) {
		this.dimension = dimension;
	}

}
