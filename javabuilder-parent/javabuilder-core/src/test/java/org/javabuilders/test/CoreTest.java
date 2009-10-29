package org.javabuilders.test;

import static org.junit.Assert.*;

import javax.swing.JPanel;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.test.resources.ParentClass;
import org.javabuilders.util.BuilderUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to ensure the builder correctly handles getting/creating object references
 * @author Jacek Furmankiewicz
 */
public class CoreTest {

	@Test
	public void existingAndNewInstanceTest() throws Exception {
		ParentClass parentClass = new ParentClass();
		
		Assert.assertNotNull("emptyClass is null", parentClass.getEmptyClass());
		Assert.assertEquals("emptyClass.constraint wrong value", "emptyClass", parentClass.getEmptyClass().getConstraint());
		
		Assert.assertNotNull("createdClass is null", parentClass.getCreatedClass());
		Assert.assertEquals("createdClass.constraint wrong value", "createdClass", parentClass.getCreatedClass().getConstraint());
	}
	
	@Test
	public void testNameGeneration() {
		assertEquals("firstName", BuilderUtils.generateName("\"First Name\"", null, null));
		assertEquals("labelFirstName", BuilderUtils.generateName("\"First Name\"", "label", null));
		assertEquals("firstNameLabel", BuilderUtils.generateName("\"First Name\"", null, "Label"));
		
		assertEquals("firstName", BuilderUtils.generateName("firstName", null, null));
		assertEquals("labelName", BuilderUtils.generateName("label.name", null, null));
		assertEquals("labelFirstName", BuilderUtils.generateName("label.firstName", null, null));
		assertEquals("firstName", BuilderUtils.generateName("First Name", null, null));
	}

	@Test
	public void testBuildContentProvider() {
		TestBuilderConfig config = new TestBuilderConfig(JPanel.class);
		
		String yaml =  "JPanel(name=testPanel)"; 
		
		BuildResult r = Builder.buildFromString(config, this, yaml);
		assertNotNull(r.get("testPanel"));
		assertTrue(r.get("testPanel") instanceof JPanel);
		
	}
	
}
