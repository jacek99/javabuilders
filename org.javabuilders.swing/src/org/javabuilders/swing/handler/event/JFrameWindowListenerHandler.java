/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

import javax.swing.JFrame;

import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderUtils;
import org.javabuilders.Node;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;

/**
 * Handles JFrame window events/listeners
 * @author Jacek Furmankiewicz
 *
 */
public class JFrameWindowListenerHandler extends WindowListenerHandler {

	private static final JFrameWindowListenerHandler singleton = new JFrameWindowListenerHandler();
	
	/**
	 * @return Singleton
	 */
	public static JFrameWindowListenerHandler getInstance() {return singleton;}
	
	/**
	 * Constructor 
	 */
	private JFrameWindowListenerHandler() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.swing.handler.event.WindowListenerHandler#createListeners(org.javabuilders.Node, org.javabuilders.BuildProcess, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values, org.javabuilders.Values)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void createListeners(final Node node, final BuildProcess process,
			final Values<String, ObjectMethod> stateValues,
			final Values<String, ObjectMethod> windowFocusValues,
			final Values<String, ObjectMethod> windowFocusLostValues,
			final Values<String, ObjectMethod> windowActivatedValues,
			final Values<String, ObjectMethod> windowClosedValues,
			final Values<String, ObjectMethod> windowClosingValues,
			final Values<String, ObjectMethod> windowDeactivatedValues,
			final Values<String, ObjectMethod> windowDeiconifiedValues,
			final Values<String, ObjectMethod> windowIconifiedValues,
			final Values<String, ObjectMethod> windowOpenedValues) {
		
		final JFrame control = (JFrame)node.getMainObject();

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
	 * @see org.javabuilders.swing.handler.event.WindowListenerHandler#getApplicableClass()
	 */
	@Override
	public Class<?> getApplicableClass() {
		return JFrame.class;
	}

}
