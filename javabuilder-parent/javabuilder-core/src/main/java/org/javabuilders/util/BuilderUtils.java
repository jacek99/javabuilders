/**
 * 
 */
package org.javabuilders.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javabuilders.BuildException;
import org.javabuilders.BuildProcess;
import org.javabuilders.BuildResult;
import org.javabuilders.BuilderConfig;
import org.javabuilders.ICustomCommand;
import org.javabuilders.NamedObjectProperty;
import org.javabuilders.Node;
import org.javabuilders.Values;
import org.javabuilders.annotations.Alias;
import org.javabuilders.annotations.DoInBackground;
import org.javabuilders.event.BackgroundEvent;
import org.javabuilders.event.CancelStatus;
import org.javabuilders.event.IBackgroundCallback;
import org.javabuilders.event.ObjectMethod;
import org.javabuilders.exception.InvalidFormatException;

/**
 * Various common utilities
 * 
 * @author Jacek Furmankiewicz
 * 
 */
public class BuilderUtils {

	private final static java.util.logging.Logger logger = Logger.getLogger(BuilderUtils.class.getSimpleName());
	private static OperatingSystem os = OperatingSystem.Windows; // it's the most likely

	// should accept both ${propertyName} and ${object.propertyName}
	private static Pattern elPattern = Pattern.compile("[${][a-z][a-zA-Z0-9]*(\\.?[a-z][a-zA-Z0-9]*)*}"); // check for EL pattern
	private static String beanPattern = "[a-zA-Z][a-zA-Z09]*(\\.?[a-z]?[a-zA-Z0-9]*)*"; // check bean pattern:either 
	// "propertyName"
	// or
	// "object.propertyName"
	// or
	// "object.propertyName.nestedProperty"

	private static Pattern namePattern = Pattern.compile("([A-Z]{0,1}[0-9a-z]+)");

	/**
	 * Static constructor
	 */
	static {
		String name = System.getProperty("os.name").toLowerCase();
		if (name.indexOf("unix") >= 0 || name.indexOf("linux") >= 0) {
			os = OperatingSystem.LinuxUnix;
		} else if (name.indexOf("mac") >= 0) {
			os = OperatingSystem.Mac;
		}
	}

	/**
	 * Returns the current operating system. Used for platform-specific fixes
	 * 
	 * @return OS
	 */
	public static OperatingSystem getOS() {
		return os;
	}

	/**
	 * Simple enum to represent the different OS types
	 * 
	 * @author Jacek Furmankiewicz
	 */
	public enum OperatingSystem {
		Windows, LinuxUnix, Mac, Other
	}

	/**
	 * Returns the method that should be invoked based on the name defined in
	 * the build file.<br/>
	 * The following order of preference is used, based on the arguments:<br/>
	 * <ol>
	 * <li>method(caller-compatible class, event class)
	 * <li>method(caller-compatible class)
	 * <li>method(event class)
	 * <li>method()
	 * </ol>
	 * Caller-compatible class has to be the caller's class or any of its
	 * superclasses/interfaces. Support for mapping to methods annotated with
	 * "@Name".
	 * 
	 * @param result
	 *            The build result
	 * @param node
	 *            Source node whose object generates the event
	 * @param methodKey
	 *            The base name of the method to be invoked (e.g. "save")
	 * @param eventClasses
	 *            an optional list of classes specific to the event (e.g.
	 *            KeyEvent or MouseEvent for Swing key event listeners)
	 * @return
	 */
	public static ObjectMethod getCallerEventMethod(BuildProcess result, Node node, String methodKey, Class<?>... eventClasses)
			throws BuildException {
		String methodName = String.valueOf(node.getProperties().get(methodKey));
		return getCallerEventMethod(result, methodName, node.getMainObject().getClass(), eventClasses);
	}

	/**
	 * Returns the method that should be invoked based on the name defined in
	 * the build file.<br/>
	 * The following order of preference is used, based on the arguments:<br/>
	 * <ol>
	 * <li>method(caller-compatible class, event class)
	 * <li>method(caller-compatible class)
	 * <li>method(event class)
	 * <li>method()
	 * </ol>
	 * Caller-compatible class has to be the caller's class or any of its
	 * superclasses/interfaces. Support for mapping to methods annotated with
	 * "@Name".
	 * 
	 * @param node
	 *            Source node whose object generates the event
	 * @param result
	 *            The build result
	 * @param methodName
	 *            Method name
	 * @param mainObjectClass
	 *            Main object (i.e. the object that generates the call to the
	 *            method, e.g. a button) class
	 * @param eventClasses
	 *            an optional list of classes specific to the event (e.g.
	 *            KeyEvent or MouseEvent for Swing key event listeners)
	 * @return Method to call or null if none found
	 */
	public static ObjectMethod getCallerEventMethod(BuildProcess result, String methodName, Class<?> mainObjectClass,
			Class<?>... eventClasses) throws BuildException {

		Set<Method> methods = new HashSet<Method>();

		// custom command
		if (result.getConfig().getCustomCommands().containsKey(methodName)) {
			ICustomCommand<? extends Object> command = result.getConfig().getCustomCommands().get(methodName);
			try {
				Method method = command.getClass().getMethod("process", BuildResult.class, Object.class);
				ObjectMethod noMethod = new ObjectMethod(command, method, ObjectMethod.MethodType.CustomCommand);
				return noMethod;
			} catch (Exception ex) {
				logger.severe(ex.getMessage());
				throw new BuildException(ex, "Unable to get custom command method: {0}", ex.getMessage());
			}
		}

		Object target = result.getCaller();

		// regular method
		for (Method method : getAllMethods(target.getClass())) {

			if (method.getParameterTypes().length <= 2) {
				// find methods with the specified name or annotated with @Name
				// with the same value
				if (method.isAnnotationPresent(Alias.class)) {
					if (method.getAnnotation(Alias.class).value().equals(methodName)) {
						methods.add(method);
					}
				} else if (method.getName().equals(methodName)) {
					methods.add(method);
				}
			}
		}

		// we have a set of methods with the same name..find the appropriate one
		// to call
		Method methodToCall = null;
		TreeMap<Integer, Method> methodsByPreference = new TreeMap<Integer, Method>();

		preferenceSearch: // start the search for compatible methods by
		// preference
		for (Method method : methods) {

			// find the best methods to call
			switch (method.getParameterTypes().length) {
			case 0:
				// no arguments - lowest preference
				methodsByPreference.put(0, method);
				break;
			case 1:
				// one argument - can be either caller or event class
				Class<?> parameterType = method.getParameterTypes()[0];
				if (parameterType.isAssignableFrom(mainObjectClass)) {
					methodsByPreference.put(2, method); // second in terms of
					// preference
				} else if (eventClasses != null && eventClasses.length > 0) {

					for (Class<?> eventClass : eventClasses) {
						if (parameterType.isAssignableFrom(eventClass)) {
							methodsByPreference.put(3, method); // third in
							// terms of
							// preference
							break;
						} else if (method.isAnnotationPresent(DoInBackground.class)
								&& BackgroundEvent.class.isAssignableFrom(method.getParameterTypes()[0])) {
							// background event method
							methodsByPreference.put(5, method); // background
							// methods have
							// highest
							// preference
						}
					}
				}
				break;
			case 2:
				// two arguments - should be caller class / event class
				Class<?> firstParameterType = method.getParameterTypes()[0];
				Class<?> secondParameterType = method.getParameterTypes()[1];

				boolean isSecondParameterAnEventClass = false;
				for (Class<?> eventClass : eventClasses) {
					if (secondParameterType.isAssignableFrom(eventClass)) {
						isSecondParameterAnEventClass = true;
						break;
					}
				}

				if (firstParameterType.isAssignableFrom(mainObjectClass) && isSecondParameterAnEventClass) {
					methodsByPreference.put(4, method); // best preference
					break preferenceSearch; // no need to search further - we
					// already found the best method
				}

				break;
			}

		}

		// find the best method to call based on preference
		for (int i = 5; i >= 0; i--) {
			if (methodsByPreference.containsKey(i)) {
				methodToCall = methodsByPreference.get(i);
				break;
			}
		}

		if (methodToCall != null) {
			methodToCall.setAccessible(true); // make sure we can call it, even
			// if it's private
		} else {
			throw new BuildException("Unable to find method to call for name \"{0}\"", methodName);
		}

		return new ObjectMethod(target, methodToCall);
	}

	/**
	 * Invoked the method mapped to an event on a caller
	 * 
	 * @param result
	 *            Build result
	 * @param node
	 *            Source that generated the event
	 * @param methods
	 *            The methods to be called (with zero parameters or one that is
	 *            compatible with the source)
	 * @param eventClassInstance
	 *            The event-specific class type (can be null)
	 * @see getCallerEventMethod()
	 */
	public static void invokeCallerEventMethods(final BuildResult result, Node node, Collection<ObjectMethod> methods,
			Object eventClassInstance) {
		invokeCallerEventMethods(result, node.getMainObject(), methods, eventClassInstance);
	}

	/**
	 * Invoked the method mapped to an event on a caller
	 * 
	 * @param result
	 *            Build result
	 * @param mainObject
	 *            Source that generated the event
	 * @param methods
	 *            The methods to be called (with zero parameters or one that is
	 *            compatible with the source)
	 * @param eventClassInstance
	 *            The event-specific class type (can be null)
	 * @see getCallerEventMethod()
	 */
	public static void invokeCallerEventMethods(final BuildResult result, Object mainObject, Collection<ObjectMethod> methods,
			Object eventClassInstance) {

		Object invocationResult = null;

		for (ObjectMethod method : methods) {
			try {

				// custom command?
				if (method.getType() == ObjectMethod.MethodType.CustomCommand) {
					method.getMethod().setAccessible(true);
					invocationResult = method.getMethod().invoke(method.getInstance(), result, mainObject);

					if (Boolean.FALSE.equals(invocationResult)) {
						// abort
						break;
					}
				} else {

					// is this a background method?
					if (method.getMethod().isAnnotationPresent(DoInBackground.class)) {

						DoInBackground ann = method.getMethod().getAnnotation(DoInBackground.class);

						final BackgroundEvent event = new BackgroundEvent(mainObject, eventClassInstance, ann.blocking(), result
								.getConfig());
						event.setCancelable(ann.cancelable());
						event.setProgressIndeterminate(ann.indeterminateProgress());
						event.setProgressStart(ann.progressStart());
						event.setProgressEnd(ann.progressEnd());
						event.setProgressValue(ann.progressValue());

						// handle internationalizing the progress message
						String resource = result.getResource(ann.progressMessage());
						event.setProgressMessage(resource);

						if (logger.isLoggable(Level.FINE)) {
							logger.log(Level.FINE, "Executing background method: %s", method.getMethod().getName());
						}

						// create the list of methods that should be executed
						// after this background one
						// completes
						final Collection<ObjectMethod> outstandingMethods = new ArrayList<ObjectMethod>();
						boolean next = false;
						for (ObjectMethod nextMethod : methods) {
							if (method.equals(nextMethod)) {
								next = true;
							} else if (next) {
								outstandingMethods.add(nextMethod);
							}
							;
						}

						// when background method is done, execute the remaining
						// methods
						// via a recursive call
						final IBackgroundCallback callback = new IBackgroundCallback() {

							public void done(Object returnValue) {
								// only continue if task was not cancelled
								if (event.getCancelStatus().getStatus() <= CancelStatus.NONE.getStatus()
										&& outstandingMethods != null && outstandingMethods.size() > 0) {
									BuilderUtils.invokeCallerEventMethods(result, event.getSource(), outstandingMethods, event
											.getOriginalEvent());
								}
							}

						};

						result.getConfig().getBackgroundProcessingHandler().doInBackground(result, result.getCaller(),
								method.getMethod(), event, callback);

						if (logger.isLoggable(Level.FINE)) {
							logger.fine("Finished executing background method: " + method.getMethod().getName());
						}
						// stop executing the methods - it is the background
						// handler's responsibility
						// to execute them after the background one completes
						// successfully
						break;
					}

					switch (method.getMethod().getParameterTypes().length) {
					case 0:
						// no arguments
						invocationResult = method.getMethod().invoke(method.getInstance());
						break;
					case 1:
						// one argument - can be caller or event class
						Class<?> parameterType = method.getMethod().getParameterTypes()[0];
						if (parameterType.isAssignableFrom(mainObject.getClass())) {
							invocationResult = method.getMethod().invoke(method.getInstance(), mainObject);
						} else {
							invocationResult = method.getMethod().invoke(method.getInstance(), eventClassInstance);
						}
						break;
					case 2:
						// two arguments - must be caller, event class instance
						invocationResult = method.getMethod().invoke(result.getCaller(), mainObject, eventClassInstance);
						break;
					}

					// if invoked method return false, abort calling the rest of
					// the methods
					if (invocationResult != null && invocationResult.equals(Boolean.FALSE)) {
						break;
					}

					if (logger.isLoggable(Level.FINE)) {
						logger.fine("Finished executing method: " + method.getMethod().getName());
					}
				}

			} catch (Exception ex) {
				throw new BuildException(ex, "Failed to invoke method: {0}. {1}", method.getMethod().getName(), ex.getMessage());
			}
		}
	}

	/**
	 * If a caller is defined, updates any of the caller's instance variables
	 * references to the corresponding ones that were created during the build.<br/>
	 * The following rules are followed:<br/>
	 * <ul>
	 * <li>The caller's instance variable reference must be null (i.e. cannot
	 * override existing one)
	 * <li>It must match exactly by name to the named object
	 * <li>The instance variables type must be compatible with the one created
	 * during the build</li>
	 * </ul>
	 */
	public static void updateNamedObjectReferencesInCaller(BuildProcess result) {

		Object caller = result.getCaller();

		//get a list of all the fields in the full object hierarchy, so that
		//setting references on superclass fields is possible too (Issue #51)
		List<Field> fields = new LinkedList<Field>();
		Class<?> parent = caller.getClass();
		while (parent != null) {
			fields.addAll(Arrays.asList(parent.getDeclaredFields()));
			parent = parent.getSuperclass();
		}
		
		if (caller != null) {

			for (String name : result.getBuildResult().keySet()) {

				for (Field field : fields) {

					String fromName = field.getName();
					if (field.isAnnotationPresent(Alias.class)) {
						fromName = field.getAnnotation(Alias.class).value();
					}

					if (fromName.equals(name)) {
						field.setAccessible(true); // ensure we have access to
						// the field, even if private
						Object value = null;
						try {
							value = field.get(caller);
							if (value == null) {
								Object namedObject = result.getBuildResult().get(name);

								if (field.getType().isAssignableFrom(namedObject.getClass())) {
									field.set(caller, namedObject);
									if (logger.isLoggable(Level.FINE)) {
										logger.fine("Successfully set reference to caller's variable: " + name);
									}
									continue;
								} else {
									if (logger.isLoggable(Level.INFO)) {
										logger.info("Failed to set value for caller's variable: " + name + ". Incompatible types.");
									}
								}
							} else {
								// instance can be pre-existing
								if (logger.isLoggable(Level.FINE)) {
									logger.info("Unable to set caller's instance variable: " + name + ". It is not null.");
								}
							}
						} catch (IllegalArgumentException e) {
							if (logger.isLoggable(Level.INFO)) {
								logger.log(Level.INFO, "Failed to access property " + name, e);
							}
						} catch (IllegalAccessException e) {
							if (logger.isLoggable(Level.INFO)) {
								logger.log(Level.INFO, "Failed to access property " + name, e);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Validates a value is not null and not empty
	 * 
	 * @param name
	 *            Value name
	 * @param value
	 *            Value
	 */
	public static void validateNotNullAndNotEmpty(String name, Object value) {
		if (value == null) {
			throw new NullPointerException(String.format("%s cannot be null", name));
		}

		if (value instanceof String && ((String) value).length() == 0) {
			throw new NullPointerException(String.format("%s cannot be empty String", name));
		}
	}

	/**
	 * Simple utility to quickly convert a single value to a list, or return a
	 * list if the value is already a list to begin with
	 * 
	 * @param value
	 * @return Value converted to list
	 */
	@SuppressWarnings("unchecked")
	public static List<Object> convertToList(Object value) {
		if (value instanceof List) {
			return (List<Object>) value;
		} else {
			List<Object> list = new ArrayList<Object>();
			list.add(value);
			return list;
		}
	}

	/**
	 * Converts a list to a string
	 * 
	 * @param list
	 *            List
	 * @param delimiter
	 *            Delimiter
	 * @param estimatedLength
	 *            Estimated length (for performance reasons)
	 * @return String
	 */
	public static String convertListToString(List<Object> list, char delimiter, int estimatedLength) {
		StringBuilder builder = new StringBuilder(estimatedLength);
		for (Object value : list) {
			if (builder.length() > 0) {
				builder.append(delimiter).append(" ");
			}
			builder.append(value);
		}
		return builder.toString();
	}

	/**
	 * Method to automatically populate the properties of an object from a map
	 * of values
	 * 
	 * @param target
	 *            Target
	 * @param properties
	 *            Map of propeties and their values
	 * @throws BuildException
	 *             Thrown if unable to set property
	 */
	public static void populateObjectPropertiesFromMap(Object target, Map<String, Object> properties) throws BuildException {
		for (String property : properties.keySet()) {
			Object value = properties.get(property);
			try {
				PropertyUtils.setProperty(target, property, value);
			} catch (Exception e) {
				throw new BuildException("Unable to set value on object for key: " + property, e);
			}
		}
	}

	/**
	 * Used for parsing property paths in binding and validation expression
	 * 
	 * @param text
	 *            Input text (with properties for binding/validation, in EL or
	 *            bean-style)
	 * @return List of properties
	 * @throws BuildException
	 */
	public static List<NamedObjectProperty> getParsedPropertyExpression(String text) throws BuildException {
		List<NamedObjectProperty> properties = new ArrayList<NamedObjectProperty>();

		// look for EL expressions in the source path
		Matcher m = elPattern.matcher(text);
		while (m.find()) {
			// add, but removing the EL ${ and } brackets
			String path = m.group().split("[${]|[}]")[1];
			// split the object name and property path into separate fields
			NamedObjectProperty property = getParsedProperty(path);
			properties.add(property);
		}

		// if none found assume it's a bean property
		if (properties.size() == 0) {
			if (text.matches(beanPattern)) {
				NamedObjectProperty property = getParsedProperty(text);
				properties.add(property);
			} else {
				throw new BuildException("Unable to parse property expression. It is recognized as neither EL or regular Bean: "
						+ text);
			}
		}
		return properties;
	}

	/**
	 * Parses a named object/property path
	 * 
	 * @param property
	 *            Property path in "objectName.propertyPath" format
	 * @return Parsed properety
	 * @throws BuildException
	 */
	public static NamedObjectProperty getParsedProperty(String property) throws BuildException {

		if (property.indexOf('.') <= 0) {
			property = String.format("this.%s", property);
		}

		int pos = property.indexOf('.');
		if (pos > 0) {
			NamedObjectProperty objectProperty = new NamedObjectProperty(property.substring(0, pos), property.substring(pos + 1));
			return objectProperty;
		} else {
			throw new BuildException("Unable to parse named object property: " + property);
		}
	}

	/**
	 * Finds the class type from a name referenced in YAML by looking at the
	 * instance variables on the caller class
	 * 
	 * @param caller
	 * @param className
	 * @param objectName
	 * @return Class (or null if none found)
	 */
	public static Class<?> getClassFromCallerFields(Object caller, String className, String objectName) {
		Class<?> typeClass = null;
		Map<String, Class<?>> classes = new HashMap<String, Class<?>>();

		Field[] fields = caller.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().getSimpleName().equals(className)) {
				classes.put(field.getName(), field.getType());
				typeClass = field.getType();
			}
		}

		if (classes.size() > 1) {
			// rare case...multiple declarations of the same custom class
			for (String name : classes.keySet()) {
				if (name.equals(objectName)) {
					typeClass = classes.get(name);
					break;
				}
			}
		}

		return typeClass;
	}

	/**
	 * Gets the class from a class alias in the YAML file
	 * 
	 * @param process
	 * @param key
	 * @param instanceName
	 *            (optionall name of the class instance)
	 * @return Class or null if not found
	 */
	public static Class<?> getClassFromAlias(BuildProcess process, String key, String instanceName) {
		Class<?> typeClass = process.getConfig().getClassType(key);
		if (typeClass == null) {
			// try treating the key as if it was a complete class name
			typeClass = BuilderUtils.getClassFromCallerFields(process.getCaller(), key, instanceName);
		}
		//if still null, check if the name is in the class hierarchy of the caller
		if (typeClass == null) {
			Class<?> parent = process.getCaller().getClass();
			while (parent != null) {
				if (parent.getSimpleName().equals(key)) {
					typeClass = parent;
					break;
				}
				parent = parent.getSuperclass();
			}
		}
		
		return typeClass;
	}

	/**
	 * @return All fields, including protected fields from superclasses
	 */
	public static Map<String, Field> getAllFields(Class<? extends Object> typeClass) {
		Map<String, Field> allFields = new HashMap<String, Field>();

		for (Field field : typeClass.getDeclaredFields()) {
			allFields.put(field.getName(), field);
			field.setAccessible(true);
		}

		typeClass = typeClass.getSuperclass();
		while (typeClass != null) {
			Field[] fields = typeClass.getDeclaredFields();
			for (Field field : fields) {
				int mod = field.getModifiers();
				if (!Modifier.isStatic(mod) && (Modifier.isProtected(mod) || Modifier.isPublic(mod))) {
					allFields.put(field.getName(), field);
					field.setAccessible(true);
				}
			}

			typeClass = typeClass.getSuperclass();
		}

		return allFields;
	}
	
	/**
	 * Looks for a field with a particular name and type
	 * @param name Name 
	 * @param typeClass Type class
	 * @return Field or null if not found
	 */
	public static Field getField(Object caller, String name, Class<? extends Object> typeClass) {
		Map<String,Field> fields = getAllFields(caller.getClass());
		Field field = null;
		for(String fieldName : fields.keySet()) {
			if (name.equals(fieldName)) {
				Field temp = fields.get(fieldName);
				if (typeClass.isAssignableFrom(temp.getType())) {
					field = temp;
				}
				break;
			}
		}
		return field;
	}

	/**
	 * Gets all accessible method from the entire object hierarchy
	 * 
	 * @param typeClass
	 *            Type class
	 * @return List of methods
	 */
	public static List<Method> getAllMethods(Class<? extends Object> typeClass) {
		List<Method> allMethods = new ArrayList<Method>();

		for (Method method : typeClass.getDeclaredMethods()) {
			allMethods.add(method);
			method.setAccessible(true);
		}

		typeClass = typeClass.getSuperclass();
		while (typeClass != null) {
			Method[] methods = typeClass.getDeclaredMethods();
			for (Method method : methods) {
				int mod = method.getModifiers();
				if (!Modifier.isStatic(mod) && (Modifier.isProtected(mod) || Modifier.isPublic(mod))) {
					allMethods.add(method);
					method.setAccessible(true);
				}
			}

			typeClass = typeClass.getSuperclass();
		}

		return allMethods;

	}

	/**
	 * Checks if a listener is needed, depending if any of the passed method
	 * lists have any value in them
	 * 
	 * @param methods
	 *            Method lists
	 * @return true if needed, false if not
	 */
	public static boolean isListenerNeeded(Values<String, ObjectMethod>... methods) {
		boolean needed = false;

		for (Values<String, ObjectMethod> methodList : methods) {
			if (methodList != null && methodList.size() > 0) {
				needed = true;
				break;
			}
		}

		return needed;
	}

	/**
	 * Checks YAML for tabs...since the jvyaml error reporting is so poor that
	 * it gives you no idea as to where the error is
	 * 
	 * @param yaml
	 */
	public static void validateYamlContent(String yaml, String fileName) {
		StringBuilder errors = new StringBuilder();
		String[] lines = yaml.split("\n");
		String previousLine = null;
		for(int i = 0; i < lines.length;i++) {
			String line = lines[i];
			//check for tabs
			int pos = line.indexOf("\t");
			if (pos >= 0) {
				errors.append(MessageFormat.format("Found a tab in line {0} starting at\n{1}\n", (i+1), line.substring(pos)));
			}
			//check for unmatched parentheses
			int left = 0, right = 0;
			int startingQuotes = 0, endingQuotes = 0;
			boolean inQuotes = false;
			for(int c = 0; c < line.length(); c++) {
				char charAt = line.charAt(c);
				if (charAt == '"') {
					inQuotes = !inQuotes;
					if (inQuotes) {
						startingQuotes++;
					} else {
						endingQuotes++;
					}
				} else if (charAt == '(' && !inQuotes) {
					left++;
				} else if (charAt == ')' && !inQuotes) {
					right++;
				} else if (charAt == '#' && !inQuotes) {
					//'#' means start of comment, disregard everything afterwards
					break;
				}
			}
			if (left != right) {
				errors.append(MessageFormat.format("Unmatched number of left and right parentheses in line {0}: {1}\n", (i+1), line));
			}
			if (startingQuotes != endingQuotes) {
				errors.append(MessageFormat.format("Unmatched number of opening and closing quotes in line {0}: {1}\n", (i+1), line));
			}
			//check if collection started without a ":" on the parent
			if (previousLine != null) {
				String trimmed = line.trim();
				if (trimmed.startsWith("-")) {
					pos = line.indexOf("-");
					int prevPos = previousLine.indexOf("-");
					if (pos != prevPos) {
						//previous line is a parent and not another element in the same collection
						//it has to end with ":" then
						String noCommentsLine = getYamlLineWithoutTrailingComments(previousLine);
						int lineIndent = getFirstNoSpacePosition(line);
						int previousLineIndent = getFirstNoSpacePosition(previousLine);
						
						if(!noCommentsLine.trim().endsWith(":") && lineIndent > previousLineIndent) {
							throw new BuildException("\":\" is missing after \"{0}\", as list is started on next line",noCommentsLine);
						}
					}
				}
			}
			
			//remember for the next line to validate against
			previousLine = line;
		}
		
		if (errors.length() > 0) {
			if (fileName != null) {
				errors.insert(0, MessageFormat.format("Errors found in file: {0}\n", fileName));
			}
			throw new InvalidFormatException(errors.toString());
		}
	}
	
	private static int getFirstNoSpacePosition(String line) {
		for(int i = 0; i < line.length();i++) {
			if (line.charAt(i) != ' ') {
				return i;
			}
		}
		return -1;
	}
	
	private static String getYamlLineWithoutTrailingComments(String line) {
		if (line.indexOf("#") < 0) {
			return line;
		} else {
			boolean inQuotes = false;
			for(int i = 0; i < line.length();i++) {
				char c = line.charAt(i);
				if (c == '"') {
					inQuotes = !inQuotes;
				} else if (c == '#' && !inQuotes) {
					//comment begins
					return line.substring(0,i);
				}
			}
			return line;
		}
	}

	/**
	 * Attempts to find an existing instance
	 * 
	 * @param caller
	 * @return
	 */
	public static Object getExistingInstanceIfAvailable(Object caller, Class<?> expectedClass, BuilderConfig config,
			Map<String, Object> data) {

		Object instance = null;

		if (caller != null) {
			Map<String, Field> fields = getAllFields(caller.getClass());
			String name = config.getNameIfAvailable(data);
			if (name != null) {
				Object possible = null;
				try {
					if (fields.containsKey(name)) {
						possible = fields.get(name).get(caller);
					}
				} catch (Exception e) {
					throw new BuildException(e, "Failed to get value for {0}: {1}", name, e.getMessage());
				}

				if (possible != null) {
					if (expectedClass.isAssignableFrom(possible.getClass())) {
						// class types are compatible
						instance = possible;
					} else {
						throw new BuildException(
								"Found a variable called {0}, but it was of incompatible type {1}, instead of the expected {2}",
								name, possible.getClass(), expectedClass);
					}
				}
			}
		}

		return instance;
	}

	/**
	 * Standard logic to get a URL from a path in YAML. Looks in the class
	 * filesystem first, if not found looks it up in the resource bundles
	 * instead. Enhancement <a
	 * href="http://code.google.com/p/javabuilders/issues/detail?id=18">#18</a>
	 * 
	 * @param process
	 *            Process
	 * @param path
	 *            Path (can be an actual path, e.g. "resources/picture.png" or a
	 *            key in the resource bundle. e.g. "image.fileNew" where in the
	 *            bundle it would say "image.fileNew=/resources/picture.png")
	 * @return URL or null if not found
	 */
	public static URL getResourceURL(BuildProcess process, String path) {
		URL url = process.getCaller().getClass().getResource(path);
		if (url == null) {
			// path not found...check if the path points to a key in the
			// resource bundle instead
			if (process.getBuildResult().isInternationalizationActive()) {
				path = process.getBuildResult().getResource(path);
				if (path != null) {
					url = process.getCaller().getClass().getResource(path);
				}
			}
		}
		return url;
	}

	/**
	 * Standard logic to get an input stream from a path in YAML. Looks in the
	 * class filesystem first, if not found looks it up in the resource bundles
	 * instead. Enhancement <a
	 * href="http://code.google.com/p/javabuilders/issues/detail?id=18">#18</a>
	 * 
	 * @param process
	 *            Process
	 * @param path
	 *            Path (can be an actual path, e.g. "resources/picture.png" or a
	 *            key in the resource bundle. e.g. "image.fileNew" where in the
	 *            bundle it would say "image.fileNew=/resources/picture.png")
	 * @return URL or null if not found
	 */
	public static InputStream getResourceInputStream(BuildProcess process, String path) {
		InputStream is = process.getCaller().getClass().getResourceAsStream(path);
		if (is == null) {
			// path not found...check if the path points to a key in the
			// resource bundle instead
			if (process.getBuildResult().isInternationalizationActive()) {
				path = process.getBuildResult().getResource(path);
				if (path != null) {
					is = process.getCaller().getClass().getResourceAsStream(path);
				}
			}
		}
		return is;
	}

	/**
	 * Generates a Java-safe name from an input string
	 * 
	 * @param input
	 * @return
	 */
	public static String generateName(String input, String prefix, String suffix) {
		Matcher m = namePattern.matcher(input);
		StringBuilder bld = new StringBuilder(input.length());
		if (prefix != null) {
			bld.append(prefix);
		}

		while (m.find()) {
			String group = m.group();
			if (group.length() > 1) {
				if (bld.length() == 0) {
					bld.append(group.substring(0, 1).toLowerCase());
					bld.append(group.substring(1));
				} else {
					bld.append(group.substring(0, 1).toUpperCase());
					bld.append(group.substring(1));
				}
			} else {
				bld.append(group.toUpperCase());
			}
		}

		if (suffix != null) {
			bld.append(suffix);
		}
		return bld.toString();
	}

	/**
	 * Reads the YAML file for a particular class
	 * 
	 * @param baseClass
	 *            Class
	 * @return YAML content for that class
	 * @throws IOException
	 */
	public static String getYamlContent(Class<?> baseClass) throws IOException {

		StringBuilder builder = new StringBuilder();

		InputStream is = baseClass.getResourceAsStream(baseClass.getSimpleName() + ".yaml");
		InputStreamReader isr = null;
		BufferedReader rdr = null;
		try {

			isr = new InputStreamReader(is);
			rdr = new BufferedReader(isr);

			String line = "";
			while ((line = rdr.readLine()) != null) {
				builder.append(line).append("\n");
			}
		} finally {
			if (rdr != null) {
				rdr.close();
			}
			if (isr != null) {
				isr.close();
			}
		}

		return builder.toString();
	}
	
    /**
     * Gets generics info despite runtime type erasure. Hack alert, obviously.
     * @param field Field
     * @return Class (if found) or null if not
     */
    public static Class<?> getGenericsTypeFromCollectionField(Field field) {
        Class<?> clazz = null;
        if (List.class.isAssignableFrom(field.getType()) || Set.class.isAssignableFrom(field.getType())) {
            field.setAccessible(true);
            ParameterizedType ptype = (ParameterizedType) field.getGenericType();
            Type[] types = ptype.getActualTypeArguments();
            if (types != null && types.length > 0) {
                clazz = (Class<?>)types[0];
            }
        }
        return clazz;
    }
    
    /**
     * Massages an argument into MessageFormat to stop it from blowing up MessageFormat (if it has embedded {} values)
     * @param argument Argument
     * @return MessageFormat-safe argument string
     */
    public static Object[] getMessageFormatSafeArguments(Object... arguments) {
    	for(int i = 0; i < arguments.length;i++) {
    		Object argument = arguments[i];
    		String str = null;
    		if (argument instanceof String) {
    			str = (String) argument;
	    	} else if (argument != null) {
	    		str = String.valueOf(str);
	    	}
    		if (str != null && str.indexOf('{') >= 0) {
    			arguments[i] = str.replace("{","\\{");
    		}
    	}
    	return arguments;
    }
    
    /**
     * Gets value from a map and substitutes it with a default if not found
     * @param map
     * @param key
     * @param defaultValue
     * @return
     */
    public static Object getValue(Map<String,Object> map, Object key, Object defaultValue) {
    	Object value = map.get(key);
    	if (value == null) {
    		value = defaultValue;
    	}
    	return value;
    }
    

}
