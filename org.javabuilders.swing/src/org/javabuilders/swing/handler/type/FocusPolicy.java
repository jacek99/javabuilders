package org.javabuilders.swing.handler.type;

import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javax.swing.LayoutFocusTraversalPolicy;

/**
 * Allows overriding the focus on a container
 * @author Jacek Furmankiewicz
 */
@SuppressWarnings("serial")
public class FocusPolicy extends LayoutFocusTraversalPolicy {
	
	private final List<Component> components;
	//constructor
	public FocusPolicy(List<Component> components) {
		this.components = components;
	}
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
}