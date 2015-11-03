package org.javabuilders.swing.handler.type.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

/**
 * Marker class to indicate the absolute layout positioning.
 * This class represents the tag AbsoluteLayout applicable to a Container.
 * LayoutManager is implemented just for respecting the cardinalities while bulding
 * the Container. All methods are no-op.
 * 
 * @author Luca Domenichini
 */
public class AbsoluteLayout implements LayoutManager {

	@Override
	public void addLayoutComponent(String name, Component comp) {
		
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	@Override
	public void layoutContainer(Container parent) {
		
	}

}
