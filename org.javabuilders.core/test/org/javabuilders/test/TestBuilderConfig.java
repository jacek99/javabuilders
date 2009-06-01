package org.javabuilders.test;

import javax.swing.JComponent;

import org.javabuilders.BuilderConfig;

/**
 * Simple BuilderConfig for core unit tests
 * @author Jacek Furmankiewicz
 *
 */
public class TestBuilderConfig extends BuilderConfig {

	/**
	 * @param types Tyoes
	 */
	public TestBuilderConfig(Class<?>... types) {
		super(null,null,null);
		
		addType(types);
	}

}
