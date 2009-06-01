package org.javabuilders.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.javabuilders.BuildException;

/**
 * A replacement for the Apache Commons PropertyUtils.
 * I did not like the API (throws exceptions on every method) + had lots of dependencies that bloated the
 * JavaBuilders size
 * @author Jacek Furmankiewicz
 *
 */
public class PropertyUtils {

	//cache them for faster lookups
	private static Map<String,Method> getters = new HashMap<String, Method>();
	private static Map<String,Method> setters = new HashMap<String, Method>();
	private static StringBuilder bld = new StringBuilder();
	
	/**
	 * Checks if a property name is valid
	 * @param instance
	 * @param propertyName
	 * @return
	 */
	public static boolean isValid(Object instance, String propertyName) {
		boolean valid = false;
		
		if (instance != null && propertyName != null && propertyName.length() > 0) {
			String name = getSetterName(propertyName);
			if (getSetter(instance, name) != null) {
				valid = true;
			}
		}
		return valid;
	}
	
	/**
	 * @param instance Object instance
	 * @param propertyName Property name
	 * @return Type of the property
	 */
	public static Class<?> getPropertyType(Object instance, String propertyName) {
		String name = getSetterName(propertyName);
		Method setter = getSetter(instance, name);
		return setter.getParameterTypes()[0];
	}
	
	/**
	 * @param instance Object instance
	 * @param propertyName Property name
	 * @return Type of the property
	 */
	public static Set<String> getPropertyNames(Object instance) {
		Set<String> names = new TreeSet<String>();
		StringBuilder bld = new StringBuilder();
		if (instance != null) {
			Method[] methods = instance.getClass().getMethods();
			for(Method method : methods) {
				if (method.getName().startsWith("set") && method.getParameterTypes().length == 1		) {
					bld.setLength(0);
					bld.append(method.getName().substring(3,4).toLowerCase());
					if (method.getName().length() > 4) {
						bld.append(method.getName().substring(4));
					}
					
					names.add(bld.toString());
				}
			}
		}
		return names;
	}
	
	/**
	 * Sets the value via reflection on an object
	 * @param instance Instance
	 * @param propertyName Property name
	 * @param value Value
	 */
	public static void setProperty(Object instance, String propertyName, Object value) {
		if (instance != null && propertyName != null) {
			
			String name = getSetterName(propertyName);
			Method setter = getSetter(instance, name);
			if (setter != null) {
				try {
					setter.invoke(instance, value);
				} catch (Exception e) {
					throw new BuildException(e,"Failed to set {0}.{1} = {2}",instance.getClass().getName(), propertyName,value);
				}
			} else {
				throw new BuildException("Unable to find setter method: {0}.{1}",instance.getClass().getName(),name);
			}
			
			
		} else {
			throw new BuildException("instance and propertyName cannot be null.");
		}
	}
	
	/**
	 * @param instance Object instance
	 * @param propertyName Property Name
	 * @return
	 */
	public static Object getProperty(Object instance, String propertyName) {
		Object value = null;
		if (instance != null) {
			String name = getGetterName(propertyName);
			Method getter = getGetter(instance, name);
			if (getter != null) {
				try {
					value = getter.invoke(instance);
				} catch (Exception e) {
					throw new BuildException(e,"Failed to get {0}.{1}",instance.getClass().getSimpleName(), propertyName);
				}
			} else {
				throw new BuildException("Unable to find getter method: {0}.{1}",instance.getClass().getName(),name);
			}
		}
		return value;
	}
	
	/**
	 * @param instance Instance
	 * @param propertyExpression Nested property expression
	 * @return Value
	 */
	public static Object getNestedProperty(Object instance, String propertyExpression) {
		String[] parts = propertyExpression.split("\\.");
		if (parts.length == 0) {
			parts = new String[]{propertyExpression};
		}
		Object value = null;
		
		for(int i = 0; i < parts.length; i++) {
			String part = parts[i];
			value = getProperty(instance, part);
			
			//stop on first null
			if (value == null || i == (parts.length - 1)) {
				break;
			} else {
				//keep going down
				instance = value;
			}
		}
		
		return value;
	}
	
	

	//gets list of setter methods
	private static Method getSetter(Object instance, String methodName) {

		bld.setLength(0);
		String key = bld.append(instance.getClass().getName()).append(".").append(methodName).toString();
		
		Method setter = setters.get(key);
		
		if (setter == null) {
			Method[] ms = instance.getClass().getMethods();
			for(Method m  : ms) {
				if (m.getName().equals(methodName) && m.getParameterTypes().length == 1) {
					setter = m;
					setter.setAccessible(true);
					setters.put(key, setter);
					break;
				}
			}
		}
		return setter;
	}

	//gets list of setter methods
	private static Method getGetter(Object instance, String methodName) {
		
		bld.setLength(0);
		String key = bld.append(instance.getClass().getName()).append(".").append(methodName).toString();
		
		Method getter = getters.get(key);
		
		if (getter == null) {
			Method[] ms = instance.getClass().getMethods();
			for(Method m  : ms) {
				if (m.getName().equals(methodName) && m.getParameterTypes().length == 0 && m.getReturnType() != null) {
					getter = m;
					getter.setAccessible(true);
					getters.put(key, getter);
					break;
				}
			}
		}
		return getter;
	}

	//gets the setter name
	private static String getSetterName(String propertyName) {
		StringBuilder bld = new StringBuilder(propertyName.length() + 3);
		bld.setLength(0);
		bld.append("set").append(propertyName.substring(0,1).toUpperCase()).append(propertyName.substring(1));
		return bld.toString();
	}
	
	//gets the getter name
	private static String getGetterName(String propertyName) {
		StringBuilder bld = new StringBuilder(propertyName.length() + 3);
		bld.setLength(0);
		bld.append("get").append(propertyName.substring(0,1).toUpperCase()).append(propertyName.substring(1));
		return bld.toString();
	}
	
	
	
}
