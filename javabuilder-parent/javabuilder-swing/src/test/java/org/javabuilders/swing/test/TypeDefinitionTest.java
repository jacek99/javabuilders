package org.javabuilders.swing.test;

import static org.junit.Assert.assertEquals;

import java.awt.Frame;

import javax.swing.JFrame;

import org.javabuilders.TypeDefinition;
import org.javabuilders.TypeDefinitionClassHierarchyComparator;
import org.junit.Test;

/**
 * Tests for Type Definitions
 * @author Jacek Furmankiewicz
 *
 */
public class TypeDefinitionTest {

	@Test
	public void testComparator() {
		
		TypeDefinition def0 = new TypeDefinition(Frame.class);
		TypeDefinition def1 = new TypeDefinition(JFrame.class);
		
		TypeDefinitionClassHierarchyComparator comp = new TypeDefinitionClassHierarchyComparator();
		
		assertEquals(0, comp.compare(def0,def0));
		assertEquals(0, comp.compare(def1,def1));
		
		assertEquals(1, comp.compare(def0,def1));
		assertEquals(-1, comp.compare(def1,def0));
		
	}
	
	
}
