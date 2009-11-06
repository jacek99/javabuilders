package org.javabuilders.test;

import static com.google.common.collect.Lists.*;
import static org.junit.Assert.*;

import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;

import org.apache.commons.lang.StringUtils;
import org.javabuilders.util.compiler.CompilerUtils;
import org.junit.Test;

/**
 * Tests the in-memory compiler
 * @author jacek
 *
 */
public class CompilerUtilsTest {

	@Test
	public void testUniqueClassNameGenerator() {
		
		List<String> names = newArrayList();
		
		for(int i = 0; i < 1000;i++) {
			String name = CompilerUtils.generateClassName(Comparator.class);
			assertFalse(names.contains(name));
			names.add(name);
		}
	}
	
	@Test
	public void testClassSignatureGenerator() {
		String name = CompilerUtils.generateClassSignature("Test1",JButton.class);
		assertTrue(name.indexOf(" extends " + JButton.class.getName()) > 0);
		
		name = CompilerUtils.generateClassSignature("Test2", Comparator.class);
		assertTrue(name.indexOf(" implements " + Comparator.class.getName()) > 0);
	}
	
	@Test
	public void testPropertiesComparator() {
		Comparator<JButton> c = (Comparator<JButton>) CompilerUtils.newComparator(JButton.class, "text","toolTipText");
		JButton b1 = new JButton("Text1");
		b1.setToolTipText("Tip1");
		JButton b11 = new JButton("Text1");
		b11.setToolTipText("Tip1");
		JButton b2 = new JButton("Text2");
		b2.setToolTipText("Tip1");
		JButton b22 = new JButton("Text2");
		b2.setToolTipText("Tip2");
		
		assertEquals(0,c.compare(b1, b1));
		assertEquals(0,c.compare(b1, b11));
		
		assertFalse(c.compare(b1,b2) == 0);
		assertFalse(c.compare(b1,b22) == 0);
	}
	
	@Test
	public void testObjectComparator() {
		Comparator<String> c = (Comparator<String>) CompilerUtils.newComparator(String.class);
		
		String s1 = "1";
		String s11 = "1";
		String s0 = "0";
		String s2 = "2";
		
		assertEquals(0, c.compare(s1,s11));
		assertEquals(-1, c.compare(s0,s1));
		assertEquals(1, c.compare(s2, s1));
	}
	
	@Test
	public void testPrimitiveComparator() {
		Comparator<Integer> c = (Comparator<Integer>) CompilerUtils.newComparator(int.class);

		int s0 = 0;
		int s1 = 1;
		int s11 = 1;
		int s2 = 2;
		
		assertEquals(0, c.compare(s1,s11));
		assertEquals(-1, c.compare(s0,s1));
		assertEquals(1, c.compare(s2, s1));
	}
	
}
