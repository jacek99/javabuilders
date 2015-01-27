/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
 * Constructor
 * @author Jacek Furmankiewicz
 */
public class ComponentMouseMotionListenerHandler extends
		AbstractPropertyHandler implements IPropertyList {

	public final static String ON_MOUSE_DRAGGED = "onMouseDragged";
	public final static String ON_MOUSE_MOVED = "onMouseMoved";
	
	private final static ComponentMouseMotionListenerHandler singleton = new ComponentMouseMotionListenerHandler();	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(MouseEvent.class);
	
	/**
	 * @return Singleton
	 */
	public static ComponentMouseMotionListenerHandler getInstance() {return singleton;}
	
	//constructor
	private ComponentMouseMotionListenerHandler() {
		super(ON_MOUSE_DRAGGED, ON_MOUSE_MOVED);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {
		
		Component component = (Component)node.getMainObject();
		final Values<String,ObjectMethod> onDragged = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_DRAGGED);
		final Values<String,ObjectMethod> onMoved = (Values<String,ObjectMethod>)node.getProperty(ON_MOUSE_MOVED);
		
		component.addMouseMotionListener(new MouseMotionListener() {
			public void mouseDragged(MouseEvent e) {
				if (onDragged != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onDragged.values(), e);
				}
			}

			public void mouseMoved(MouseEvent e) {
				if (onMoved != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onMoved.values(), e);
				}
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
