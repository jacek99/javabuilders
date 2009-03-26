package org.javabuilders.swt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.swt.widgets.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.Builder;

public class SWTBuilder {

	public static final String MARGIN_BOTTOM = "marginBottom";
	public static final String MARGIN_HEIGHT = "marginHeight";
	public static final String MARGIN_WIDTH = "marginWidth";
	public static final String MARGIN_TOP = "marginTop";
	public static final String MARGIN_LEFT = "marginLeft";
	public static final String MARGIN_RIGHT = "marginRight";
	public static final String SPACING = "spacing";
	public static final String STYLE = "style";
	public static final String TEXT = "text";
	
	public static final String ON_SELECTION = "onSelection";
	
	public static final String PARENT = "parent";

	private static final SWTBuilderConfig config = new SWTBuilderConfig();
	
	/**
	 * @return SWT builder config
	 */
	public static SWTBuilderConfig getConfig() {return config;}
	
	/**
	 * Main SWT building method
	 * @param caller
	 * @param bundles
	 * @return
	 * @throws IOException
	 * @throws BuildException
	 */
	public static BuildResult build(Object caller, ResourceBundle...bundles) throws BuildException {
		return Builder.build(getConfig(),caller, bundles);
	}
	
	/**
	 * Main SWT building method
	 * @param caller
	 * @param bundles
	 * @param Explicit parent (if required)
	 * @return
	 * @throws IOException
	 * @throws BuildException
	 */
	public static BuildResult build(Widget parent, Object caller, ResourceBundle...bundles) throws BuildException {
		Map<String,Widget> map = new HashMap<String, Widget>();
		map.put(SWTBuilder.PARENT, parent);
		return Builder.build(getConfig(),caller, map, bundles);
	}
}
