/**
 * 
 */
package org.javabuilders.swing.handler.property;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IAllowedPropertyCombinations;
import org.javabuilders.IAllowedPropertyFormat;
import org.javabuilders.Node;
import org.javabuilders.PropertyCombination;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Handles the Component.size property by allowing to specify it as a "size" or
 * "width" / "height" property, e.g.: <code>
 * size: 800x400
 * size: packed
 * </code> or: <code>
 * width: 800
 * height: 400
 * </code>
 * 
 * @author Jacek Furmankiewicz
 * 
 */
public class ComponentSizeHandler extends AbstractPropertyHandler implements
		IAllowedPropertyFormat, IAllowedPropertyCombinations {

	public final static String SIZE = "size";
	public final static String WIDTH = "width";
	public final static String HEIGHT = "height";
	public final static String PACKED = "packed";

	private final static ComponentSizeHandler singleton = new ComponentSizeHandler();
	
	private PropertyCombination combination = new PropertyCombination();
	
	
	/**
	 * Returns the singleton
	 * @return Singleton
	 */
	public static ComponentSizeHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	public ComponentSizeHandler() {
		super(SIZE, WIDTH, HEIGHT);
		
		combination.add(SIZE);
		combination.add(WIDTH,HEIGHT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig,
	 *      org.javabuilders.BuildResult, org.javabuilders.Node,
	 *      java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess result, Node node,
			String key) throws BuildException {

		Component component = (Component) node.getMainObject();

		if (node.getProperties().containsKey(SIZE)) {
			String size = (String) node.getProperties().get(SIZE);
			
			if (PACKED.equals(size)) {
				if (component instanceof Window) {
					Window window = (Window)component;
					window.pack();
				} else if (component instanceof Frame) {
					Frame frame = (Frame)component;
					frame.pack();
				} else {
					throw new BuildException("'size : packed' is only valid for Window/Frame and subclasses");
				}
			} else {			
				String[] parts = size.split("x");
				Integer width = Integer.parseInt(parts[0]);
				Integer height = Integer.parseInt(parts[1]);
				component.setSize(width, height);
			}

		} else {
			// specified via separate width/height properties
			int width = node.getLongProperty(WIDTH).intValue();
			int height = node.getLongProperty(HEIGHT).intValue();
			component.setSize(width, height);
		}
	}

	/**
	 * Returns Component.class
	 * @return Component class
	 */
	public Class<?> getApplicableClass() {
		return Component.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IAllowedPropertyFormat#getRegexPattern(java.lang.String)
	 */
	public String getRegexPattern(String propertyName) {
		if (SIZE.equals(propertyName)) {
			return "\\d+x\\d+|packed";
		} else if (WIDTH.equals(propertyName)) {
			return "\\d+";
		} else if (HEIGHT.equals(propertyName)) {
			return "\\d+";
		} else {
			return "?";
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IAllowedPropertyFormat#getValidSample(java.lang.String)
	 */
	public String getValidSample(String propertyName) {
		if (SIZE.equals(propertyName)) {
			return "800x400 | packed";
		} else if (WIDTH.equals(propertyName)) {
			return "800";
		} else if (HEIGHT.equals(propertyName)) {
			return "400";
		} else {
			return "?";
		}
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IAllowedPropertyCombinations#getAllowedCombinations()
	 */
	public PropertyCombination getAllowedCombinations() {
		return combination;
	}

}
