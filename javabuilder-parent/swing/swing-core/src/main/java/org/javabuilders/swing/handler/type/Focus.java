package org.javabuilders.swing.handler.type;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LayoutFocusTraversalPolicy;

/**
 * Used for controlling control focus
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class Focus extends LayoutFocusTraversalPolicy {

	private List<Component> components = new ArrayList<Component>();

	@Override
	public Component getComponentAfter(Container aContainer, Component aComponent) {
		int i = components.indexOf(aComponent) + 1;
		if (i >= components.size()) {
			i = 0;
		}
		return components.get(i);
	}

	@Override
	public Component getComponentBefore(Container aContainer, Component aComponent) {
		int i = components.indexOf(aComponent) + 1;
		if (i < 0) {
			i = components.size() - 1;
		}
		return components.get(i);
	}

	@Override
	public Component getDefaultComponent(Container aContainer) {
		return components.get(0);
	}

	@Override
	public Component getFirstComponent(Container aContainer) {
		return components.get(0);
	}

	@Override
	public Component getLastComponent(Container aContainer) {
		return components.get(components.size() - 1);
	}



	/**
	 * @return the components
	 */
	public List<Component> getComponents() {
		return components;
	}

	/**
	 * @param components the components to set
	 */
	public void setComponents(List<Component> components) {
		this.components = components;
	}

}
