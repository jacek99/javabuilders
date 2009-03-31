package org.javabuilders.gtk;

/**
 * Commonly used constants
 * @author Jacek Furmankiewicz
 */
public class GtkConstants {

	public static final String LABEL = "label";
	public static final String LAYOUT = "layout";
	public static final String HOMOGENOUS = "homogenous";
	public static final String MNEMONIC_LABEL = "mnemonicLabel";
	public static final String SPACING = "spacing";
	public static final String STOCK= "stock";
	public static final String TITLE = "title";
	
	//internal
	public static final String INTERNAL_LAYOUT_HANDLED = "internalLayoutHandled"; //used for child to indicate it took care of the layout, so superclass type handlers skip it
}
