package org.javabuilders.swing.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

import javax.swing.JTextField;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.handler.validation.BuilderValidators;
import org.javabuilders.handler.validation.ValidationMessageList;
import org.javabuilders.swing.SwingJavaBuilder;
import org.junit.Test;

/**
 * Unit tests for validations
 */
public class ValidationsTest {

	private String base = 
		"JPanel(name=panel): \n" +
		"    - JLabel(name=label,text=label.field)\n" +
		"    - JTextField(name=field)\n";
	
	private BuilderConfig config = SwingJavaBuilder.getConfig();
	private ResourceBundle bundle = ResourceBundle.getBundle("TestResources");
	private String fieldLabel = bundle.getString("message.label.field");
	
	private static final String FIELD = "field";
	
	@Test
	public void mandatoryTest() throws IOException, BuildException {
		String test = base +
		   "validate: \n" +
		   "    - field.text: {label: message.label.field, mandatory: true}";
		
		BuildResult result = Builder.buildFromString(config, this, test, bundle);
		ValidationMessageList list = result.getValidationMessages();
		
		//test missing
		assertEquals(1,list.size());
		assertFalse(result.validate(false));
		testMessage(list.get(0).getMessage(),BuilderValidators.getDefaultMandatoryMessage(),fieldLabel);
		
		//assure passes if valid
		JTextField field = (JTextField)result.get(FIELD);
		field.setText("Some text");
		
		list = result.getValidationMessages();
		assertEquals(0, list.size());
		assertTrue(result.validate(false));
	}
	
	@Test
	public void minLengthTest() throws IOException, BuildException {
		String test = base +
		   "validate: \n" +
		   "    - field.text: {label: Field, minLength: 5}";
		
		BuildResult result = Builder.buildFromString(config, this, test);
		ValidationMessageList list = result.getValidationMessages();
		
		//assure passes if valid
		JTextField field = (JTextField)result.get(FIELD);
		field.setText("12345");
		
		list = result.getValidationMessages();
		assertEquals(0, list.size());
		assertTrue(result.validate(false));
		
		field.setText("12345 232323");
		
		list = result.getValidationMessages();
		assertEquals(0, list.size());
		assertTrue(result.validate(false));
	}
	
	@Test
	public void maxLengthTest() throws IOException, BuildException {
		String test = base +
		   "validate: \n" +
		   "    - field.text: {label: Field, maxLength: 5}";
		
		BuildResult result = Builder.buildFromString(config, this, test);
		
		//test invalid
		JTextField field = (JTextField)result.get(FIELD);
		field.setText("123456");

		ValidationMessageList list = result.getValidationMessages();
		assertEquals(1,list.size());
		assertFalse(result.validate(false));
		testMessage(list.get(0).getMessage(),BuilderValidators.getDefaultMaxLengthMessage(),fieldLabel,5);
		
		//assure passes if valid
		field.setText("1234");
		
		list = result.getValidationMessages();
		assertEquals(0, list.size());
		assertTrue(result.validate(false));
		
		field.setText("");
		
		list = result.getValidationMessages();
		assertEquals(0, list.size());
		assertTrue(result.validate(false));
	}
	
	@Test
	public void dateFormatTest() throws IOException, BuildException {
		String test = base +
		   "validate: \n" +
		   "    - field.text: {label: Field, dateFormat: dd/MM/yyyy}";
		
		BuildResult result = Builder.buildFromString(config, this, test);
		
		//test invalid
		JTextField field = (JTextField)result.get(FIELD);
		field.setText("45/45/9999");

		ValidationMessageList list = result.getValidationMessages();
		assertEquals(1,list.size());
		assertFalse(result.validate(false));
		testMessage(list.get(0).getMessage(),BuilderValidators.getDefaultDateFormatMessage(),fieldLabel,
				"dd/MM/yyyy");
		
		//assure passes if valid
		field.setText("31/12/2006");
		
		list = result.getValidationMessages();
		assertEquals(0, list.size());
		assertTrue(result.validate(false));
	}
	
	//common method to test messages are formatted correctly
	private void testMessage(String actual, String format, Object...arguments) {
		String pattern = Builder.getResourceBundle().getString(format);
		String expected = MessageFormat.format(pattern, arguments);
		assertEquals(expected, actual);
	}
	
	
}
