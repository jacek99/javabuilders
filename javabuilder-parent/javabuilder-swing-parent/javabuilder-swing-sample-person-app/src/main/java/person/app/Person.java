package person.app;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.MessageFormat;

public class Person {
	private String firstName;
	private String lastName;
	private String emailAddress;

	//databinding support
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName, listener);
	}
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName, listener);
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		Object oldValue = this.firstName;
		this.firstName = firstName;
		support.firePropertyChange("firstName", oldValue, this.firstName);

	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		Object oldValue = this.lastName;
		this.lastName = lastName;
		support.firePropertyChange("lastName", oldValue, this.lastName);
		
	}

	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * @param emailAddress
	 *            the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		Object oldValue = this.emailAddress;
		this.emailAddress = emailAddress;
		support.firePropertyChange("emailAddress", oldValue, this.emailAddress);
		
	}

	@Override
	public String toString() {
		return MessageFormat.format("{0} {1} : {2}", getFirstName(), getLastName(), getEmailAddress());
	}
}
