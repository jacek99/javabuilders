package org.javabuilders.swing.test.issues;

import static org.junit.Assert.*;

import java.awt.event.ActionEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;

import org.javabuilders.BuildException;
import org.javabuilders.BuildResult;
import org.javabuilders.Builder;
import org.javabuilders.BuilderConfig;
import org.javabuilders.handler.validation.ICustomValidator;
import org.javabuilders.handler.validation.IValidator;
import org.javabuilders.handler.validation.ValidationMessage;
import org.javabuilders.handler.validation.ValidationMessageList;
import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.controls.JBSeparator;
import org.javabuilders.swing.test.issues.resources.CustomGenericPanel;
import org.javabuilders.swing.test.issues.resources.Issue10;
import org.javabuilders.swing.test.issues.resources.Issue11;
import org.javabuilders.swing.test.issues.resources.Issue12_JPopupMenuException;
import org.javabuilders.swing.test.issues.resources.Issue17_JMenuBarInJDialog;
import org.javabuilders.swing.test.issues.resources.Issue20_DuplicateNames;
import org.javabuilders.swing.test.issues.resources.Issue20_NoYaml;
import org.javabuilders.swing.test.issues.resources.Issue23_Exception;
import org.javabuilders.swing.test.issues.resources.Issue7;
import org.javabuilders.swing.test.issues.resources.IssueNullValue;
import org.javabuilders.swing.test.issues.resources.IssueNullValue2;
import org.javabuilders.swing.util.SwingYamlBuilder;
import org.javabuilders.test.TestBuilderConfig;
import org.javabuilders.util.YamlBuilder;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Tests for all submitted issues
 * @author Jacek Furmankiewicz
 */
public class IssuesTests {

	@Test
	public void issue7Test() throws Exception {
		JPanel panel = new Issue7();
		
		JLabel label = (JLabel)panel.getComponent(0);
		assertEquals("someLabel",label.getName());
		
		JTextField field = (JTextField)panel.getComponent(1);
		assertEquals("someTextField",field.getName());
		
		JPanel panel2 = (JPanel)panel.getComponent(2);
		assertEquals("childPanel",panel2.getName());
		
		JButton button = (JButton)panel2.getComponent(0);
		assertEquals("someButton", button.getName());
		
	}
	
	@Test
	public void issue10Test() throws Exception {
		@SuppressWarnings("unused")
		JFrame frame = new Issue10();
	}
	
	
	@Test
	@Ignore("Ignore for now until we move up to JDK 6")
	public void issue11Test() throws Exception {
		Issue11 issue = new Issue11();
		
		assertNotNull("PopupMenu instance is null",issue.getPopup());
		assertNotNull("JPopupMenu instance is null",issue.getJpopup());
	}
	
	@Test
	public void issue12Test() throws Exception {
		Issue12_JPopupMenuException test = new Issue12_JPopupMenuException();
		test.dispose();
	}
	
	@Test
	public void issueNullValueTest() {
		IssueNullValue test = new IssueNullValue();
		test.dispose();
	}

	@Test
	public void issueNullValue2Test() {
		IssueNullValue2 test = new IssueNullValue2();
		test.dispose();
	}
	
	@Test
	public void issue17Test_JMenuBarInJDialog() {
		Issue17_JMenuBarInJDialog dialog = new Issue17_JMenuBarInJDialog();
		
		assertNotNull("menu is null",dialog.menuBar);
		assertNotNull("JDialog,getJMenuBar()is null",dialog.getJMenuBar());
		assertEquals("dialog.menuBar != dialog.getJMenuBar()", dialog.getJMenuBar(), dialog.menuBar);
	}
	
	@Test
	public void issue19_JBSeparatorTextLocalized() {
		String yaml= "JBSeparator(text=button.ok)";
		JBSeparator sep = new JBSeparator();
		ResourceBundle bundle = ResourceBundle.getBundle("TestResources");
		
		SwingJavaBuilder.build(sep, yaml, bundle);
		
		assertEquals("JBSeparator.text not localized", bundle.getString("button.ok"), sep.getText());
	}
	
	@Test
	public void issue21_FailToInvokeMethodWithAncestorSignature() {
		String yaml = "JButton(onAction=issue21)";
		JButton button = new JButton("Test") {
			private void issue21(JComponent c, ActionEvent evt) {
			}
		};
		
		SwingJavaBuilder.build(button, yaml);
		button.doClick();
	}
	
	@Test
	public void issue23_Exception() {
		Issue23_Exception frame = new Issue23_Exception();
	}
	
	/**
	 * If no YAML file found, a proper BuildException should be thrown instead of NullPointerException
	 */
	@Test(expected=BuildException.class)
	public void issue20_NoYaml() {
		Issue20_NoYaml panel = new Issue20_NoYaml();
	}
	
	/**
	 * Properly throw an error if duplicate names are defined in the YAML file
	 */
	@Test(expected=BuildException.class)
	public void issue20_duplicateNames() {
		Issue20_DuplicateNames panel = new Issue20_DuplicateNames();
		
	}
	
	/**
	 * Test that fetching icons via direct path works
	 */
	@Test
	public void issue18_directIconPath() {
		
		String yaml="JButton(name=button,icon=resources/document-new.png)";
		JButton button = (JButton) Builder.buildFromString(SwingJavaBuilder.getConfig(), this, yaml).get("button");
		assertNotNull("JButton.icon was null", button.getIcon());
	}
	
	/**
	 * Test that fetching icons via resource bundles works
	 */
	@Test
	public void issue18_bundleIconPath() {
		String yaml="JButton(name=button,icon=icon.issue18)";
		JButton button = (JButton) Builder.buildFromString(SwingJavaBuilder.getConfig(), this, yaml, ResourceBundle.getBundle("TestResources")).get("button");
		assertNotNull("JButton.icon was null", button.getIcon());
	}
	
	/**
	 * Test that fetching icons via direct path works
	 */
	@Test
	public void issue18_directIconImagePath() {
		
		String yaml="JFrame(name=frame,iconImage=resources/document-new.png)";
		JFrame frame= (JFrame) Builder.buildFromString(SwingJavaBuilder.getConfig(), this, yaml).get("frame");
		assertNotNull("JFrame.image was null", frame.getIconImage());
	}
	
	/**
	 * Test that fetching icons via resource bundles works
	 */
	@Test
	public void issue18_bundleIconImagePath() {
		String yaml="JFrame(name=frame,iconImage=icon.issue18)";
		JFrame frame= (JFrame) Builder.buildFromString(SwingJavaBuilder.getConfig(), this, yaml, ResourceBundle.getBundle("TestResources")).get("frame");
		assertNotNull("JFrame.image was null", frame.getIconImage());
	}
	
	@Test
	public void issue47_chainedConfirmValidate() {
		String yaml="JButton(name=button,onAction=($confirm,$validate))";
		JButton button = (JButton) SwingJavaBuilder.build(this, yaml).get("button");
		assertNotNull(button.getActionListeners());
		assertEquals(1,button.getActionListeners().length);
	}
	
	
	@Test
	public void issue48_customValidator() {

		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JTextField(name=text)");
			validate();
			___("- text.text: {mandatory: true}");
			
		}}.build(this);
		
		JTextField text = (JTextField) r.get("text");
		assertNotNull(text);
		assertFalse(r.validate(false)); //validation should fail
		text.setText("TEST");
		assertTrue(r.validate(false)); //validation should pass
		
		//add custom validator
		r.getValidators().add(new ICustomValidator() {
			public void validate(ValidationMessageList list) {
				list.add(new ValidationMessage("TEST"));
			}
		});
		
		assertFalse(r.validate(false)); //validation should fail
		ValidationMessageList list = r.getValidationMessages();
		assertEquals(1,list.size());
		assertEquals("TEST",list.get(0).getMessage());
		
		//validate again, should still be the same
		assertFalse(r.validate(false)); //validation should fail
		list = r.getValidationMessages();
		assertEquals(1,list.size());
		assertEquals("TEST",list.get(0).getMessage());
		
		//make both standard and custom fail - we should have 2 error messages
		text.setText(null);
		
		assertFalse(r.validate(false)); //validation should fail
		list = r.getValidationMessages();
		assertEquals(2,list.size());
		assertEquals("TEST",list.get(1).getMessage());
		
		text.setText("");
		assertFalse(r.validate(false)); //validation should fail
		list = r.getValidationMessages();
		assertEquals(2,list.size());
		assertEquals("TEST",list.get(1).getMessage());
	}
	

	//variation where the custom validator is added BEFORE the first validate
	@Test
	public void issue48_customValidator2() {

		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JTextField(name=text)");
			validate();
			___("- text.text: {mandatory: true}");
			
		}}.build(this);

		//add custom validator
		r.getValidators().add(new ICustomValidator() {
			public void validate(ValidationMessageList list) {
				if (list.size() > 0) {
					//only add error if another one exists
					list.add(new ValidationMessage("TEST"));
				}
			}
		});
		
		JTextField text = (JTextField) r.get("text");
		assertNotNull(text);
		assertFalse(r.validate(false)); //validation should fail
		ValidationMessageList list = r.getValidationMessages();
		assertEquals(2,list.size());
		assertEquals("TEST",list.get(1).getMessage());
		
		text.setText("TEST");
		assertTrue(r.validate(false)); //validation should pass
		assertEquals(0, r.getValidationMessages().size());
	}
	
	@Test
	public void issue50_referenceSuperclassProtectedVariables() {
		CustomGenericPanel panel = new CustomGenericPanel();
		
		assertNotNull("superclass text field is null",panel.getGenericTextField());
		assertNotNull("class text field is null",panel.getCustomTextField());
		
	}
	
	@Test
	public void issue52_onSelectionForJList() {
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JList(name=list,onSelection=jlist_selection)");
		}}.build(this);

		JList list = (JList) r.get("list");
		assertNotNull(list);
		assertEquals(1,list.getListSelectionListeners().length);
	}
	
	@Test
	public void issue52_onSelectionForJListMultiple() {
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JList(name=list,onSelection=(jlist_selection,jlist_selection2))");
		}}.build(this);

		JList list = (JList) r.get("list");
		assertNotNull(list);
		assertEquals(1,list.getListSelectionListeners().length);
	}
	
	@Test(expected=BuildException.class)
	public void issue52_onSelectionForJListInvalid() {
		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- JList(name=list,onSelection=jlist2_selection)"); //wrong method name
		}}.build(this);

		JList list = (JList) r.get("list");
		assertNotNull(list);
	}

	//needed for a test
	private void jlist_selection(ListSelectionEvent evt) {
		//do nothing
	}

	//needed for a test
	private void jlist_selection2(ListSelectionEvent evt) {
		//do nothing
	}
	
	@Test
	public void issue53_noActionsUnderJPanel() {

		BuildResult r = new SwingYamlBuilder("JPanel:") {{
			___("- Action(name=text)");
		}}.build(this);

	}

}
