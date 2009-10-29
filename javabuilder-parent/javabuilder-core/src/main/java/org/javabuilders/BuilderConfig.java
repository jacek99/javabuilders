/**
 * 
 */
package org.javabuilders;

import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Logger;

import org.javabuilders.event.BuildListener;
import org.javabuilders.event.IBackgroundProcessingHandler;
import org.javabuilders.handler.DefaultPropertyHandler;
import org.javabuilders.handler.DefaultTypeHandler;
import org.javabuilders.handler.IPropertyHandler;
import org.javabuilders.handler.ITypeHandler;
import org.javabuilders.handler.IntegerAsValueHandler;
import org.javabuilders.handler.binding.BuilderBindings;
import org.javabuilders.handler.type.ClassAsValueHandler;
import org.javabuilders.handler.type.IntArrayAsValueHandler;
import org.javabuilders.handler.type.IntegerArrayAsValueHandler;
import org.javabuilders.handler.validation.BuilderValidators;
import org.javabuilders.handler.validation.DefaultValidatorTypeHandler;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.PropertyUtils;

/**
 * Represents the configuration for a builder (e.g. Swing vs SWT, etc)
 * @author Jacek Furmankiewicz
 */
public class BuilderConfig {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(BuilderConfig.class.getSimpleName());
	
	static ITypeHandler defaultTypeHandler = new DefaultTypeHandler();
	static IPropertyHandler defaultPropertyHandler =  DefaultPropertyHandler.getInstance();
	
	private static String devSourceFolder = null;
	public static final String SOURCE = "javabuilders.dev.src";
	public final static String CUSTOM_COMMAND_REGEX = "\\$[a-zA-Z0-9]+"; //e.g. "$validate"
	public final static String GLOBAL_VARIABLE_REGEX = "\\$\\$\\{[a-zA-Z0-9]+\\}"; //e.g. "$${dateFormat}"
	private static Set<ResourceBundle> bundles = new LinkedHashSet<ResourceBundle>();
	
	public static String PROPERY_STRING_LITERAL_CONTROL_PREFIX = "StringLiteralControl.Prefix";
	public static String PROPERY_STRING_LITERAL_CONTROL_SUFFIX = "StringLiteralControl.Suffix";

	/**
	 * Static constructor
	 */
	static {
		devSourceFolder = System.getProperty(SOURCE);
	}
	
	/**
	 * @return Current dev source folder (usually null unless overwritted with -Djavabuilders.dev.src)
	 */
	public static String getDevSourceFolder() {
		return devSourceFolder;
	}

	/**
	 * @param devSourceFolder Development source folder. Allows to hot deploy YAML content without restarting the whole app
	 */
	public static void setDevSourceFolder(String devSourceFolder) {
		BuilderConfig.devSourceFolder = devSourceFolder;
	}
	
	private Map<Class<?>,Map<String,IPropertyHandler>>  propertyHandlers = new HashMap<Class<?>,Map<String,IPropertyHandler>>();
	
	protected Map<Class<?>,Map<String,IPropertyHandler>> getPropertyHandlers() {
		return propertyHandlers;
	}
	private Map<Class<?>,TypeDefinition> typeDefinitions = new HashMap<Class<?>, TypeDefinition>();
	private Map<String,Class<?>> typeAliases = new HashMap<String, Class<?>>();
	
	private boolean markInvalidResourceBundleKeys = true;

	//internal cache used to avoid re-creating the hierarchy of type definitions with every request
	//loaded lazily, upon demand
	private Map<Class<?>,Set<TypeDefinition>> typeDefinitionsForClassCache = new HashMap<Class<?>, Set<TypeDefinition>>();
	
	private IBackgroundProcessingHandler backgroundProcessingHandler = null;
	private IValidationMessageHandler validationMessageHandler = null;
	
	private Map<String,ICustomCommand<? extends Object>> customCommands = new HashMap<String, ICustomCommand<? extends Object>>();
	
	private Set<BuildListener> buildListeners = new LinkedHashSet<BuildListener>();
	
	private Map<String,Object> customProperties = new HashMap<String, Object>();
	
	private Map<String,Object> globals = new HashMap<String, Object>();
	
	private String namePropertyName = Builder.NAME;
	
	/**
	 * Constructor
	 * @param backgroundProcessingHandler Domain-specific background processing handler
	 */
	public BuilderConfig(IBackgroundProcessingHandler backgroundProcessingHandler, 
			IValidationMessageHandler validationMessageHandler, ICustomCommand<Boolean> confirmCommand) {
		
		this.backgroundProcessingHandler = backgroundProcessingHandler;
		this.validationMessageHandler = validationMessageHandler;

		customCommands.put(Builder.CONFIRM_CUSTOM_COMMAND, confirmCommand);
		
		addType(Builder.BIND, BuilderBindings.class);
		addType(Builder.VALIDATE, BuilderValidators.class);

		forType(Class.class).valueHandler(ClassAsValueHandler.getInstance());
		forType(BuilderValidators.class).typeHandler(DefaultValidatorTypeHandler.getInstance());
		
		//handler for static final int constants
		forType(int.class).valueHandler(IntegerAsValueHandler.getInstance());
		forType(int[].class).valueHandler(IntArrayAsValueHandler.getInstance());
		forType(Integer[].class).valueHandler(IntegerArrayAsValueHandler.getInstance());
		
		//default custom commands
		addCustomCommand(Builder.VALIDATE_CUSTOM_COMMAND, new ICustomCommand<Boolean>() {
			public Boolean process(BuildResult result, Object source) {
				return result.validate();
			}
		});
	}
	
	/**
	 * Defines metadata about a type, e.g.<br/>
	 * <code>
	 * defineType(JFrame.class).requires("name","text").requires(LayoutManager.class).delay(LayoutManager.class);
	 * @param applicableClass Class to which this type definition applies
	 * @return The created type definition so that it can be used via the Builder pattern
	 */
	public TypeDefinition forType(Class<?> applicableClass) {
		if (applicableClass == null) {
			throw new NullPointerException("applicableClass cannot be null");
		}
		TypeDefinition def = null;
		//lazy creation
		if (typeDefinitions.containsKey(applicableClass)) {
			def = typeDefinitions.get(applicableClass);
		} else {
			def = new TypeDefinition(applicableClass);
			typeDefinitions.put(applicableClass, def);
			
			//every time a new type definition is created
			//let's clear the cache to be on the safe side
			typeDefinitionsForClassCache.clear();
		}
		return def;
	}
	
	/**
	 * Adds multiple classes with aliases that correspond to the class names,, e.g.
	 * <code>
	 * addType(JFrame.class, JButton.class);
	 * <code>
	 * which corresponds to the type names in the builder file, e.g.
	 * <code>
	 * <b>JFrame</b>:
	 *     name: myFrame
	 *     title: My Frame
	 *     <b>JButton</b>:
	 *         name: myButton
	 *         text: My Button
	 * </code>
	 * @param classTypes Class types
	 * @return BuilderConfig (for use in Builder pattern) 
	 */
	public BuilderConfig addType(Class<?>... classTypes) {
		for(Class<?> type : classTypes) {
			addType(type);
		}
		return this;
	}
	
	/**
	 * Adds an alias and the class type that corresponds to it
	 * (assuming both names are the same), e.g.
	 * <code>
	 * addType(JFrame.class);
	 * addType(JButton.class);
	 * <code>
	 * which corresponds to the type names in the builder file, e.g.
	 * <code>
	 * <b>JFrame</b>:
	 *     name: myFrame
	 *     title: My Frame
	 *     <b>JButton</b>:
	 *         name: myButton
	 *         text: My Button
	 * </code>
	 * @param classType Class type
	 * @return BuilderConfig (for use in Builder pattern) 
	 */
	public BuilderConfig addType(Class<?> classType) {
		addType(classType.getSimpleName(),classType);
		return this;
	}
	
	/**
	 * Adds an alias and the class type that corresponds to it, e.g.
	 * <code>
	 * addType("JFrame",JFrame.class);
	 * addType("JButton",JButton.class);
	 * <code>
	 * which corresponds to the type names in the builder file, e.g.
	 * <code>
	 * <b>JFrame</b>:
	 *     name: myFrame
	 *     title: My Frame
	 *     <b>JButton</b>:
	 *         name: myButton
	 *         text: My Button
	 * </code>
	 * @param alias Alias
	 * @param classType Class type
	 * @return BuilderConfig (for use in Builder pattern) 
	 */
	public BuilderConfig addType(String alias, Class<?> classType) {
		if (alias == null || alias.length() == 0) {
			throw new NullPointerException("alias cannot be null or empty");
		}
		if (classType == null) {
			throw new NullPointerException("classType cannot be null");
		}
		if (typeAliases.containsKey(alias)) {
			String error = String.format("Duplicate alias '%s' for class '%s'. One already exists for '%s'",
					alias,classType.getName(),(typeAliases.get(alias)).getName());
			throw new DuplicateAliasException(error);
		}
		typeAliases.put(alias, classType);
		return this;
	}
	
	/**
	 * Removes a class from the list of recognized types
	 * @param classType Class type
	 * @return
	 */
	public BuilderConfig removeType(Class<?> classType) {
		List<String> keys = new ArrayList<String>();
		for(String alias: typeAliases.keySet()) {
			Class<?> type = typeAliases.get(alias);
			if (type == classType) {
				keys.add(alias);
			}
		}
		//remove all flagged keys
		for(String key:keys) {
			typeAliases.remove(key);
		}
		return this;
	}

	/**
	 * Gets all the defined type definitions, by class
	 * @return
	 */
	public Collection<TypeDefinition> getTypeDefinitions() {
		return typeDefinitions.values();
	}
	
	/**
	 * Returns the exact type definition for a particular class
	 * @param classType Class type
	 * @return Type definition or null if none found
	 */
	public TypeDefinition getTypeDefinition(Class<?> classType) {
		return typeDefinitions.get(classType);
	}
	
	/**
	 * Gets a list of all the pertinent type definitions.
	 * @param classType Class type
	 * @return Type definitions, sorted by inheritance tree
	 */
	public Set<TypeDefinition> getTypeDefinitions(Class<?> classType) {
		if (classType == null) {
			throw new NullPointerException("classType cannot be null");
		}
		
		Set<TypeDefinition> defs = null;
		if (typeDefinitionsForClassCache.containsKey(classType)) {
			defs = typeDefinitionsForClassCache.get(classType);
		} else {
			//first request - lazy creation
			defs = new TreeSet<TypeDefinition>(new TypeDefinitionClassHierarchyComparator());
			
			//classes
			Class<?> superClass = classType;
			while (superClass != null) {
				if (typeDefinitions.containsKey(superClass)) {
					defs.add(typeDefinitions.get(superClass));
				}			
				superClass = superClass.getSuperclass();
			}
			
			//interfaces
			Class<?>[] interfaces = classType.getInterfaces();
			for(Class<?> interfaceType : interfaces) {
				superClass = interfaceType;
				while (superClass != null) {
					if (typeDefinitions.containsKey(superClass)) {
						defs.add(typeDefinitions.get(superClass));
					}			
					superClass = superClass.getSuperclass();
				}
			}
			
			typeDefinitionsForClassCache.put(classType, defs);
		}
		
		return defs;
	}
	
	/**
	 * Indicates if a class is defined as a type
	 * @param classType Class type
	 * @return
	 */
	public boolean isTypeDefined(Class<?> classType) {
		return typeDefinitions.containsKey(classType);
	}
	
	/**
	 * Returns the class type associated with a particular alias. Null if none found.
	 * Should never be called directly, use BuilderUtils.getClassFromAlias() instead.
	 * @param alias Alias
	 * @return Class (null if none found)
	 */
	public Class<?> getClassType(String alias)  {
		Class<?> classType = typeAliases.get(alias);
		return classType;
	}
	
	/**
	 * Checks the raw data (before an object has been even handled) to extract its name, 
	 * if it has been specified
	 * @param data Raw parses data
	 * @return Name or null if not found
	 */
	public String getNameIfAvailable(Map<String,Object> data) {
		String name = (String) data.get(namePropertyName);
		return name;
	}
	
	/**
	 * Returns the name of an object instance
	 * @param instance
	 * @return Object name
	 * @throws ConfigurationException 
	 */
	public String getObjectName(Object instance) throws ConfigurationException {
		String name = null;
		try {
			Object value = PropertyUtils.getProperty(instance, namePropertyName);
			if (value != null) {
				name = String.valueOf(value);
			}
		} catch (Exception ex) {
			//ignore
		}
		
		return name;
	}
	
	/**
	 * Defines the property name that will be used for defining named objects (usually "name").
	 * Even if class type does not have a name property, the builder will handle it transparently
	 */
	public void setNamePropertyName(String namePropertyName) {
		this.namePropertyName = namePropertyName;
	}
	
	
	/**
	 * @return The name of the property used to define names (usually "name")
	 */
	public String getNamePropertyName() {
		return this.namePropertyName;
	}

	/**
	 * Returns the flag that controls if resource keys in the builder file
	 * are surrounded with "#" if not found in the list of ResourceBundles
	 * @return The markInvalidResourceBundleKeys flag
	 */
	public boolean isMarkInvalidResourceBundleKeys() {
		return markInvalidResourceBundleKeys;
	}

	/**
	 * Sets the flag that controls if resource keys in the builder file
	 * are surrounded with "#" if not found in the list of ResourceBundles
	 * @param markInvalidResourceBundleKeys The markInvalidResourceBundleKeys flag
	 */
	public void setMarkInvalidResourceBundleKeys(
			boolean markInvalidResourceBundleKeys) {
		this.markInvalidResourceBundleKeys = markInvalidResourceBundleKeys;
	}

	/**
	 * @return The domain-specific background processing handler
	 */
	public IBackgroundProcessingHandler getBackgroundProcessingHandler() {
		return backgroundProcessingHandler;
	}

	/**
	 * @param backgroundProcessingHandler The domain-specific background processing handler
	 */
	public void setBackgroundProcessingHandler(
			IBackgroundProcessingHandler backgroundProcessingHandler) {
		this.backgroundProcessingHandler = backgroundProcessingHandler;
	}

	/**
	 * @return the domain-specific validation message handler
	 */
	public IValidationMessageHandler getValidationMessageHandler() {
		return validationMessageHandler;
	}

	/**
	 * @param validationMessageHandler the domain-specific validation message handler to set
	 */
	public void setValidationMessageHandler(
			IValidationMessageHandler validationMessageHandler) {
		this.validationMessageHandler = validationMessageHandler;
	}
	
	/**
	 * Allows adding of custom commands
	 * @param globalName Global name
	 * @param command Command
	 * @return Current config
	 */
	public BuilderConfig addCustomCommand(String globalName, ICustomCommand<Boolean> command)  {
		BuilderUtils.validateNotNullAndNotEmpty("globalName", globalName);
		BuilderUtils.validateNotNullAndNotEmpty("command", command);
		
		if (globalName.matches(CUSTOM_COMMAND_REGEX)) {

			if (customCommands.containsKey(globalName)) {
				throw new BuildException("A custom command with the global name " + globalName + " is already defined"); 
			} else {
					customCommands.put(globalName, command);
			}

		} else {
			throw new BuildException(globalName + " is not a valid custom command name. Must start with '$'");
		}
		
		return this;
	}
	
	/**
	 * @return Custom commands
	 */
	public Map<String,ICustomCommand<? extends Object>> getCustomCommands() {
		return customCommands;
	}

	/**
	 * @return Global resource bundles
	 */
	public Set<ResourceBundle> getResourceBundles() {
		return bundles;
	}
	
	/**
	 * Gets string resource from the specified bundles
	 * @param key Key
	 * @return String (or null if none found)
	 */
	public String getResource(String key) {
		String value = null;
		for(ResourceBundle  bundle : getResourceBundles()) {
			if (bundle.containsKey(key)) {
				value = bundle.getString(key);
				break;
			}
		}
		return value;
	}
	
	/**
	 * Add a global resource bundle
	 * @param resourceBundleName Bundle name
	 */
	public void addResourceBundle(String resourceBundleName) {
		getResourceBundles().add(ResourceBundle.getBundle(resourceBundleName));
	}

	/**
	 * Add a global resource bundle
	 * @param resourceBundle Bundle 
	 */
	public void addResourceBundle(ResourceBundle resourceBundle) {
		getResourceBundles().add(resourceBundle);
	}
	
	/**
	 * Adds a build listener
	 * @param listener Build listener
	 */
	public void addBuildListener(BuildListener listener) {
		buildListeners.add(listener);
	}
	
	/**
	 * Removes a build listener
	 * @param listener Build listener
	 */
	public void removeBuildListener(BuildListener listener) {
		if (buildListeners.contains(listener)) {
			buildListeners.remove(listener);
		}
	}
	
	/**
	 * @return Build listeners
	 */
	public BuildListener[] getBuildListeners() {
		return buildListeners.toArray(new BuildListener[0]);
	}

	/**
	 * Gets the collection of custom properties that allow to store any additional domain-specific settings
	 * @return the customProperties
	 */
	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}
	
	/**
	 * Factory method that should be overriden for each toolkit with the 
	 * property change support that is proper for that toolkit's threading rules
	 * @return Domain-specific property change support
	 */
	public PropertyChangeSupport createPropertyChangeSupport(Object source) {
		return new PropertyChangeSupport(source);
	}
	

	/**
	 * Adds a global variable
	 * @param name Name
	 * @param value Value
	 * @return This
	 */
	public BuilderConfig addGlobalVariable(String name, Object value) {
		BuilderUtils.validateNotNullAndNotEmpty("name", name);
		BuilderUtils.validateNotNullAndNotEmpty("value", value);
		
		if (name.matches(GLOBAL_VARIABLE_REGEX)) {

			if (globals.containsKey(globals)) {
				throw new BuildException("A global variable {0} already exists", name); 
			} else {
				globals.put(name, value);
			}

		} else {
			throw new BuildException("{0} is not a valid global variable. Must start with '$'", name);
		}
		
		return this;
	}
	
	/**
	 * Gets global variable value
	 * @param name Name
	 * @param expectedType Expected variable type
	 * @return Value
	 */
	public Object getGlobalVariable(String name, Class<?> expectedType)  {
		BuilderUtils.validateNotNullAndNotEmpty("name", name);
		BuilderUtils.validateNotNullAndNotEmpty("expectedType", expectedType);
		
		Object value = globals.get(name);
		if (value == null) {
			throw new BuildException("Global variable {0} is null",name);
		}
		if (!expectedType.isAssignableFrom(value.getClass())) {
			throw new BuildException("Global variable {0} is not compatible with expected type {1}",
					name, expectedType);
		}
		
		return value;
	}
	
	/**
	 * @deprecated Use forType(Class<?>).typeHandler() instead
	 */
	public BuilderConfig addTypeHandler(ITypeHandler typeHandler) {
		forType(typeHandler.getApplicableClass()).typeHandler(typeHandler);
		return this;
	}
	
	/**
	 * @deprecated Use TypeDefinition.getTypeHandler(BuilderConfig, Class) instead
	 */
	public ITypeHandler getTypeHandler(Class<?> classType) {
		return TypeDefinition.getTypeHandler(this, classType);
	}

	/**
	 * @deprecated use forType(Class).propertyHandler() instead
	 */
	public BuilderConfig addPropertyHandler(Class<?> type, IPropertyHandler handler) {
		forType(type).propertyHandler(handler);
		return this;
	}

	/**
	 * @deprecated user TypeDefinition.getPropertyHandler(BuilderConfig,Class,String) instead
	 */
	public IPropertyHandler getPropertyHandler(Class<?> classType, String key) {
		return TypeDefinition.getPropertyHandler(this, classType, key);
	}

}
