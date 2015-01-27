package org.javabuilders.swing.handler.property;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.ActionMap;
import javax.swing.ComponentInputMap;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ActionMapUIResource;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingJavaBuilderUtils;
import org.javabuilders.swing.SwingJavaBuilderUtils.ActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Text handler for all buttons (takes care of mnemonics and acelerators as well)
 * @author Jacek Furmankiewicz
 *
 */
public class AbstractButtonTextHandler extends AbstractPropertyHandler {

	private static final Logger LOG = LoggerFactory.getLogger(AbstractButtonTextHandler.class);
	
	public static final String TEXT = "text";
	
	public AbstractButtonTextHandler() {
		super(TEXT);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("serial")
	public void handle(BuilderConfig config, BuildProcess process, Node node, String key) throws BuildException {
		String text = String.valueOf(node.getProperties().get(key));
		final AbstractButton button = (AbstractButton)node.getMainObject();
		
		ActionDefinition def = SwingJavaBuilderUtils.getActionDefintion(text);
		button.setText(def.getText());
		if (def.getMnemonic() != null) {
			button.setMnemonic(def.getMnemonic());
		}
		//accelerators can be called on JMenuItems only, but on JMenu (throws Swing exception)
		if (def.getAccelerator() != null) {
			if (button instanceof JMenuItem && !(button instanceof JMenu)) {
				JMenuItem menuItem = (JMenuItem) button;
				menuItem.setAccelerator(def.getAccelerator());
			} else {
				//special logic to support accelerator on JButton (issue 86)
				if (button instanceof JButton) {
					
					InputMap keyMap = new ComponentInputMap(button);
					keyMap.put(def.getAccelerator(), "action");

					ActionMap actionMap = new ActionMapUIResource();
					actionMap.put("action", new AbstractAction() {
						@Override
						public void actionPerformed(ActionEvent e) {
							
							ActionEvent evt = new ActionEvent(button, ActionEvent.ACTION_PERFORMED, "action");
							
							//fire event listeners
							if (button.getActionListeners() != null) {
								for(ActionListener listener : button.getActionListeners()) {
									listener.actionPerformed(evt);
								}
							}
							
							//handle existing defined action
							if (button.getAction() != null && !button.getAction().equals(this)) {
								button.getAction().actionPerformed(evt);
							}
							
						}
					});

					SwingUtilities.replaceUIActionMap(button, actionMap);
					SwingUtilities.replaceUIInputMap(button, JComponent.WHEN_IN_FOCUSED_WINDOW, keyMap);
					
					//add accelerator info to tooltip if not used
					if (button.getToolTipText() == null || button.getToolTipText().length() == 0) {
						button.setToolTipText(def.getAcceleratorText());
					} 
					
				} else {
					LOG.warn("Ignored accelerator: can only be set on JMenuItem(s) or JButton(s): " + text);
				}

			}
		}
		
		
		
	}

}
