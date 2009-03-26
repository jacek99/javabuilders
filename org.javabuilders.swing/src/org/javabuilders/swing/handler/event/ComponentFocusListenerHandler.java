/**
 * 
 */
package org.javabuilders.swing.handler.event;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.List;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.BuilderUtils;
import org.javabuilders.IPropertyList;
import org.javabuilders.Node;
import org.javabuilders.ValueListDefinition;
import org.javabuilders.Values;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.handler.AbstractPropertyHandler;

/**
 * Maps the onFocus and onFocusLost events to create the appropriate Focus listener
 * @author Jacek Furmankiewicz
 *
 */
public class ComponentFocusListenerHandler extends AbstractPropertyHandler implements IPropertyList {

	private final static ComponentFocusListenerHandler singleton = new ComponentFocusListenerHandler();

	private final static List<ValueListDefinition> defs = ValueListDefinition.getCommonEventDefinitions(FocusEvent.class);
	
	/**
	 * @return Singleton
	 */
	public final static ComponentFocusListenerHandler getInstance() {
		return singleton;
	}
	
	/**
	 * Constructor
	 */
	private ComponentFocusListenerHandler(String... consumedKeys) {
		super(Builder.ON_FOCUS, Builder.ON_FOCUS_LOST);
	}
	
	/* (non-Javadoc)
	 * @see org.javabuilders.handler.IPropertyHandler#handle(org.javabuilders.BuilderConfig, org.javabuilders.BuildResult, org.javabuilders.Node, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public void handle(final BuilderConfig config, final BuildProcess process, final Node node,
			String key) throws BuildException {

		Component component = (Component)node.getMainObject();
		
		final Values<String,ObjectMethod> onFocusList = (Values<String, ObjectMethod>) node.getProperties().get(Builder.ON_FOCUS);
		final Values<String,ObjectMethod> onFocusLostList = (Values<String, ObjectMethod>) node.getProperties().get(Builder.ON_FOCUS_LOST);
		
		component.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
				if (onFocusList != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onFocusList.values(), e);
				}
			}

			public void focusLost(FocusEvent e) {
				if (onFocusLostList != null) {
					BuilderUtils.invokeCallerEventMethods(process.getBuildResult(), node, onFocusLostList.values(), e);
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
