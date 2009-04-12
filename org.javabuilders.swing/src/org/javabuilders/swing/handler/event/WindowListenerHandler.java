/**
 * 
 */
package org.javabuilders.swing.handler.event;

import static org.javabuilders.swing.SwingJavaBuilder.*;

import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
/**
 * Handles all window events/listeners
 * @author Jacek Furmankiewicz
 *
 */
public class WindowListenerHandler extends AbstractPropertyHandler implements IPropertyList {

	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(WindowEvent.class);
	
	private static final WindowListenerHandler singleton = new WindowListenerHandler();
	
	/**
	 * @return Singleton
	 */
	public static WindowListenerHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	protected WindowListenerHandler() {
		super(ON_STATE_CHANGED, ON_WINDOW_FOCUS, ON_WINDOW_FOCUS_LOST,ON_WINDOW_ACTIVATED,
				ON_WINDOW_CLOSED, ON_WINDOW_CLOSING, ON_WINDOW_DEACTIVATED, ON_WINDOW_DEICONIFIED,
				ON_WINDOW_ICONIFIED,ON_WINDOW_OPENED);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(BuilderConfig config, final BuildProcess process, Node node,
			String key) throws BuildException {
		
		Values<String,ObjectMethod> stateValues = (Values<String, ObjectMethod>) 
			node.getProperty(ON_STATE_CHANGED);
		
		Values<String,ObjectMethod> windowFocusValues = (Values<String, ObjectMethod>) 
			node.getProperty(ON_WINDOW_FOCUS);
		Values<String,ObjectMethod> windowFocusLostValues = (Values<String, ObjectMethod>) 
			node.getProperty(ON_WINDOW_FOCUS_LOST);
		
		Values<String,ObjectMethod> windowActivatedValues = (Values<String, ObjectMethod>) 
			node.getProperty(ON_WINDOW_ACTIVATED);
		Values<String,ObjectMethod> windowClosedValues = (Values<String, ObjectMethod>) 
		node.getProperty(ON_WINDOW_CLOSED);
		Values<String,ObjectMethod> windowClosingValues = (Values<String, ObjectMethod>) 
		node.getProperty(ON_WINDOW_CLOSING);
		Values<String,ObjectMethod> windowDeactivatedValues = (Values<String, ObjectMethod>) 
		node.getProperty(ON_WINDOW_DEACTIVATED);
		Values<String,ObjectMethod> windowDeiconifiedValues = (Values<String, ObjectMethod>) 
		node.getProperty(ON_WINDOW_DEICONIFIED);
		Values<String,ObjectMethod> windowIconifiedValues = (Values<String, ObjectMethod>) 
		node.getProperty(ON_WINDOW_ICONIFIED);
		Values<String,ObjectMethod> windowOpenedValues = (Values<String, ObjectMethod>) 
		node.getProperty(ON_WINDOW_OPENED);

		createListeners(node, process, stateValues, windowFocusValues, windowFocusLostValues,
				windowActivatedValues, windowClosedValues, windowClosingValues, 
				windowDeactivatedValues, windowDeiconifiedValues, windowIconifiedValues,
				windowOpenedValues);
	}
	
	//overriden in descendants if the need arises
	@SuppressWarnings("unchecked")
	protected void createListeners(final Node node, final BuildProcess process,
			final Values<String,ObjectMethod> stateValues, final Values<String,ObjectMethod> windowFocusValues,
			final Values<String,ObjectMethod> windowFocusLostValues, 
			final Values<String,ObjectMethod> windowActivatedValues,
			final Values<String,ObjectMethod> windowClosedValues,
			final Values<String,ObjectMethod> windowClosingValues,
			final Values<String,ObjectMethod> windowDeactivatedValues,
			final Values<String,ObjectMethod> windowDeiconifiedValues,
			final Values<String,ObjectMethod> windowIconifiedValues,
			final Values<String,ObjectMethod> windowOpenedValues) {
		
		final Window control = (Window)node.getMainObject();

		if (stateValues != null) {
			control.addWindowStateListener(new WindowStateListener() {
				public void windowStateChanged(WindowEvent e) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, stateValues.values(), e);
				}
			});
		}
		
		//window focus
		if (BuilderUtils.isListenerNeeded(windowFocusValues, windowFocusLostValues)) {
			control.addWindowFocusListener(new WindowFocusListener() {
				public void windowGainedFocus(WindowEvent e) {
					if (windowFocusValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowFocusValues.values(), e);
					}
				}
				public void windowLostFocus(WindowEvent e) {
					if (windowFocusLostValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowFocusLostValues.values(), e);
					}
				}
			});
		}
		
		//windows listener
		if (BuilderUtils.isListenerNeeded(windowActivatedValues, windowClosedValues, windowClosingValues,
				windowDeactivatedValues, windowDeiconifiedValues, windowIconifiedValues, windowOpenedValues)) {
			
			control.addWindowListener(new WindowListener() {
				public void windowActivated(WindowEvent e) {
					if (windowActivatedValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowActivatedValues.values(), e);
					}
				}

				public void windowClosed(WindowEvent e) {
					if (windowClosedValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowClosedValues.values(), e);
					}
				}

				public void windowClosing(WindowEvent e) {
					if (windowClosingValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowClosingValues.values(), e);
					}
				}

				public void windowDeactivated(WindowEvent e) {
					if (windowDeactivatedValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowDeactivatedValues.values(), e);
					}
				}

				public void windowDeiconified(WindowEvent e) {
					if (windowDeiconifiedValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowDeiconifiedValues.values(), e);
					}
				}

				public void windowIconified(WindowEvent e) {
					if (windowIconifiedValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowIconifiedValues.values(), e);
					}
				}

				public void windowOpened(WindowEvent e) {
					if (windowOpenedValues != null) {
						BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), control, 
								windowOpenedValues.values(), e);
					}
				}
			});
		}

	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IApplicable#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Window.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}

}
