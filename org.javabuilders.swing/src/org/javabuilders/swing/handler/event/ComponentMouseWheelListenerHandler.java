/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.Component;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.util.BuilderUtils;

/**
 * Handler for mouse wheel events
 * @author Jacek Furmankiewicz
 */
public class ComponentMouseWheelListenerHandler extends AbstractPropertyHandler implements IPropertyList {

	public final static String ON_MOUSE_WHEEL_MOVED = "onMouseWheelMoved";
	
	private final static ComponentMouseWheelListenerHandler singleton = new ComponentMouseWheelListenerHandler();
	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(MouseWheelEvent.class);
	
	/**
	 * @return Singleton
	 */
	public static ComponentMouseWheelListenerHandler getInstance() {return singleton;}
	
	/**
	 * Constructor
	 */
	private ComponentMouseWheelListenerHandler() {
		super(ON_MOUSE_WHEEL_MOVED);
	}


	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {

		Component component = (Component)node.getMainObject();
		final Values<String,ObjectMethod> methods = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_WHEEL_MOVED);
		
		component.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent e) {
				BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, methods.values(), e);
			}
		});
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IKeyValueConsumer#getApplicableClass()
	 */
	public Class<?> getApplicableClass() {
		return Component.class;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}
}
