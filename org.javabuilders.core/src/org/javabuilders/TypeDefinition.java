/**
 * 
 */
package org.javabuilders;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javabuilders.handler.ITypeAsValueHandler;
import org.javabuilders.handler.ITypeHandler;
import org.javabuilders.handler.ITypeHandlerAfterCreationProcessor;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.layout.DefaultResize;

/**
 * Defines the metadata for a specific type
 * Used when instantiating new objects
 * @author Jacek Furmankiewicz
 */
public class TypeDefinition implements IKeyValueConsumer {

	public static final Integer DEFAULT_DELAY_WEIGHT = 1000;
	public final static Logger logger = Logger.getLogger(TypeDefinition.class.getSimpleName());
	
	/*
	 * STATIC HELPER METHODS
	 */
	
	/**
	 * Gets the delayed weight for a type. 0 means no delay
	 * @param handler Handler
	 * @param typeDefinitions Type definitions
	 * @return Delay weight
	 */
	public static Integer getDelayedWeight(ITypeHandler handler, Collection<TypeDefinition> typeDefinitions) {
		Integer delayedWeight = 0;
		
		root:
		for(TypeDefinition def : typeDefinitions) {
			Map<Class<?>,Integer> delayedClasses = def.getDelayedTypes();
			for(Class<?> delayedClass : delayedClasses.keySet()) {
				if (delayedClass.isAssignableFrom(handler.getApplicableClass())) {
					delayedWeight = delayedClasses.get(delayedClass);
					//take the delayed weight from the lowest object in the hierarchy
					break root;
				}
			}
		}
		
		return delayedWeight;
	}
	
	/**
	 * Gets the delayed weight for a type's property. 0 means no delay
	 * @param handler Handler
	 * @param typeDefinitions Type definitions
	 * @return Delay weight
	 */
	public static Integer getDelayedWeight(ITypeHandler handler, String property, Collection<TypeDefinition> typeDefinitions) {
		Integer delayedWeight = 0;
		
		for(TypeDefinition def : typeDefinitions) {
			Map<String,Integer> delayedProperties = def.getDelayedProperties();
			if (delayedProperties.containsKey(property)) {
				delayedWeight = delayedProperties.get(property);
				break;
			}
		}
		
		return delayedWeight;
	}
	
	
	/**
	 * Returns if a property cannot be localized or not
	 * @param propertyName Property name
	 * @param typeDefinitions Type definitions for the object instance
	 * @return true if localizable, false if not
	 */
	public static boolean isLocalizableProperty(String propertyName, Collection<TypeDefinition> typeDefinitions) {
		boolean isLocalizable = false;
		
		for(TypeDefinition def : typeDefinitions) {
			if (def.isLocalized(propertyName)) {
				isLocalizable = true;
				break;
			}
		}
		
		return isLocalizable;
	}
	
	/**
	 * Returns if the current parent of the node is of an allowed type
	 * @param parent Parrent
	 * @param typeDefinitions List of applicable type definitions
	 * @return
	 */
	public static boolean isParentAllowed(Object parent, Collection<TypeDefinition> typeDefinitions) {
		boolean isAllowed = true;

		for(TypeDefinition def : typeDefinitions) {
			if (def.isParentAllowed(parent)) {
				isAllowed = true;
				break;
			}
		}

		
		return isAllowed;
	}
	
	/**
	 * Returns the list of allowed parent types for a class
	 * @param config Builder config
	 * @param classType Class type
	 * @return Set of allowed parent types
	 */
	public static Set<Class<?>> getAllowedParents(BuilderConfig config, Class<?> classType) {
		Set<Class<?>> allowed = new HashSet<Class<?>>();
		
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			for(Class<?> parentClass : def.getAllowedParents()) {
				allowed.add(parentClass);
			}
		}
		
		return allowed;
	}
	
	/**
	 * Returns a set of all the required keys
	 * @return List of required keys
	 */
	public static Set<String> getRequiredKeys(BuilderConfig config, Class<?> classType) {
		Set<String> keys = new HashSet<String>();
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			keys.addAll(def.getRequiredKeys());
		}
		return keys;
	}
	
	/**
	 * Returns a set of all the required types
	 * @return List of required keys
	 */
	public static Set<Class<?>> getRequiredTypes(BuilderConfig config, Class<?> classType) {
		Set<Class<?>> keys = new HashSet<Class<?>>();
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			keys.addAll(def.getRequiredTypes());
		}
		return keys;
	}
	
	/**
	 * Returns a list of all the defaults for this type
	 * @param config Config
	 * @param classType Class type	
	 * @return List of defaults
	 */
	public static Map<String,Object> getDefaults(BuilderConfig config, Class<?> classType) {
		Map<String,Object> map = new HashMap<String, Object>();
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			map.putAll(def.getDefaults());
		}
		return map;
	}
	
	/**
	 * Returns a set of all ignored properties
	 * @param config Config
	 * @param classType Class Type
	 * @return Set of ignored property names
	 */
	public static Set<String> getIgnored(BuilderConfig config, Class<?> classType) {
		Set<String> ignored = new HashSet<String>();
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			ignored.addAll(def.getAllIgnored());
			
		}
		return ignored;
	}
	
	/**
	 * Gets the default resize policy for a type
	 * @param config Config
	 * @param classType Class type
	 * @return Default resize policy
	 */
	public static DefaultResize getDefaultResize(BuilderConfig config, Class<?> classType) {
		DefaultResize resize = DefaultResize.NONE;
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			resize = def.getDefaultResize();
			break;
		}
		return resize;
	}
	
	/**
	 * Gets the default resize policy for a type
	 * @param config Config
	 * @param parentClassType Parent class type (the type where the method will actually be invoked)
	 * @param classType Class type (the child type of the parent that may map to a method on the parent)
	 * @return Method (or null if none found)
	 */
	public static Method getTypeAsMethod(BuilderConfig config, Class<?> parentClassType, Class<?> classType) {
		Method target = null;
		for(TypeDefinition def : config.getTypeDefinitions(parentClassType)) {
			for(Class<?> mappedType : def.getTypesAsMethods().keySet()) {
				if (mappedType.isAssignableFrom(classType)) {
					target = def.getTypesAsMethods().get(mappedType);
					break;
				}
			}
		}
		return target;
	}
	
	/**
	 * Gets the list of finish-processors for a class
	 * @param config Config
	 * @param classType Class type
	 * @return List of finish processors
	 */
	public static List<ITypeHandlerFinishProcessor> getFinishProcessors(BuilderConfig config, Class<?> classType) {
		List<ITypeHandlerFinishProcessor> list = new LinkedList<ITypeHandlerFinishProcessor>();
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			if (def.getFinishProcessor() != null) {
				list.add(def.getFinishProcessor());
			}
		}
		return list;
	}

	/**
	 * Gets the list of after creation-processors for a class
	 * @param config Config
	 * @param classType Class type
	 * @return List of finish processors
	 */
	public static List<ITypeHandlerAfterCreationProcessor> getAfterCreationProcessors(BuilderConfig config, Class<?> classType) {
		List<ITypeHandlerAfterCreationProcessor> list = new LinkedList<ITypeHandlerAfterCreationProcessor>();
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			if (def.getAfterCreationProcessor() != null) {
				list.add(def.getAfterCreationProcessor());
			}
		}
		return list;
	}

	
	/**
	 * Gets the "type as value" handler, if defined
	 * @param config Config
	 * @param classType Class type
	 * @return  "type as value" handler, or null if none
	 */
	public static ITypeAsValueHandler<? extends Object> getTypeAsValueHandler(BuilderConfig config, Class<?> classType) {
		
		ITypeAsValueHandler<? extends Object> handler = null;
		
		//special handling for enums - creates type as value handlers for them automatically
		if (classType.isEnum()) {
			TypeDefinition def = config.getTypeDefinition(classType);
			if (def == null || def.getTypeAsValueHandler() == null) {
				//create a default "type as value" handler for any enum that is encountered during the build process
				config.forType(classType).valueHandler(createEnumTypeAsValueHandler(classType));
				if (logger.isLoggable(Level.INFO)) {
					logger.log(Level.INFO,"Created ITypeAsValueHandler instance for " + classType.getName());
				}
			}
		}
		
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			if (def.getTypeAsValueHandler() != null) {
				handler = def.getTypeAsValueHandler();
				break;
			}
		}
		return handler;
	}
	
	/**
	 * Creates a "value as type" handler for an enum automatically
	 * @param enumClass Enum class
	 * @return Handler or null if class not an enum
	 */
	public static ITypeAsValueHandler<? extends Object> createEnumTypeAsValueHandler(final Class<?> enumClass) {
		ITypeAsValueHandler<? extends Object> handler = null;
		
		if (enumClass.isEnum()) {
			Object[] constants = enumClass.getEnumConstants();
			final Map<String,Object> values = new HashMap<String, Object>();
			final StringBuilder regexBuilder = new StringBuilder();
			for(Object constant : constants) {
				//allow both the constant and it's more Java-like version to be used as an alias
				values.put(constant.toString(), constant);
				String shortConstant = getShortEnumConstant(constant); 
				values.put(shortConstant, constant);
				if (regexBuilder.length() > 0) {
					regexBuilder.append("|");
				}
				regexBuilder.append(constant).append("|").append(shortConstant);
			}
			final String regex = regexBuilder.toString();
			
			//create new handler dynamically
			handler = new ITypeAsValueHandler<Object>() {

				public String getInputValueSample() {
					return values.toString();
				}

				public String getRegex() {
					return regex;
				}

				public Object getValue(BuildProcess process,Node node,String key,Object inputValue) throws BuildException{
					return values.get(inputValue);
				}

				public Class<?> getApplicableClass() {
					return enumClass;
				}
				
			};
		}
		
		return handler;
	}
	
	//returns the Java-like representation of an enum constant (e.g. "DIRECTION_LEFT" would become "directionLeft" and "DirectionLeft" would become "directonLeft")
	public static String getShortEnumConstant(Object constant) {
		String sConstant = constant.toString();
		StringBuilder builder = new StringBuilder(sConstant.length());
		
		if (sConstant.equals(sConstant.toUpperCase())) {
			//constant style enum value, e.g. "DIRECTION_LEFT"
			
			String[] parts = sConstant.split("_");
			for(int p = 0; p < parts.length; p++) {
				String part = parts[p];
				
				if (p == 0) {
					builder.append(part.toLowerCase());
				} else {
					if (part.length() > 0) {
						builder.append(part.substring(0,1).toUpperCase());
						if (part.length() > 1) {
							builder.append(part.substring(1).toLowerCase());
						}
					}
				}
			}
		} else {
			//new-school style enum value, e.g. "DirectionLeft" => "directionLeft"
			builder.append(sConstant.substring(0,1).toLowerCase());
			builder.append(sConstant.substring(1));
		}
		
		return builder.toString();
	}
	
	/**
	 * Gets the real or mapped property value
	 * @param config Config
	 * @param classType Class type
	 * @return Property value
	 */
	public static Object getPropertyValue(BuilderConfig config, Class<?> classType, String propertyName, Object value) throws BuildException {
		
		Object returnValue = value; //default
		
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			Map<String,? extends Object> mappedValues = def.getMappedPropertyValues(propertyName);
			if (mappedValues != null) {
				if (mappedValues.containsKey(value)) {
					returnValue = mappedValues.get(value);
					break;
				} else {
					//this property is flagged as mapped, but the value in the YAML file is not defined
					throw new BuildException("Invalid value \"{0}\"  for {1}.{2}. Allowed values are: {3}",value, classType.getSimpleName(),propertyName,
							mappedValues.keySet());
				}
			}
		}
		
		return returnValue;
	}
	
	/**
	 * Gets the alias for a property, if defined
	 * @param config Config
	 * @param classType Class type
	 * @param propertyName Property name
	 * @return Alias or null if none defined
	 */
	public static String getPropertyForAlias(BuilderConfig config, Class<?> classType, String alias)  {
		String actual = null;
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			actual = def.getPropertyForAlias(alias);
			if (actual != null) {
				//stop on the first alias definition
				break;
			}
		}
		
		return actual;
	}
	
	/**
	 * Gets the value of a custom property, if defined
	 * @param config Config
	 * @param classType Class type
	 * @param propertyName Property name
	 * @return Custom property value or null if none defined
	 */
	public static Object getCustomProperty(BuilderConfig config, Class<?> classType, String propertyName)  {
		Object value = null;
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			if (def.getCustomProperties().containsKey(propertyName))  {
				value = def.getCustomProperties().get(propertyName);
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * Checks if a property should be treated as a list.
	 * @param config Config
	 * @param classType Class type
	 * @param propertyName Property name
	 * @return true/false
	 */
	public static Object isList(BuilderConfig config, Class<?> classType, String propertyName) {
		boolean list = false;
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			if (def.isList(propertyName)) {
				list = true;
				break;
			}
		}
		return list;
	}
	
	/**
	 * Tries to see if there is any class with static int constants mapped to a particular property name
	 * @param config Config
	 * @param classType Class type
	 * @param propertyName Property name
	 * @return Class or null if none found
	 */
	public static Class<?> getPropertyConstantsClass(BuilderConfig config, Class<?> classType, String propertyName) {
		Class<?> constants = null;
		for(TypeDefinition def : config.getTypeDefinitions(classType)) {
			constants = def.getPropertyConstants(propertyName);
			if (constants != null) {
				break;
			}
		}
		return constants;
	}
	
	
	/*
	 * INSTANCE METHODS
	 */
	
	private Class<?> applicableClass = null;
	private Set<String> requiredKeys = new HashSet<String>();
	private Set<Class<?>> requiredTypes = new HashSet<Class<?>>();
	private Map<Class<?>,Integer> delayedTypes  = new HashMap<Class<?>,Integer>();
	private Map<String,Integer> delayedProperties = new HashMap<String,Integer>();
	private Set<String> localizedProperties = new HashSet<String>();
	private Set<Class<?>> allowedParents = new HashSet<Class<?>>();
	private Map<String,Object> defaults = new HashMap<String,Object>();
	private Set<String> ignoredProperties = new HashSet<String>();
	private DefaultResize defaultResize = DefaultResize.NONE;
	private Map<Class<?>,Method> typesAsMethods = new HashMap<Class<?>, Method>();
	private ITypeHandlerFinishProcessor finishProcessor;
	private ITypeHandlerAfterCreationProcessor afterCreationProcessor;
	private ITypeAsValueHandler<? extends Object> typeAsValueHandler;
	private Map<String,String> propertyAliases = new HashMap<String, String>();
	private Map<String,Map<String, ? extends Object>> mappedProperties = new HashMap<String, Map<String,? extends Object>>();
	private Map<String,Object> customProperties = new HashMap<String,Object>();
	private List<String> propertiesAsList = new ArrayList<String>();
	private Map<String,Class<?>> propertyConstants = new HashMap<String, Class<?>>();
	
	/**
	 * Constructor
	 */
	public TypeDefinition(Class<?> applicableClass) {
		if (applicableClass == null) {
			throw new NullPointerException("applicableClass cannot be null");
		}
		this.applicableClass = applicableClass;
	}
	
	/**
	 * @return The applicable class
	 */
	public Class<?> getApplicableClass() {
		return applicableClass;
	}

	/**
	 * Returns a set of all the required types
	 * @param defs
	 * @return
	 */
	public Set<Class<?>> getRequiredTypes(List<TypeDefinition> defs) {
		Set<Class<?>> types = new HashSet<Class<?>>();
		for(TypeDefinition def : defs) {
			types.addAll(def.requiredTypes);
		}
		return types;
	}

	
	/**
	 * @return The required keys
	 */
	public Set<String> getRequiredKeys() {
		return requiredKeys;
	}
	
	/**
	 * @return The required types
	 */
	public Set<Class<?>> getRequiredTypes() {
		return requiredTypes;
	}
	
	/**
	 * Utility method to make adding required keys easier using the Builder pattern, e.g.
	 * <code>
	 * type.requires("name","text").requires(LayoutManager.class);
	 * </code>
	 * @param keys List of required keys
	 * @return Current instance, for use by Builder pattern
	 */
	public TypeDefinition requires(String...keys) {
		for(String key : keys) {
			getRequiredKeys().add(key);
		}
		return this;
	}
	
	/**
	 * Utility method to make adding required keys easier using the Builder pattern
	 * <code>
	 * type.requires("name","text").requires(LayoutManager.class);
	 * </code>
	 * @param types List of required class types
	 * @return Current instance, for use by Builder pattern
	 */
	public TypeDefinition requires(Class<?>...types) {
		for(Class<?> type : types) {
			getRequiredTypes().add(type);
		}
		return this;
	}
	
	
	/**
	 * Utility method to make adding delayed types easier using the Builder pattern
	 * <code>
	 * type.requires("name","text").requires(LayoutManager.class).delay(LayoutManager.class);
	 * </code>
	 * @param types List of required class types
	 * @return Current instance, for use by Builder pattern
	 */
	public TypeDefinition delay(int delayWeight, Class<?>...types) {
		for(Class<?> type : types) {
			getDelayedTypes().put(type,delayWeight);
		}
		return this;
	}
	
	/**
	 * Utility method to make adding delayed types easier using the Builder pattern
	 * <code>
	 * type.requires("name","text").requires(LayoutManager.class).delay(LayoutManager.class);
	 * </code>
	 * @param delayWeight Delay weight. Allows to control the order in which propertes are delayed. The ones with the
	 *                    lower weight get executed first.
	 * @param types List of required class types
	 * @return Current instance, for use by Builder pattern
	 */
	public TypeDefinition delay(Class<?>...types) {
		return delay(DEFAULT_DELAY_WEIGHT,types);
	}

	/**
	 * Utility method to make adding delayed properties easier using the Builder pattern
	 * <code>
	 * type.requires("name","text").requires(LayoutManager.class).delay("MigLayout");
	 * </code>
	 * @param delayWeight Delay weight. Allows to control the order in which propertes are delayed. The ones with the
	 *                    lower weight get executed first.
	 * @param properties List of required class types
	 * @return Current instance, for use by Builder pattern
	 */
	public TypeDefinition delay(int delayWeight, String...properties) {
		for(String property : properties) {
			getDelayedProperties().put(property, delayWeight);
		}
		return this;
	}
	
	/**
	 * Utility method to make adding delayed properties easier using the Builder pattern
	 * <code>
	 * type.requires("name","text").requires(LayoutManager.class).delay("MigLayout");
	 * </code>
	 * @param properties List of required class types
	 * @return Current instance, for use by Builder pattern
	 */
	public TypeDefinition delay(String...properties) {
		return delay(DEFAULT_DELAY_WEIGHT,properties);
	}

	
	/**
	 * Gets the list of types whose processing should be delayed till others are processed
	 * first, including their delay weights. <br/>
	 * Typical example is a LayoutManager, which should only be processed after all
	 * the other components are instantiated.
	 * @return The delayed types
	 */
	public Map<Class<?>,Integer> getDelayedTypes() {
		return delayedTypes;
	}
	
	/**
	 * Gets the list of properties whose processing should be delayed till others are processed
	 * first, including their delay weights <br/>
	 * Typical example is a LayoutManager, which should only be processed after all
	 * the other components are instantiated.
	 * @return The delayed types list
	 */
	public Map<String,Integer> getDelayedProperties() {
		return delayedProperties;
	}

	/**
	 * IKeyValueConsumer implementation.<br/>
	 * Returns the list of keys that are consumed during the creation of a type<br/>
	 * (e.g. the 'name' and 'text' keys are always consumed when creating a new JButton)
	 */
	public Set<String> getConsumedKeys() {
		return requiredKeys;
	}
	
	/**
	 * Defines a property as being potentially localizable (e.g. "text", "title", etc.)
	 * @param propertyNames
	 * @return Same instance, for use in Builder pattern
	 */
	public TypeDefinition localize(String... propertyNames) {
		for(String propertyName : propertyNames) {
			localize(propertyName);
		}
		return this;
	}
	
	/**
	 * Defines a property as being potentially localizable (e.g. "text", "title", etc.)
	 * @param propertyName
	 * @return Same instance, for use in Builder pattern
	 */
	public TypeDefinition localize(String propertyName) {
		localizedProperties.add(propertyName);
		return this;
	}
	
	/**
	 * Returns if a property was defined as being potentially localizable
	 * @param propertyName
	 * @return
	 */
	public boolean isLocalized(String propertyName) {
		return localizedProperties.contains(propertyName);
	}
	
	/**
	 * Defines a property as one that is ignored (i.e. processed differently somehow)
	 * @param propertyNames Property names to ignore
	 * @return Current type definition, for use in Builder pattern
	 */
	public TypeDefinition ignore(String... propertyNames) {
		for (String propertyName : propertyNames) {
			ignore(propertyName);
		}
		return this;
	}
	/**
	 * Defines a property as one that is ignored (i.e. processed differently somehow)
	 * @param propertyName Property name
	 * @return Current type definition, for use in Builder pattern
	 */
	public TypeDefinition ignore(String propertyName) {
		ignoredProperties.add(propertyName);
		return this;
	}
	
	/**
	 * Returns if a property should be ignored and not processed
	 * @param propertyName Property name
	 * @return True if ignored, false if to be processed
	 */
	public boolean isIgnored(String propertyName) {
		return ignoredProperties.contains(propertyName);
	}
	
	/**
	 * Returns all ignored properties
	 * @return Set of properties
	 */
	public Set<String> getAllIgnored() {
		return ignoredProperties;
	}

	/**
	 * Defines the parent that is allowed to contain the current type
	 * (e.g. a JMenuBar can only be created underneath a JFrame)
	 * @param parentTypes Parent class types
	 * @return Same instance, for use in Builder pattern
	 */
	public TypeDefinition allowParent(Class<?>... parentTypes) {
		
		for(Class<?> parentType : parentTypes) {
			if (parentType == null) {
				throw new NullPointerException("parentType cannot be null");
			}
			allowedParents.add(parentType);
		}
		return this;
	}
	
	/**
	 * Returns the list of allowed parent types
	 * @return Allowed parent types
	 */
	public Set<Class<?>> getAllowedParents() {
		return allowedParents;
	}
	
	/**
	 * Returns if the parent instance is allowed for this type
	 * @param parent Parent instance
	 * @return True if allowed, false if not
	 */
	public boolean isParentAllowed(Object parent) {
		if (parent == null) {
			throw new NullPointerException("parent cannot be null");
		}
		
		boolean allowed = false;
		for(Class<?> classType : allowedParents) {
			if (classType.isAssignableFrom(parent.getClass())){
				allowed = true;
				break;
			}
		}
		
		return allowed;
	}

	/**
	 * Adds a default value for this type.
	 * @param property Property name
	 * @param value Value
	 * @return Same instance, for use in Builder pattern
	 */
	public TypeDefinition defaultValue(String property, Object value) {
		if (property == null) {
			throw new NullPointerException("property cannot be null");
		}
		if (value == null) {
			throw new NullPointerException("value cannot be null");
		}
		defaults.put(property, value);
		return this;
	}
	
	/**
	 * Allows setting of the default resize policy
	 * @param resize Resize policy
	 * @return Type definition, for use in Builder pattern
	 */
	public TypeDefinition defaultResize(DefaultResize resize) {
		this.defaultResize = resize;
		return this;
	}
	
	/**
	 * @return Default resize policy
	 */
	public DefaultResize getDefaultResize() {
		return this.defaultResize;
	}
	
	/**
	 * Returns a list of all the defaults
	 * @return List of defaults for this type
	 */
	public Map<String,Object> getDefaults() {
		return defaults;
	}
	
	/**
	 * Maps a type to a method that that type should be passed into as a parameter, e.g.
	 * <code>"MigLayout: ..."<code>
	 * should automatically map to <code>setLayoutManager()</code>
	 * @param type Type
	 * @param methodName Method to call
	 * @return this
	 */
	public TypeDefinition typeAsMethod(Class<?> type, String methodName) {
		Method[] methods = getApplicableClass().getMethods();
		Method target = null;
		for(Method method : methods) {
			if (method.getName().equals(methodName) && method.getParameterTypes().length == 1 &&
					method.getParameterTypes()[0].isAssignableFrom(type)) {
				target = method;
				break;
			}
		}
		return typeAsMethod(type, target);
	}
	
	/**
	 * Maps a type to a method that that type should be passed into as a parameter, e.g.
	 * <code>"MigLayout: ..."<code>
	 * should automatically map to <code>setLayoutManager()</code>
	 * @param type Type
	 * @param method Method to call
	 * @return this
	 */
	public TypeDefinition typeAsMethod(Class<?> type, Method method) {
		BuilderUtils.validateNotNullAndNotEmpty("method", method);
		typesAsMethods.put(type,method);
		return this;
	}
	
	/**
	 * @return Methods that the types maps to
	 */
	public Map<Class<?>,Method> getTypesAsMethods() {
		return typesAsMethods;
	}
	
	/**
	 * Gets mapped property values (ie. when a value is an alias, e.g. "JFrame.EXIT_ON_CLOSE" is really just an int value of 1")
	 * @param propertyName Property name
	 * @return Map of mapped key/value [airs
	 */
	public Map<String,? extends Object> getMappedPropertyValues(String propertyName) {
		return mappedProperties.get(propertyName);
	}
	
	/**
	 * Sets the mapped property values for a particular property
	 * @param propertyName Property  name
	 * @param values Map of values
	 * @return This
	 */
	public TypeDefinition asMapped(String propertyName, Map<String, ? extends Object> values) {
		mappedProperties.put(propertyName, values);
		return this;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return applicableClass.getName();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return applicableClass.hashCode();
	}

	/**
	 * @return the finish processor
	 */
	public ITypeHandlerFinishProcessor getFinishProcessor() {
		return finishProcessor;
	}

	/**
	 * @param finishProcessor the finish processor to set
	 * @return This
	 */
	public TypeDefinition finishProcessor(ITypeHandlerFinishProcessor finishProcessor) {
		this.finishProcessor = finishProcessor;
		return this;
	}
	
	/**
	 * @return the after creation processor
	 */
	public ITypeHandlerAfterCreationProcessor getAfterCreationProcessor() {
		return afterCreationProcessor;
	}
	
	/**
	 * @param finishProcessor the finish processor to set
	 * @return This
	 */
	public TypeDefinition afterCreationProcessor(ITypeHandlerAfterCreationProcessor processor) {
		this.afterCreationProcessor =processor;
		return this;
	}
	

	/**
	 * @return the typeAsValueHandler
	 */
	public ITypeAsValueHandler<? extends Object> getTypeAsValueHandler() {
		return typeAsValueHandler;
	}

	/**
	 * @param typeAsValueHandler the typeAsValueHandler to set
	 */
	public TypeDefinition valueHandler(ITypeAsValueHandler<? extends Object> typeAsValueHandler) {
		this.typeAsValueHandler = typeAsValueHandler;
		return this;
	}
	
	/**
	 * Property alias
	 * @param propertyName
	 * @param alias
	 * @return
	 */
	public TypeDefinition propertyAlias(String propertyName, String alias) {
		propertyAliases.put(alias,propertyName);
		return this;
	}
	
	/**
	 * @param alias Property alias
	 * @return The real property the alias represents or null if none found
	 */
	public String getPropertyForAlias(String alias) {
		return propertyAliases.get(alias);
	}
	
	/**
	 * @return Custom properties
	 */
	public TypeDefinition customProperty(String propertyName, Object value) {
		getCustomProperties().put(propertyName,value);
		return this;
	}
	
	/**
	 * @return Custom properties
	 */
	public Map<String,Object> getCustomProperties() {
		return customProperties;
	}
	
	/**
	 * Defines a property that should automatically be converted to a list of
	 * particular type
	 * @param propertyName
	 * @param type
	 * @return
	 */
	public TypeDefinition asList(String propertyName) {
		propertiesAsList.add(propertyName);
		return this;
	}
	
	/**
	 * Returns if a property is supposed to be a list
	 * @param propertyName Property name
	 * @return true/false
	 */
	public boolean isList(String propertyName) {
		return propertiesAsList.contains(propertyName);
	}
	
	/**
	 * Maps a class with static int constants to a particular property. Workaround for various bad APIs 
	 * @param propertyName Property name
	 * @param constantsClass Constants class for it
	 * @return This
	 */
	public TypeDefinition propertyConstants(String propertyName, Class<?> constantsClass) {
		propertyConstants.put(propertyName, constantsClass);
		return this;
	}
	
	/**
	 * @param propertyName
	 * @return
	 */
	public Class<?> getPropertyConstants(String propertyName) {
		return propertyConstants.get(propertyName);
	}
	
	
}
