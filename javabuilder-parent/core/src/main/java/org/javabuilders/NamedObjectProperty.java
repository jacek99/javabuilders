package org.javabuilders;

/**
 * Simple class to keep track of named objects and their properties. Used in binding/validations
 * @author Jacek Furmankiewicz
 */
public class NamedObjectProperty {

	private String name;
	private String propertyExpression;
	
	/**
	 * @param name Object name
	 * @param propertyExpression Path to the property
	 */
	public NamedObjectProperty(String name, String propertyExpression) {
		this.name = name;
		this.propertyExpression = propertyExpression;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the property
	 */
	public String getPropertyExpression() {
		return propertyExpression;
	}
	/**
	 * @param propertyExpression the property to set
	 */
	public void setPropertyExpression(String propertyExpression) {
		this.propertyExpression = propertyExpression;
	} 
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("%s.%s",name,propertyExpression);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof NamedObjectProperty) {
			NamedObjectProperty p = (NamedObjectProperty) obj;
			if (p.getName().equals(getName()) && p.getPropertyExpression().equals(getPropertyExpression())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
}
