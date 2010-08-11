/**
 * 
 */
package org.javabuilders.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.KeyStroke;

import org.javabuilders.BuildException;

/**
 * Default action created by the SwingBuilder. Enhanced AbstractAction with some nicer API
 * @author Jacek Furmankiewicz
 */
public class SwingAction extends AbstractAction {

	/**
	 * Serialization
	 */
	private static final long serialVersionUID = 914022683711186677L;

	private IActionHandler actionHandler = null;
	
	/**
	 * Constructor 
	 */
	public SwingAction() {
	}

	
	/**
	 * @param text Name/text
	 */
	public SwingAction(String text) {
		super(text);
	}

	/**
	 * @param name Name
	 * @param icon Icon
	 */
	public SwingAction(String name, Icon icon) {
		super(name, icon);
	}
	
	/**
	 * @param text Name/text
	 */
	public void setText(String text) {
		putValue(Action.NAME, text);
	}
	
	/**
	 * @return Name/text
	 */
	public String getText() {
		return (String) getValue(Action.NAME);
	}
	
	/**
	 * @param icon Icon
	 */
	public void setIcon(Icon icon) {
		putValue(SMALL_ICON, icon);
	}
	
	/**
	 * @return Action
	 */
	public Icon getIcon() {
		return (Icon) getValue(SMALL_ICON);
	}
	
	/**
	 * @param text Tooltip
	 */
	public void setToolTipText(String text) {
		putValue(SHORT_DESCRIPTION, text);
	}
	
	/**
	 * @return Tooltip
	 */
	public String getToolTipText() {
		return (String) getValue(SMALL_ICON);
	}
	
	/**
	 * @param text Long description
	 */
	public void setLongDescription(String text) {
		putValue(LONG_DESCRIPTION, text);
	}
	
	/**
	 * @return Long description
	 */
	public String getLongDescription() {
		return (String) getValue(LONG_DESCRIPTION);
	}
	
	/**
	 * @param mnemonic Mnemonic
	 */
	public void setMnemonic(Integer mnemonic) {
		putValue(MNEMONIC_KEY, mnemonic);
	}
	
	/**
	 * @return Mnemonic
	 */
	public Integer getMnemonic() {
		return (Integer) getValue(MNEMONIC_KEY);
	}
	
	/**
	 * @param accelerator Accelerator key
	 */
	public void setAccelerator(KeyStroke accelerator) {
		putValue(ACCELERATOR_KEY, accelerator);
	}
	
	/**
	 * @return Accelerator key
	 */
	public KeyStroke getAccelerator() {
		return (KeyStroke) getValue(ACCELERATOR_KEY);
	}
	
	/**
	 * @param action Action command
	 */
	public void setActionCommand(String action) {
		putValue(ACTION_COMMAND_KEY, action);
	}
	
	/**
	 * @return Action command
	 */
	public String getActionCommand() {
		return (String) getValue(ACTION_COMMAND_KEY);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (actionHandler != null) {
			actionHandler.onAction(e);
		} else {
			throw new BuildException("No action handler defined for action: " + getText());
		}
	}

	/**
	 * @return Action handler (called from actionPerformed());
	 */
	public IActionHandler getActionHandler() {
		return actionHandler;
	}

	/**
	 * @param actionHandler Action handler
	 */
	public void setActionHandler(IActionHandler actionHandler) {
		this.actionHandler = actionHandler;
	}
	
	/**
	 * Interface to dispatch the action execution to from actionPerformed()
	 */
	public interface IActionHandler {
		void onAction(ActionEvent e);
	}
}
