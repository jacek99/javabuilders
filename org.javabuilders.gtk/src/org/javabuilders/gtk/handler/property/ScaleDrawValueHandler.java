/**
 * 
 */
package org.javabuilders.gtk.handler.property;

import org.freedesktop.bindings.Pointer;
import org.gnome.gtk.GtkJavaBuilderInternalUtils;
import org.gnome.gtk.Scale;
import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Scale.drawValue -> hidden, need to expose it
 * @author Jacek Furmankiewicz
 *
 */
public class ScaleDrawValueHandler extends AbstractPropertyHandler {

	public ScaleDrawValueHandler() {
		super("drawValue");
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String)
	 */
	public void handle(BuilderConfig config, BuildProcess process, Node node, String key) throws BuildException {
		Scale scale = (Scale) node.getMainObject();
		
		if ("false".equals(node.getStringProperty("drawValue"))) {
			GtkJavaBuilderInternalUtils.setScaleDrawValue(scale, false);
		} else {
			GtkJavaBuilderInternalUtils.setScaleDrawValue(scale, true);
		}

	}

}
