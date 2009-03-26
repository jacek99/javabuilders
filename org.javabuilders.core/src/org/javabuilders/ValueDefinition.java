package org.javabuilders;

import java.util.HashSet;
import java.util.Set;

import org.javabuilders.event.ObjectMethod;

/**
 * Defines a value, it's optional default and allowed values
 * @author Jacek Furmankiewicz 
 */
public class ValueDefinition {
	private boolean isVariableLength = false;
	private Class<?> type = null;
	private Object defaultValue = null;
	private Set<Object> allowedValues = new HashSet<Object>();
	private String regexPattern = "";
	private String name;
	private Class<?>[] relatedTypes = new Class[0];
	
	/**
	 * @param name Value name
	 * @param type Type
	 */
	public ValueDefinition(String name,Class<?> type) {
		BuilderUtils.validateNotNullAndNotEmpty("name",name);
		BuilderUtils.validateNotNullAndNotEmpty("type",type);
		
		this.name = name;
		this.type = type;
	}

	/**
	 * @param name Name
	 * @param type Type
	 * @param defaultValue Default value
	 */
	public ValueDefinition(String name, Class<?> type, Object defaultValue) {
		BuilderUtils.validateNotNullAndNotEmpty("name",name);
		BuilderUtils.validateNotNullAndNotEmpty("type",type);
		this.name = name;
		this.type = type;
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the default value
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the default value to set
	 */
	public void setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the type
	 */
	public Class<?> getType() {
		return type;
	}
	
	/**
	 * Checks if a value is valid for this definition
	 * @param value Value
	 * @param result Result
	 * @param node Node
	 * @param valueList Value being built
	 * @return
	 * @throws BuildException Thrown fi any exception occurs
	 */
	public void validateValue(BuildProcess result, Node node, Object value, 
			Values<Object,Object> valueList) throws BuildException {
		
		if (getType().equals(NamedObject.class)) { 
			//Named objects
			if (result.getBuildResult().containsKey(value)) {
				valueList.put(value, result.getBuildResult().get(value));
			} else {
				valueList.put(value,null,"'%s' is not a valid named object", value);
			}
		} else if (getType().equals(EventMethod.class)) {
			
			//methods
			ObjectMethod method = BuilderUtils.getCallerEventMethod(result, (String)value, node.getMainObject().getClass(), 
					getRelatedTypes());
			if (method!= null) {
				valueList.put(value, method);
			} else {
				valueList.put(value,null,"No methods found on caller that correspond to the name '%s'",value);
			}
			
		} else {
			//regular types
			if (value != null && !getType().isAssignableFrom(value.getClass())) {
				valueList.put(value, null, "Incorrect type. Must be of %s",getType().getName());
			}
			
			//check allowed values
			if (!valueList.containsKey(value) && getAllowedValues().size() > 0) {
				boolean found = false;
				for(Object allowedValue : getAllowedValues()) {
					if (value != null && value.equals(allowedValue)) {
						found = true;
						break;
					}
				}
				if (!found) {
					valueList.put(value, null,"'%s' is not in list of allowed values: %s",value,
							getAllowedValues().toString());
				}
			}
			
			//check regex
			if (!valueList.containsKey(value) && getRegexPattern().length() > 0 && value instanceof String) {
				String stringValue = (String)value;
				if (!stringValue.matches(getRegexPattern())) {
					valueList.put(value,null, "Value '%s' failed regex validatoin '%s'",value,getRegexPattern());
				}
			}
		}
		
		if (!valueList.containsKey(value)) {
			//all good - value same as key
			valueList.put(value, value);
		}
		
		return;
	}

	/**
	 * @return the allowed values
	 */
	public Set<Object> getAllowedValues() {
		return allowedValues;
	}

	/**
	 * @return if multiple values of this type are allowed (same as Java's "String...values")
	 */
	public boolean isVariableLength() {
		return isVariableLength;
	}

	/**
	 * @param isVariableLength Define whether multiple values are allowed
	 */
	public void setVariableLength(boolean isVariableLength) {
		this.isVariableLength = isVariableLength;
	}

	/**
	 * @return the regex pattern used for validation
	 */
	public String getRegexPattern() {
		return regexPattern;
	}

	/**
	 * @param regexPattern the regex pattern for validation
	 */
	public void setRegexPattern(String regexPattern) {
		this.regexPattern = regexPattern;
	}

	/**
	 * @return Value name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Gets related types. Used mostly to define event classes for methods
	 */
	public Class<?>[] getRelatedTypes() {
		return relatedTypes;
	}

	/**
	 * @param relatedTypes Sets related types
	 */
	public void setRelatedTypes(Class<?>... relatedTypes) {
		this.relatedTypes = relatedTypes;
	}
}


