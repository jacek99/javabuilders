package org.javabuilders.layout.mig;

import java.util.HashMap;
import java.util.Map;

import org.javabuilders.layout.ControlConstraint;
import org.javabuilders.layout.DefaultResize;
import org.javabuilders.layout.Size;

/**
 * Common constants and methods for integrating the MigLayout (since it is used by both the SWT and Swing
 * builders and hence cross-domain)
 * @author Jacek Furmankiewicz
 *
 */
public class MigLayoutCommon {

	public final static String LAYOUT_CONSTRAINTS = "layoutConstraints";
	public final static String ROW_CONSTRAINTS = "rowConstraints";
	public final static String COLUMN_CONSTRAINTS = "columnConstraints";
	public final static String DEFAULT_ROW_COLUMN_CONSTRAINT = "[] ";

	/**
	 * RESIZE HELPERS
	 */
	private final static Map<DefaultResize,String> resizeConstraints = new HashMap<DefaultResize, String>();
	static {
		resizeConstraints.put(DefaultResize.BOTH, "grow");
		resizeConstraints.put(DefaultResize.X_AXIS, "growx");
		resizeConstraints.put(DefaultResize.Y_AXIS, "growy");	
	}
	
	/**
	 * Handles all the size/height/resize logic
	 * @param builder Current builder
	 * @param c Control constraints
	 * @param defaultResize Default resize
	 * @param additionalConstraints Any additional control constraints
	 */
	public static void handleResize(StringBuilder builder, ControlConstraint c, DefaultResize defaultResize, String additionalConstraints) {
		String resize = resizeConstraints.get(defaultResize);
		
		if (c.getHSize() == Size.DEFAULT && c.getVSize() == Size.DEFAULT) {
			//no specific size constraints, use default resize logic
			if (resize != null) {
				//only add it there aren't some additional "grow" constraints that override it
				if (additionalConstraints == null || additionalConstraints.indexOf("grow") < 0) {
					//no resize related additional constraints
					if (builder.length() > 0) {
						builder.append(", ");
					}
					builder.append(resize);
				}
			}
		} else {

			if (builder.length() > 0) {
				builder.append(", ");
			}
			//width
			switch (c.getHSize()) {
			case MIN:
				builder.append("width min!");
				break;
			case PREF:
				builder.append("width pref!");
				break;
			case MAX:
				builder.append("growx"); // width max! seemed buggy
				break;
			case DEFAULT:
				if (defaultResize == DefaultResize.BOTH || defaultResize == DefaultResize.X_AXIS) {
					builder.append(resizeConstraints.get(DefaultResize.X_AXIS));
				} else {
					builder.append("width pref!");
				}
				break;
			}

			//height
			if (builder.length() > 0) {
				builder.append(", ");
			}
			switch (c.getVSize()) {
			case MIN:
				builder.append("height min!");
				break;
			case PREF:
				builder.append("height pref!");
				break;
			case MAX:
				builder.append("growy"); //height max! seemed buggy
				break;
			case DEFAULT:
				if (defaultResize == DefaultResize.BOTH || defaultResize == DefaultResize.Y_AXIS) {
					builder.append(resizeConstraints.get(DefaultResize.Y_AXIS));
				} else {
					builder.append("height pref!");
				}
				break;
			}

		}
	}
	
}
