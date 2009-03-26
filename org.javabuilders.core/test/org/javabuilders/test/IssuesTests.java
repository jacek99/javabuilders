package org.javabuilders.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JComponent;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.test.resources.Issue14Class;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for reported issues
 * @author Jacek
 *
 */
public class IssuesTests {

	@Test
	public void testIssue14_EmbeddedParentheses() throws Exception {
		String yamlContent = "Issue14Class(name=test, constraint=\"aaa and (not bbb or ccc)\")";
		BuilderConfig config = new BuilderConfig(null, null, null, null);
		config.addNamedObjectCriteria(JComponent.class, "name");
		
		config.addType(Issue14Class.class);
		
		BuildResult result = Builder.buildFromString(config, this, yamlContent);
		Issue14Class test = (Issue14Class) result.get("test");

		Assert.assertNotNull("test returned null",test);
		Assert.assertEquals("aaa and (not bbb or ccc)", test.getConstraint());
	}
	
	@Test
	public void testIssue24_NamedObjectPropertyHashing() {
		NamedObjectProperty title = new NamedObjectProperty("this","title");
		NamedObjectProperty author = new NamedObjectProperty("this","author");
		
		Map<NamedObjectProperty,String> testMap = new HashMap<NamedObjectProperty, String>();
		testMap.put(title, "title");
		testMap.put(author, "author");
		
		assertEquals(2, testMap.size());
		
		assertTrue("title != title", title.equals(title));
		assertTrue("author != author", author.equals(author));
		assertTrue("title = author", !title.equals(author));
	}
}
