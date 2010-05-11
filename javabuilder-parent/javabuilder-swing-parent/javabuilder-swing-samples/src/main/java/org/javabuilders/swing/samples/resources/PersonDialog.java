package org.javabuilders.swing.samples.resources;

import javax.swing.JDialog;

import org.javabuilders.swing.SwingJavaBuilder;


/**
 * Book entry dialog
 * @author Jacek Furmankiewicz
 */
public class PersonDialog extends JDialog {

	private Person person;
	
	public PersonDialog() {
		this(new Person());
	}
	
	public PersonDialog(Person person) {
		this.person = person;
		SwingJavaBuilder.build(this);
	}
	
	public Person getPerson() {
		return person;
	}
	
	private void save() {
		setVisible(false);
	}
	
}
