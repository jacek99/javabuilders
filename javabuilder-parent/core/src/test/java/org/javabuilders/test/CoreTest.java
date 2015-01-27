package org.javabuilders.test;

import static org.junit.Assert.*;

import java.util.ResourceBundle;

import javax.swing.JButton;
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
		
		TestBuilderConfig config = new TestBuilderConfig(JPanel.class);
		BuildResult r = new BuildResult(config,this);
		
		assertEquals("firstName", BuilderUtils.generateName(r,"\"First Name\"", null, null));
		assertEquals("labelFirstName", BuilderUtils.generateName(r,"\"First Name\"", "label", null));
		assertEquals("firstNameLabel", BuilderUtils.generateName(r,"\"First Name\"", null, "Label"));
		
		assertEquals("firstName", BuilderUtils.generateName(r,"firstName", null, null));
		assertEquals("labelName", BuilderUtils.generateName(r,"label.name", null, null));
		assertEquals("labelFirstName", BuilderUtils.generateName(r,"label.firstName", null, null));
		assertEquals("firstName", BuilderUtils.generateName(r,"First Name", null, null));
	}

	@Test
	public void testBuildContentProvider() {
		TestBuilderConfig config = new TestBuilderConfig(JPanel.class);
		
		String yaml =  "JPanel(name=testPanel)"; 
		
		BuildResult r = Builder.buildFromString(config, this, yaml);
		assertNotNull(r.get("testPanel"));
		assertTrue(r.get("testPanel") instanceof JPanel);
		
	}
	
	@Test
	public void testPrototypeControlNameParsing() {
		
		TestBuilderConfig config = new TestBuilderConfig(JButton.class);
		
		assertEquals("test32_3",config.prototype("JButton(name=test32_3)"));
		assertEquals("test32",config.prototype("JButton( name=test32 )"));
		assertEquals("test4",config.prototype("JButton(onAction=sux,name=test4 ))"));
		assertEquals("test55",config.prototype("JButton(onAction=sux,name=test55, tes3t=3 )"));
		
		assertEquals("JButton(name=test32_3)",config.getPrototype("test32_3"));
	}
	
	@Test 
	public void testResourceFallbackWithInternationalization() {
		
		//configure global resource
		TestBuilderConfig config = new TestBuilderConfig(JButton.class,JPanel.class);
		config.forType(JButton.class).localize("text");
		config.forType(JPanel.class).children(JButton.class,0,Integer.MAX_VALUE);
		config.addResourceBundle("org.javabuilders.test.Global");
		
		//build with additional local resource
		String yaml = "JPanel:\n" +
			"    - JButton(name=global,text=button.cancel)\n" +
			"    - JButton(name=local,text=title.cancelTask)\n" +
			"    - JButton(name=jb,text=label.processing)\n" +
			"    - JButton(name=wrong,text=label.wrong)";
		
		BuildResult r = Builder.buildFromString(config, this, yaml, ResourceBundle.getBundle("org.javabuilders.test.Local"));
		
		JButton global = (JButton) r.get("global");
		JButton local = (JButton) r.get("local");
		JButton jb = (JButton) r.get("jb");
		JButton wrong = (JButton) r.get("wrong");
		
		//each of the labels should have been fetched from different resources and override the base JB one
		assertEquals("Global Cancel", global.getText());
		assertEquals("Local Cancel Task", local.getText());
		assertEquals("Processing...", jb.getText());
		assertEquals("label.wrong", wrong.getText());
	}
	
	@Test 
	public void testResourceFallbackWithInternationalizationAndMarkedInvalidKeys() {
		
		//configure global resource
		TestBuilderConfig config = new TestBuilderConfig(JButton.class,JPanel.class);
		config.forType(JButton.class).localize("text");
		config.forType(JPanel.class).children(JButton.class,0,Integer.MAX_VALUE);
		config.addResourceBundle("org.javabuilders.test.Global");
		config.setMarkInvalidResourceBundleKeys(true);
		
		//build with additional local resource
		String yaml = "JPanel:\n" +
			"    - JButton(name=global,text=button.cancel)\n" +
			"    - JButton(name=local,text=title.cancelTask)\n" +
			"    - JButton(name=jb,text=label.processing)\n" +
			"    - JButton(name=wrong,text=label.wrong)";
		
		BuildResult r = Builder.buildFromString(config, this, yaml, ResourceBundle.getBundle("org.javabuilders.test.Local"));
		
		JButton global = (JButton) r.get("global");
		JButton local = (JButton) r.get("local");
		JButton jb = (JButton) r.get("jb");
		JButton wrong = (JButton) r.get("wrong");
		
		//each of the labels should have been fetched from different resources and override the base JB one
		assertEquals("Global Cancel", global.getText());
		assertEquals("Local Cancel Task", local.getText());
		assertEquals("Processing...", jb.getText());
		assertEquals("#label.wrong#", wrong.getText());
	}


	@Test 
	public void testResourceFallbackWithoutInternationalization() {
		
		//configure global resource
		TestBuilderConfig config = new TestBuilderConfig(JButton.class,JPanel.class);
		config.forType(JButton.class).localize("text");
		config.forType(JPanel.class).children(JButton.class,0,Integer.MAX_VALUE);
		
		//build with additional local resource
		String yaml = "JPanel:\n" +
			"    - JButton(name=test,text=Button Text)\n";
		
		BuildResult r = Builder.buildFromString(config, this, yaml);

		//label should be unaffected
		JButton global = (JButton) r.get("test");
		assertEquals("Button Text", global.getText());
	}
	
	@Test 
	public void testMarkingOfInvalidResourceKeys() {
		
		//configure global resource
		TestBuilderConfig config = new TestBuilderConfig(JButton.class,JPanel.class);
		config.forType(JButton.class).localize("text");
		config.forType(JPanel.class).children(JButton.class,0,Integer.MAX_VALUE);
		config.addResourceBundle("org.javabuilders.test.Global");
		
		//build with additional local resource
		String yaml = "JPanel:\n" +
			"    - JButton(name=test,text=Button Text)\n";
		
		BuildResult r = Builder.buildFromString(config, this, yaml);

		//label should be unaffected
		JButton global = (JButton) r.get("test");
		assertEquals("Button Text", global.getText());
		
		//now set it to mark invalid resource keys
		config.setMarkInvalidResourceBundleKeys(true);
		
		BuildResult r2 = Builder.buildFromString(config, this, yaml);

		//label should be unaffected
		JButton global2 = (JButton) r2.get("test");
		assertEquals("#Button Text#", global2.getText());

	}

}
