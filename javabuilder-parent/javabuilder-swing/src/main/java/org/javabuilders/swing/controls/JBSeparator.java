/**
 * 
 */
package org.javabuilders.swing.controls;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

/**
 * An enhanced version of a JSeaprator that displays a title before the separator, e.g.<br/>
 * Title ------------------------------------------<br/>
 * This can be used instead of JPanels with a Title Border to indicate grouping, which simplifies the layout
 * @author Jacek Furmankiewicz
 *
 */
public class JBSeparator extends JPanel {

	/**
	 * Serialization support
	 */
	private static final long serialVersionUID = -5286756879004703880L;
	private JLabel label = new JLabel();
	
	/**
	 * Constructor
	 */
	public JBSeparator() {
		super(new MigLayout("insets 0","[8px][shrink][grow]"));
		
		add(new JSeparator(SwingConstants.HORIZONTAL),"growx, aligny center");
		add(label);
		add(new JSeparator(SwingConstants.HORIZONTAL),"growx, aligny center");
	}
	
	/**
	 * @param text Text to set
	 */
	public void setText(String text) {
		label.setText(text);
	}
	
	/**
	 * @return Text
	 */
	public String getText() {
		return label.getText();
	}
	
	


}
