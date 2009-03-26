package org.javabuilders.test;

import org.javabuilders.test.resources.ParentClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests to ensure the builder correctly handles getting/creating object references
 * @author Jacek Furmankiewicz
 */
public class CoreTests {

	@Test
	public void existingAndNewInstanceTest() throws Exception {
		ParentClass parentClass = new ParentClass();
		
		Assert.assertNotNull("emptyClass is null", parentClass.getEmptyClass());
		Assert.assertEquals("emptyClass.constraint wrong value", "emptyClass", parentClass.getEmptyClass().getConstraint());
		
		Assert.assertNotNull("createdClass is null", parentClass.getCreatedClass());
		Assert.assertEquals("createdClass.constraint wrong value", "createdClass", parentClass.getCreatedClass().getConstraint());
	}
}
