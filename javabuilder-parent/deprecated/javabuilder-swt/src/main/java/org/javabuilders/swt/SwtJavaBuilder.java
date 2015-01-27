package org.javabuilders.swt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.eclipse.swt.widgets.Widget;
import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.annotations.BuildFile;

public class SwtJavaBuilder {

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

	private static final SwtJavaBuilderConfig config = new SwtJavaBuilderConfig();
	
	/**
	 * @return SWT builder config
	 */
	public static SwtJavaBuilderConfig getConfig() {return config;}
	
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
		map.put(SwtJavaBuilder.PARENT, parent);
		return Builder.build(getConfig(),caller, map, bundles);
	}
	
	/**
	 * SwingBuilder method that accepts passed in YAML instead of looking for a separate file
	 * @param caller The calling object 
	 * @param bundles Optional list of bundles
	 * @param yaml Valid YAML content
	 * @return Buld result
	 */
	public static BuildResult build(Widget parent, Object caller, String yaml, ResourceBundle...bundles)  {
		Map<String,Widget> map = new HashMap<String, Widget>();
		map.put(SwtJavaBuilder.PARENT, parent);
		return Builder.buildFromString(getConfig(),caller, yaml, map, bundles);
	}
	
	/**
	 * Builds from a specific file
	 * @param parent Parent
	 * @param caller Caller
	 * @param fileName File name  (relative to caller's location)
	 * @param resourceBundles Resource Bundles
	 * @return
	 */
	public static BuildResult buildFromFile(Widget parent, Object caller, String fileName, 
			ResourceBundle...resourceBundles)  {
		Map<String,Widget> map = new HashMap<String, Widget>();
		map.put(SwtJavaBuilder.PARENT, parent);
		return Builder.build(getConfig(),caller, fileName, map, resourceBundles);
	}

}
