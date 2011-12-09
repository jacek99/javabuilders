/**
 * 
 */
package org.javabuilders.swing;

import java.util.ResourceBundle;

import javax.swing.UIManager;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.BuilderUtils.OperatingSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Swing Builder
 * 
 * @author Jacek Furmankiewicz
 */
public class SwingJavaBuilder  {

	private static SwingJavaBuilderConfig config = new SwingJavaBuilderConfig();
	private static final Logger LOG = LoggerFactory.getLogger(SwingJavaBuilder.class);
	
	public final static String VGAP = "vgap";
	public final static String HGAP = "hgap";
	
	public final static String DEFAULT_LAYOUT_MANAGER = "__DefaultSwingLayoutManager__";
	public final static String TITLE = "title";
	public final static String TEXT = "text";
	public final static String TOOL_TIP_TEXT = "toolTipText";
	public final static String ACCELERATOR = "accelerator";
	public final static String ICON = "icon";
	public final static String ACTION = "action";
	
	public final static String LAYOUT_DATA = "layoutData";
	
	public final static String POPUP_MENU = "popupMenu";

	/**
	 * The standard property name used to indicate on onKeyPressed event
	 */
	public final static String ON_KEY_PRESSED = "onKeyPressed";
	public final static String ON_KEY_RELEASED = "onKeyReleased";
	public final static String ON_KEY_TYPED = "onKeyTyped";
	
	public final static String ON_MOUSE_CLICKED = "onMouseClicked";
	public final static String ON_MOUSE_DOUBLE_CLICKED = "onMouseDoubleClicked";
	public final static String ON_MOUSE_RIGHT_CLICKED = "onMouseRightClicked";
	public final static String ON_MOUSE_ENTERED = "onMouseEntered";
	public final static String ON_MOUSE_EXITED ="onMouseExited";
	public final static String ON_MOUSE_PRESSED ="onMousePressed";
	public final static String ON_MOUSE_RELEASED = "onMouseReleased";
	
	public final static String ON_STATE_CHANGED = "onStateChanged";
	public final static String ON_WINDOW_FOCUS = "onWindowFocus";
	public final static String ON_WINDOW_FOCUS_LOST = "onWindowFocusLost";
	
	public final static String ON_WINDOW_ACTIVATED = "onWindowActivated";
	public final static String ON_WINDOW_CLOSED = "onWindowClosed";
	public final static String ON_WINDOW_CLOSING = "onWindowClosing";
	public final static String ON_WINDOW_DEACTIVATED = "onWindowDeactivated";
	public final static String ON_WINDOW_DEICONIFIED = "onWindowDeiconified";
	public final static String ON_WINDOW_ICONIFIED = "onWindowIconified";
	public final static String ON_WINDOW_OPENED = "onWindowOpened";
	

	/**
	 * Property used to flag nodes that should not be processed in any way for layout management (e.g. JSPlitPane)
	 */
	public final static String PROPERTY_IGNORE_LAYOUT_MANAGER = "ignoreLayoutManager";
	
	/**
	 * Returns the standard SwingBuilder configuration
	 * @return
	 */
	public static SwingJavaBuilderConfig getConfig() {
		return config;
	}
	
	/**
	 * Main Swing Builder methods
	 * @param caller The calling object (it automatically assumes a YAML file with the same name as the objects class (e.g. "MyFrame.java" needs "MyFrame.yaml" exists in the same package)
	 * @return Build result
	 */
	public static BuildResult build(Object caller)  {
		return Builder.build(getConfig(),caller);
	}
	
	/**
	 * Main Swing Builder methods
	 * @param caller The calling object (it automatically assumes a YAML file with the same name as the objects class (e.g. "MyFrame.java" needs "MyFrame.yaml" exists in the same package)
	 * @param bundles Optional list of bundles
	 * @return Build result
	 */
	public static BuildResult build(Object caller, ResourceBundle...bundles)  {
		return Builder.build(getConfig(),caller,bundles);
	}
	
	/**
	 * SwingBuilder method that accepts passed in YAML instead of looking for a separate file
	 * @param caller The calling object 
	 * @param bundles Optional list of bundles
	 * @param yaml Valid YAML content
	 * @return Buld result
	 */
	public static BuildResult build(Object caller, String yaml, ResourceBundle...bundles)  {
		return Builder.buildFromString(getConfig(),caller, yaml, bundles);
	}
	
	/**
	 * Workaround for buggy Sun logic where under certain Linux desktops the GTK+ look is not activated
	 */
	public static void initSystemLookAndFeel() {
		try {
			String lf = UIManager.getSystemLookAndFeelClassName();
			if (BuilderUtils.getOS() == OperatingSystem.LinuxUnix) {
				//always use GTK+ theme, even under XFCE in Linux
				lf = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
			}
			UIManager.setLookAndFeel(lf);
		} catch (Exception ex) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				LOG.error("Unable to set proper L&F",e);
			}
		}
	}
	
}

