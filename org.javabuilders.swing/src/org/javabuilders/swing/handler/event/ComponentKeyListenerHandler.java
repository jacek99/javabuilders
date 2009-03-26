/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;
import org.javabuilders.swing.SwingJavaBuilder;

/**
 * Handles creating a KeyListener via the onKeyPressed, onKeyReleased, onKeyTyped events
 * @author Jacek Furmankiewicz
 *
 */
public class ComponentKeyListenerHandler extends AbstractPropertyHandler implements IPropertyList{

	private final static ComponentKeyListenerHandler singleton = new ComponentKeyListenerHandler();

	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(KeyEvent.class);
	
	/**
	 * @return Singleton
	 */
	public static ComponentKeyListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private ComponentKeyListenerHandler() {
		super(SwingJavaBuilder.ON_KEY_PRESSED,SwingJavaBuilder.ON_KEY_RELEASED,
				SwingJavaBuilder.ON_KEY_TYPED);
	}

	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config,final  BuildProcess process, final Node node,
			String key) throws BuildException {

		Component component = (Component)node.getMainObject();
		
		final Values<String,ObjectMethod> onKeyTypedList = (Values<String,ObjectMethod>)node.getProperty(SwingJavaBuilder.ON_KEY_TYPED);
		final Values<String,ObjectMethod> onKeyReleasedList = (Values<String,ObjectMethod>)node.getProperty(SwingJavaBuilder.ON_KEY_RELEASED);
		final Values<String,ObjectMethod> onKeyPressedList = (Values<String,ObjectMethod>)node.getProperty(SwingJavaBuilder.ON_KEY_PRESSED);
		
		component.addKeyListener(new KeyListener() {

			public void keyPressed(KeyEvent e) {
				if (onKeyPressedList.size() > 0) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onKeyPressedList.values(), e);
				}
			}

			public void keyReleased(KeyEvent e) {
				if (onKeyReleasedList.size() > 0) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onKeyReleasedList.values(), e);
				}
			}

			public void keyTyped(KeyEvent e) {
				if (onKeyTypedList.size() > 0) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onKeyTypedList.values(), e);
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
	 * @see org.javabuilders.IPropertyList#isList(java.lang.String)
	 */
	public boolean isList(String propertyName) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.javabuilders.IPropertyList#getValueListDefinitions(java.lang.String)
	 */
	public List<ValueListDefinition> getValueListDefinitions(String propertyName) {
		return defs;
	}

}
