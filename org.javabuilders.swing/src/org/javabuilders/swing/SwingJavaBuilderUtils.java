/**
 * 
 */
package org.javabuilders.swing;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JWindow;
import javax.swing.KeyStroke;

import org.javabuilders.BuildException;
import org.javabuilders.Builder;
import org.javabuilders.Node;

/**
 * Utility methods for the Swing Builder
 * @author Jacek Furmankiewicz
 */
public class SwingJavaBuilderUtils {

	public static final String MNEMONIC = "&";
	public final static String SEPARATOR = "+";
	private final static String SEPARATOR_ESCAPED = "__";
	
	private static Map<String,Integer> mnemonics = new HashMap<String, Integer>();
	private static Map<String,Integer> actionEvents = new HashMap<String, Integer>();
	private static List<String> functionKeys = new ArrayList<String>();
	
	//initialize list of mnemonics
	private static char[] letters = {'1','2','3','4','5','6','7','8','9','0',
		'q','w','e','r','t','y','u','i','o','p',
		'a','s','d','f','g','h','j','k','l',
		'z','x','c','v','b','n','m'
	};
	
	static {
		for(char letter : letters) {
			mnemonics.put(String.valueOf(letter),(int)letter);
		}
		mnemonics.put("0", KeyEvent.VK_0);
		mnemonics.put("1", KeyEvent.VK_1);
		mnemonics.put("2", KeyEvent.VK_2);
		mnemonics.put("3", KeyEvent.VK_3);
		mnemonics.put("4", KeyEvent.VK_4);
		mnemonics.put("5", KeyEvent.VK_5);
		mnemonics.put("6", KeyEvent.VK_6);
		mnemonics.put("7", KeyEvent.VK_7);
		mnemonics.put("8", KeyEvent.VK_8);
		mnemonics.put("9", KeyEvent.VK_9);
		
		mnemonics.put("q", KeyEvent.VK_Q);
		mnemonics.put("w", KeyEvent.VK_W);
		mnemonics.put("e", KeyEvent.VK_E);
		mnemonics.put("r", KeyEvent.VK_R);
		mnemonics.put("t", KeyEvent.VK_T);
		mnemonics.put("y", KeyEvent.VK_Y);
		mnemonics.put("u", KeyEvent.VK_U);
		mnemonics.put("i", KeyEvent.VK_I);
		mnemonics.put("o", KeyEvent.VK_O);
		mnemonics.put("p", KeyEvent.VK_P);
		mnemonics.put("a", KeyEvent.VK_A);
		mnemonics.put("s", KeyEvent.VK_S);
		mnemonics.put("d", KeyEvent.VK_D);
		mnemonics.put("f", KeyEvent.VK_F);
		mnemonics.put("g", KeyEvent.VK_G);
		mnemonics.put("h", KeyEvent.VK_H);
		mnemonics.put("j", KeyEvent.VK_J);
		mnemonics.put("k", KeyEvent.VK_K);
		mnemonics.put("l", KeyEvent.VK_L);
		mnemonics.put("z", KeyEvent.VK_Z);
		mnemonics.put("x", KeyEvent.VK_X);
		mnemonics.put("c", KeyEvent.VK_C);
		mnemonics.put("v", KeyEvent.VK_V);
		mnemonics.put("b", KeyEvent.VK_B);
		mnemonics.put("n", KeyEvent.VK_N);
		mnemonics.put("m", KeyEvent.VK_M);
		
		actionEvents.put("ctrl", ActionEvent.CTRL_MASK);
		actionEvents.put("alt", ActionEvent.ALT_MASK);
		actionEvents.put("shift", ActionEvent.SHIFT_MASK);
		actionEvents.put("meta", ActionEvent.META_MASK);
		
		functionKeys.add("f1");
		functionKeys.add("f2");
		functionKeys.add("f3");
		functionKeys.add("f4");
		functionKeys.add("f5");
		functionKeys.add("f6");
		functionKeys.add("f7");
		functionKeys.add("f8");
		functionKeys.add("f9");
		functionKeys.add("f10");
		functionKeys.add("f11");
		functionKeys.add("f12");

	}
	
	public static ActionDefinition getActionDefintion(String text) {
		
		KeyStroke acc = null;
		ActionDefinition def = new ActionDefinition();
		
		//handle cases with embedded accelerator
		if (text.indexOf("\t") >= 0) {
			//has embedded accelerator, e.g. "Save\tCtrl+S", like in SWT
			String[] parts = text.split("\t");
			text = parts[0];
			acc = getAccelerator(parts[1]);
			def.setAccelerator(acc);
		}
		
		//handle potential mnemonic
		int index = text.indexOf(MNEMONIC);
		if (index >= 0 && index < (text.length() - 1)) {
			//embedded mnemonic
			String mnemonicLetter = String.valueOf(text.charAt(index + 1)).toLowerCase();
			if (mnemonics.containsKey(mnemonicLetter)) {
				text = text.replace(MNEMONIC, "");
				def.setText(text);
				def.setMnemonic(mnemonics.get(mnemonicLetter));
			} 
		} else {
			def.setText(text);
		}
		
		return def;
	}
	
	
	/**
	 * @param accelerator Accelerator string
	 * @return Accelerator key stroke
	 */
	public static KeyStroke getAccelerator(String accelerator) {
		accelerator = accelerator.toLowerCase();
		accelerator = accelerator.replace(SEPARATOR,SEPARATOR_ESCAPED); //required for split() to work, "+" is a reserver regular expression character
		String[] parts = accelerator.split(SEPARATOR_ESCAPED);
		int keyEvent = 0;
		KeyStroke keyStroke = null;
		
		if (parts.length == 1 && functionKeys.contains(accelerator)) {
			
			keyStroke = KeyStroke.getKeyStroke(accelerator.toUpperCase());
			
		} else if (parts.length >= 2) {
			
			int actionEvent = 0;
			
			for(String part : parts) {
				
				if (mnemonics.containsKey(part)) {
					keyEvent = mnemonics.get(part);
				} else {
					//must be an action event
					if (actionEvents.containsKey(part)) {
						actionEvent += actionEvents.get(part);
					} else {
						throw new BuildException("Invalid accelerator value: {0}. Valid values: ctrl|shift|alt|meta+key, e.g. Ctrl+S",
								accelerator);
					}
				}
				keyStroke = KeyStroke.getKeyStroke(keyEvent, actionEvent);
			}
		}
			
		return keyStroke;
	}
	
	/**
	 * Utility method to find the component by its name
	 * @return Component if found, null if not
	 * @throws BuildException
	 *  
	 */
	public static Component getComponent(Node componentsNode, String name) throws BuildException {
		if (Builder.CONTENT.equals(componentsNode.getKey())) {
			Component component = null;
			for(Node child  : componentsNode.getChildNodes()) {
				if (child.getMainObject() instanceof Component) {
					Component temp = (Component)child.getMainObject();
					
					if (name.equals(temp.getName())) {
						component = temp;
						break;
					}
				}
			}
			return component;
		} else {
			throw new BuildException("componentsNode is not a valid Components node");
		}
	}

	/**
	 * Finds out if a component has already been added to a parent
	 * @param parent
	 * @param component
	 * @return
	 */
	public static boolean isComponentAlreadyAdded(Container parent, Component component) {
		boolean isAdded = false;
		for(int i = 0; i < parent.getComponentCount();i++) {
			if (parent.getComponent(i).equals(component)) {
				isAdded = true;
				break;
			}
		}
		return isAdded;
	}
	
	/**
	 * Finds the top level parent (frame/dialog/window) of any component
	 * @param component Component
	 * @return Parent or null if none found
	 */
	public static Component getTopLevelParent(Object component) {
		Component top = null;
		
		if (component instanceof Component) {
			Component c = (Component)component;

			while (c.getParent() != null) {
				c = c.getParent();
			}
			top = c;
		}
		
		return top;
	}
	
	/**
	 * Gets the parent JComponent, using nothing but the Node hierarchy
	 * @param current Current node
	 * @return JComponent or null if none found
	 */
	public static Container getParentContainer(Node current) {
		Container c = null;
		
		Node parent = current.getParent();
		while (parent != null) {
			Object main = parent.getMainObject();
			if (main instanceof Container) {
				c = (Container) main;
			} else if (main instanceof JFrame) {
				c = ((JFrame)main).getContentPane();
			} else if (main instanceof JDialog) {
				c = ((JDialog)main).getContentPane();
			} else if (main instanceof JWindow) {
				c = ((JWindow)main).getContentPane();
			}
			
			if (c != null) {
				break;
			} else {
				parent = parent.getParent();
			}
		}
		
		return c;
	}
	
	/**
	 * Class that represents an action item's properties
	 */
	public static class ActionDefinition {
		private String text;
		private Integer mnemonic;
		private KeyStroke accelerator;
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Integer getMnemonic() {
			return mnemonic;
		}
		public void setMnemonic(Integer mnemonic) {
			this.mnemonic = mnemonic;
		}
		public KeyStroke getAccelerator() {
			return accelerator;
		}
		public void setAccelerator(KeyStroke accelerator) {
			this.accelerator = accelerator;
		}
	}

}
