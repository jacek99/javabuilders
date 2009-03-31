package org.javabuilders.test.resources;

import javax.swing.JComponent;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;

public class ParentClass {

	private Issue14Class emptyClass;
	private Issue14Class createdClass = new Issue14Class("test");

	public ParentClass() {
		BuilderConfig config = new BuilderConfig(null,null,null);
		config.addType(ParentClass.class);
		config.addType(Issue14Class.class);
		config.addNamedObjectCriteria(JComponent.class, "name");
		
		BuildResult result = Builder.build(config, this);
		
		int i = 0;
	}
	
	/**
	 * @return the emptyClass
	 */
	public Issue14Class getEmptyClass() {
		return emptyClass;
	}
	/**
	 * @param emptyClass the emptyClass to set
	 */
	public void setEmptyClass(Issue14Class emptyClass) {
		this.emptyClass = emptyClass;
	}
	/**
	 * @return the createdClass
	 */
	public Issue14Class getCreatedClass() {
		return createdClass;
	}
	/**
	 * @param createdClass the createdClass to set
	 */
	public void setCreatedClass(Issue14Class createdClass) {
		this.createdClass = createdClass;
	}
}
