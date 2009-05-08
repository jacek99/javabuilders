/**
 * 
 */
package org.javabuilders.swing.handler.event;

import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_CLICKED;
import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_DOUBLE_CLICKED;
import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_ENTERED;
import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_EXITED;
import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_PRESSED;
import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_RELEASED;
import static org.javabuilders.swing.SwingBuilder.ON_MOUSE_RIGHT_CLICKED;
import static org.javabuilders.swing.SwingBuilder.POPUP_MENU;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPopupMenu;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuildResult;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handler for all the onMouse* event that simplifies creating MouseListeners
 * @author Jacek Furmankiewicz
 */
public class ComponentMouseListenerHandler extends AbstractPropertyHandler implements IPropertyList{

	private static final ComponentMouseListenerHandler singleton = new ComponentMouseListenerHandler();

	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(MouseEvent.class);
	
	/**
	 * @return Singleton
	 */
	public static ComponentMouseListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	public ComponentMouseListenerHandler() {
		super(ON_MOUSE_CLICKED, ON_MOUSE_DOUBLE_CLICKED, ON_MOUSE_RIGHT_CLICKED, ON_MOUSE_ENTERED, 
				ON_MOUSE_EXITED, ON_MOUSE_PRESSED, ON_MOUSE_RELEASED, POPUP_MENU);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {
		
		final Values<String,ObjectMethod> onMouseClicked = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_CLICKED);
		final Values<String,ObjectMethod> onMouseDoubleClicked = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_DOUBLE_CLICKED);
		final Values<String,ObjectMethod> onMouseEntered = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_ENTERED);
		final Values<String,ObjectMethod> onMouseExited = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_EXITED);
		final Values<String,ObjectMethod> onMousePressed = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_PRESSED);
		final Values<String,ObjectMethod> onMouseReleased = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_RELEASED);
		final Values<String,ObjectMethod> onMouseRightClicked = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_RIGHT_CLICKED);
		final String popupMenu = node.getStringProperty(POPUP_MENU);
		
		//validate popup menu is valid, if specified
		if (popupMenu != null) {
			Object menu = process.getBuildResult().get(popupMenu);
			if (menu == null) {
				throw new BuildException("Unable to find an instance of JPopupMenu for {0}",popupMenu);
			} else if (!(menu instanceof JPopupMenu)) {
				throw new BuildException("Unable to find an instance of JPopupMenu for {0}: {0} is of type {1} instead",
						popupMenu, menu.getClass());
			}
		}

		final Component component = (Component)node.getMainObject();
		component.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						if (onMouseDoubleClicked != null) {
							BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMouseDoubleClicked.values(), e);
						}
					} else {
						if (onMouseClicked != null) {
							BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMouseClicked.values(), e);
						}
					}
				} else if (e.getButton() == MouseEvent.BUTTON3 && onMouseRightClicked != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMouseRightClicked.values(), e);
				}
			}

			public void mouseEntered(MouseEvent e) {
				if (onMouseEntered != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMouseEntered.values(), e);
				}
			}

			public void mouseExited(MouseEvent e) {
				if (onMouseExited != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMouseExited.values(), e);
				}
			}

			public void mousePressed(MouseEvent e) {
				if (onMousePressed != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMousePressed.values(), e);
				}
				if (e.isPopupTrigger()) {
					showPopup(process.getBuildResult(), component, e, popupMenu);
				}
			}

			public void mouseReleased(MouseEvent e) {
				if (onMouseReleased != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMouseReleased.values(), e);
				}
				if (e.isPopupTrigger()) {
					showPopup(process.getBuildResult(), component, e, popupMenu);
				}
			}
			
		});
	}
	
	//shows the popup
	private void showPopup(BuildResult result, Component component, MouseEvent e, String popupMenu) {
		if (popupMenu != null) {
			Object menu = result.get(popupMenu);
			if (menu instanceof JPopupMenu) {
				((JPopupMenu)menu).show(component, e.getX(), e.getY());
			} else {
				throw new BuildException("Unable to find JPopupMenu corresponding to: {0}",popupMenu);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Component.class;
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		if (POPUP_MENU.equals(propertyName)) {
			return false;
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

}
