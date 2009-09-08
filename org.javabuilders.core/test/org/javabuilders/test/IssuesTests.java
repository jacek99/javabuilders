package org.javabuilders.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.InvalidPropertyException;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.exception.InvalidFormatException;
import org.javabuilders.exception.UnrecognizedAliasException;
import org.javabuilders.test.resources.LocalBuildFilePanel;
import org.javabuilders.test.resources.GlobalBuildFilePanel;
import org.javabuilders.test.resources.Issue14Class;
import org.javabuilders.test.resources.Issue38Class;
import org.javabuilders.util.YamlBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for reported issues
 * @author Jacek
 *
 */
public class IssuesTests {

	private GlobalBuildFilePanel testPanel = null;
	
	@After
	public void tearDown() {
		testPanel = null;
	}
	
	@Test
	public void testIssue14_EmbeddedParentheses() throws Exception {
		String yamlContent = "Issue14Class(name=test, constraint=\"aaa and (not bbb or ccc)\")";
		BuilderConfig config = new TestBuilderConfig(Issue14Class.class);
		
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
	
	@Test(expected=InvalidFormatException.class)
	public void issue34_MissingParentheses() {
		String yaml = " JTextField(name=xDogYearsTF  , columns=3";
		BuilderConfig c = new TestBuilderConfig(JTextField.class);
		Builder.buildFromString(c, this, yaml);
	}
	
	@Test(expected=InvalidFormatException.class)
	public void issue34_MissingParenthesesNested() {
		String yaml = "JPanel:\n    JTextField(name=xDogYearsTF  , columns=3";
		BuilderConfig c = new TestBuilderConfig(JPanel.class,JTextField.class);
		Builder.buildFromString(c, this, yaml);
	}
	
	@Test(expected=InvalidFormatException.class)
	public void issue36_ImprovedErrorHandling_Tabs() {
		String tabTest = "JPanel:\n\tJLabel(name=test)";
		BuilderConfig c = new TestBuilderConfig(JLabel.class,JPanel.class);
		Builder.buildFromString(c, this, tabTest);
	}
	
	@Test(expected=UnrecognizedAliasException.class)
	public void issue36_ImprovedErrorHandling_SpacesBeforeParentheses() {
		String tabTest = "JLabel (name=test)";
		BuilderConfig c = new TestBuilderConfig(JLabel.class,JPanel.class);
		BuildResult r = Builder.buildFromString(c, this, tabTest);
	}
	
	@Test
	public void issue38_NestedStaticClass() {
		Issue38Class.View view = new Issue38Class.View();
		
		assertNotNull(view);
		assertNotNull("view.label is null",view.getLayout());
	}
	
	@Test
	public void issue38_BuildFileLocal() {
		//test a local build file in the same package
		LocalBuildFilePanel p = new LocalBuildFilePanel();
		assertNotNull(p);
		assertNotNull(p.getLabel());
		assertEquals("Test", p.getLabel().getText());
	}
	
	@Test
	public void issue38_BuildFileGlobal() {
		//test a local build file in a global package
		GlobalBuildFilePanel p = new GlobalBuildFilePanel();
		assertNotNull(p);
		assertNotNull(p.getLabel());
		assertEquals("Test", p.getLabel().getText());
	}
	
	@Test
	public void issue44_customComponentInstantiation() {
		testPanel = new GlobalBuildFilePanel();
		testPanel.getLabel().setText("ISSUE_44");
		
		BuilderConfig c = new TestBuilderConfig(JPanel.class);
		c.forType(JPanel.class).children(JPanel.class, 0, Integer.MAX_VALUE);
		
		YamlBuilder bld = new YamlBuilder("JPanel(name=panel):") {{
				___("- GlobalBuildFilePanel(name=testPanel)");
				___("- GlobalBuildFilePanel(name=testPanel2)");
		}};
		BuildResult r = Builder.buildFromString(c, this, bld.toString());
		
		//existing name should refer to existing instance
		GlobalBuildFilePanel gp = (GlobalBuildFilePanel) r.get("testPanel");
		assertNotNull(gp);
		assertSame(testPanel, gp);
		assertEquals("ISSUE_44",gp.getLabel().getText());
		
		//non-existant name should create new instance
		GlobalBuildFilePanel gp2 = (GlobalBuildFilePanel) r.get("testPanel2");
		assertNotNull(gp2);
		assertNotSame(testPanel, gp2);
		assertFalse(gp.getLabel().getText().equals(gp2.getLabel().getText()));
	}
	
	@Test(expected=InvalidFormatException.class)
	public void issue50_quotesValidation() {
		BuilderConfig c = new TestBuilderConfig(JButton.class);
		String yaml = "JButton(text=\"text)"; 
		BuildResult r = Builder.buildFromString(c, this, yaml);
	}
	
	@Test(expected=InvalidFormatException.class)
	public void issue50_tabValidation() {
		BuilderConfig c = new TestBuilderConfig(JButton.class);
		String yaml = "JButton(text=Ok)\t"; 
		BuildResult r = Builder.buildFromString(c, this, yaml);
	}
	
	@Test(expected=InvalidFormatException.class)
	public void issue50_parenthesesValidation() {
		BuilderConfig c = new TestBuilderConfig(JButton.class);
		String yaml = "JButton(onAction=(save,test)"; 
		BuildResult r = Builder.buildFromString(c, this, yaml);
	}
	
	@Test
	public void issue64_frenchResources() {
		
		assertEquals("Cancel",Builder.getResourceBundle().getString("button.cancel"));
		
		Locale.setDefault(Locale.FRANCE);
		assertEquals("Abandonner",Builder.getResourceBundle().getString("button.cancel"));
		
		Locale.setDefault(new Locale("fr","CA"));
		assertEquals("Abandonner",Builder.getResourceBundle().getString("button.cancel"));
		
	}

	
}
