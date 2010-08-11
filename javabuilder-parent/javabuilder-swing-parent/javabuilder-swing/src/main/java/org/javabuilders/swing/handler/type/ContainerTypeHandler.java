/**
 * 
 */
package org.javabuilders.swing.handler.type;

import java.awt.Component;
import java.awt.Container;
import java.awt.LayoutManager;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.Node;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.handler.property.ComponentSizeHandler;



/**
 * Handler for Containers
 * @author Jacek Furmankiewicz
 *
 */
public class ContainerTypeHandler implements ITypeHandlerFinishProcessor {

	private static final ContainerTypeHandler singleton = new ContainerTypeHandler();
	/**
	 * @return Singleton
	 */
	public static ContainerTypeHandler getInstance() {return singleton;}

	private Set<Class<?>> ignored = new HashSet<Class<?>>();
	
	/**
	 * Constructor
	 */
	private ContainerTypeHandler() {
		ignored.add(JMenuBar.class);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.ITypeHandlerFinishProcessor#finish(org.javabuilders.BuilderConfig, org.javabuilders.BuildProcess, org.javabuilders.Node, java.lang.String, java.util.Map)
	 */
	public void finish(BuilderConfig config, BuildProcess process,
			Node current, String key, Map<String, Object> typeDefinition)
			throws BuildException {

		Container c = (Container) current.getMainObject();
		Node content = current.getChildNode(Builder.CONTENT) ; 
		if (content != null && 
				!content.containsType(LayoutManager.class) &&
				!current.isCustomPropertyEqualTo(SwingJavaBuilder.PROPERTY_IGNORE_LAYOUT_MANAGER, Boolean.TRUE)) {
			
			for(Node child : content.getChildNodes(Component.class)) {
				if (!isIgnored(child.getMainObject())) {
					
					//special handling for JFrame/JDesktopPane
					if (c instanceof JFrame && child.getMainObject() instanceof JDesktopPane) {
						//do nothing...already done earlier via setContentPane()
					} else {
						c.add((Component) child.getMainObject());
					}
					
				}
			}
			
			//fix for size=packed if there is no layout manager
			if (ComponentSizeHandler.PACKED.equals(current.getProperty(ComponentSizeHandler.SIZE))) {
				try {
					//handle both Frame and Window
					Method pack = c.getClass().getMethod("pack");
					pack.invoke(c);
				} catch (Exception e) {
					throw new BuildException(e);
				} 
			}
		}
	}
	
	//figures out if this type should be ignored
	private boolean isIgnored(Object child) {
		boolean ignore = false;
		
		if (child != null) {
			for(Class<?> ignoredClass : ignored) {
				if (ignoredClass.isAssignableFrom(child.getClass())) {
					ignore = true;
					break;
				}
			}
		} else {
			ignore = true;
		}
		
		return ignore;
	}

}
