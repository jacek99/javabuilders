package org.javabuilders.swing.handler.property;

import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IAllowedValues;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handles java.awt.Frame.setExtendedState().
 * @author Jacek Furmankiewicz
 */
public class FrameExtendedStateHandler extends AbstractPropertyHandler implements IAllowedValues {

	public final static String STATE = "state";
	
	public final static String ICON = "icon";
	public final static String MAXH = "maxh";
	public final static String MAXV = "maxv";
	public final static String MAX = "max";
	
	private final static java.util.logging.Logger logger = Logger.getLogger(FrameExtendedStateHandler.class.getSimpleName());
	private final static FrameExtendedStateHandler singleton = new FrameExtendedStateHandler();
	
	private Map<String,Integer> values = new HashMap<String,Integer>();

	/**
	 * Return singleton
	 * 
	 * @return Singleton
	 */
	public static FrameExtendedStateHandler getInstance() {
		return singleton;
	}

	/**
	 * Constructor
	 */
	private FrameExtendedStateHandler() {
		super(STATE);

		// define list of values
		values.put("normal", Frame.NORMAL);
		values.put("iconified", Frame.ICONIFIED);
		values.put("maximized_horiz", Frame.MAXIMIZED_HORIZ);
		values.put("maximized_vert", Frame.MAXIMIZED_VERT);
		values.put("maximized_both", Frame.MAXIMIZED_BOTH);

		// user friendly aliases
		values.put("icon", Frame.ICONIFIED);
		values.put("maxh", Frame.MAXIMIZED_HORIZ);
		values.put("maxv", Frame.MAXIMIZED_VERT);
		values.put("max", Frame.MAXIMIZED_BOTH);
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {

		Frame frame = (Frame) node.getMainObject();
		String value = node.getStringProperty(key); 
		
		Integer state = values.get(value);
		frame.setExtendedState(state);

		//this Swing functionality is buggy on non-Windows platforms
		//even Toolkit.getDefaultToolkit().isFrameStateSupported(state) returns the wrong value under GTK+
		//need to fix it manually
		
		if (BuilderUtils.getOS() == BuilderUtils.OperatingSystem.LinuxUnix) {
		
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Due to OS-specific Swing bug, handling manually Frame.extendedState: " + state);
			}
			
			//get the max screen size
			GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
			Rectangle screenRect= ge.getMaximumWindowBounds();
			
			switch (state) {
			case Frame.MAXIMIZED_BOTH:
				frame.setSize(screenRect.width, screenRect.width);
				break;
			case Frame.MAXIMIZED_HORIZ:
				frame.pack();
				frame.setSize(screenRect.width,frame.getHeight());
				break;
			case Frame.MAXIMIZED_VERT:
				frame.pack();
				frame.setSize(frame.getWidth(),screenRect.height);
				break;
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Frame.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IAllowedValues#getAllowedValues()
	 */
	public Collection<String> getAllowedValues() {
		return values.keySet();
	}

}
