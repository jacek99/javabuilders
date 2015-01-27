package org.javabuilders;

import java.lang.reflect.Method;

/**
 * Represents those properties that should be automatically translated into object method invocations, e.g.:
 * <code>size : packed</code> means really <code>frame.pack()</code>
 * 
 * @author Jacek Furmankiewicz
 *
 */
public class PropertyAsMethodInvocation {

	private Method method;
	private String propertyName;
	private int valueArgumentIndex = 0; //by default the value from the YAML file is the first argument
	private Object[] arguments;
	public Method getMethod() {
		return method;
	}
	/**
	 * @param method Method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	/**
	 * @return Method
	 */
	public String getPropertyName() {
		return propertyName;
	}
	/**
	 * @param propertyName Property name
	 */
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	/**
	 * @return Controls where the value from the YAML goes into the method signature (it's not always the first argument), e.g. JScrollPane.setCorner(String, Component)
	 */
	public int getValueArgumentIndex() {
		return valueArgumentIndex;
	}
	/**
	 * @param valueArgumentIndex  Controls where the value from the YAML goes into the method signature (it's not always the first argument), e.g. JScrollPane.setCorner(String, Component)
	 */
	public void setValueArgumentIndex(int valueArgumentIndex) {
		this.valueArgumentIndex = valueArgumentIndex;
	}
	/**
	 * @return Method arguments
	 */
	public Object[] getArguments() {
		return arguments;
	}
	/**
	 * @param arguments Method arguments
	 */
	public void setArguments(Object[] arguments) {
		this.arguments = arguments;
	}
	
}
