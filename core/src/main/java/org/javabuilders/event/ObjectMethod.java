package org.javabuilders.event;

import java.lang.reflect.Method;

/**
 * Represents the method to be executed on any named object
 * @author Jacek Furmankiewicz
 */
public class ObjectMethod {

	private Object instance;
	private Method method;
	private MethodType type = MethodType.Regular;

	/**
	 * @param instance Object instance
	 * @param method Method to execute on that instance
	 */
	public ObjectMethod(Object instance, Method method) {
		this(instance,method,MethodType.Regular);
	}
	
	/**
	 * @param instance Object instance
	 * @param method Method to execute on that instance
	 */
	public ObjectMethod(Object instance, Method method, MethodType type) {
		this.instance = instance;
		this.method =  method;
		this.type = type;
	}
	
	/**
	 * @return the method
	 */
	public Method getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(Method method) {
		this.method = method;
	}
	/**
	 * @return the instance
	 */
	public Object getInstance() {
		return instance;
	}
	/**
	 * @param instance the instance to set
	 */
	public void setInstance(Object instance) {
		this.instance = instance;
	}
	
	/**
	 * Defines method type
	 * @author Jacek Furmankiewicz
	 */
	public enum MethodType {
		Regular, CustomCommand
	}

	/**
	 * @return Method type
	 */
	public MethodType getType() {
		return type;
	}

	/**
	 * @param type Method type
	 */
	public void setType(MethodType type) {
		this.type = type;
	}
	
}
