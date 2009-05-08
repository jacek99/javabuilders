/**
 * 
 */
package org.javabuilders;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.beanutils.PropertyUtils;
import org.javabuilders.annotations.BuildFile;
import org.javabuilders.event.BuildEvent;
import org.javabuilders.event.BuildListener;
import org.javabuilders.exception.InvalidFormatException;
import org.javabuilders.handler.GlobalVariablePropertyHandler;
import org.javabuilders.handler.IPropertyHandler;
import org.javabuilders.handler.ITypeAsValueHandler;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.handler.ITypeHandler;
import org.javabuilders.handler.ITypeHandlerAfterCreationProcessor;
import org.javabuilders.handler.ITypeHandlerFinishProcessor;
import org.javabuilders.util.BuilderUtils;
import org.javabuilders.util.ChildrenCardinalityUtils;
import org.jvyaml.YAML;

/**
 * Ancestor for all builders
 * @author Jacek Furmankiewicz
 */
public class Builder {
	
	private final static Logger logger = Logger.getLogger(Builder.class.getSimpleName());
	
	public final static Map<String,?> EMPTY_PROPERTIES = null;

	public final static String NAME = "name";

	/**
	 * The standard property names used throughout the builder types
	 */
	public final static String CONTENT = "content";
	/**
	 * The standard property name used for layout constraints
	 */
	public final static String CONSTRAINTS = "constraints";
	
	/**
	 * The standard property name used to indicate a binding node
	 */
	public final static String BIND = "bind";
	/**
	 * The standard property name used to indicate a validation node
	 */
	public final static String VALIDATE = "validate";

	/**
	 * The standard property name used for storing a single value that represents an entire object
	 */
	public final static String VALUE = "value";
	
	/**
	 * The standard property name used for visually laying out components in a YAML file
	 */
	public final static String LAYOUT = "layout";
	
	/**
	 * The standard property name used to indicate on onAction event
	 */
	public final static String ON_ACTION = "onAction";
	
	/**
	 * The standard property name used to indicate on onFocus event
	 */
	public final static String ON_FOCUS = "onFocus";

	/**
	 * The standard property name used to indicate on onFocusLost event
	 */
	public final static String ON_FOCUS_LOST = "onFocusLost";
	/**
	 * Reserved keyword used to indicate the caller : "this"
	 */
	public final static String THIS = "this";
	/**
	 * Prefix for named objects($), e.g. "$$saveButton"
	 */
	public final static String NAMED_OBJECT_REGEX = "\\$\\{[a-zA-Z0-9]+\\}";
	private final static int NAMED_OBJECT_PREFIX_LENGTH = 2;
	private final static int NAMED_OBJECT_SUFFIX_LENGTH = 1;
	
	/**
	 * Name of the $validate custom command
	 */
	public final static String VALIDATE_CUSTOM_COMMAND = "$validate";
	/**
	 * Name of the $confirm custom command
	 */
	public final static String CONFIRM_CUSTOM_COMMAND = "$confirm";
	
	/**
	 * String boolean false
	 */
	public final static String BOOLEAN_FALSE = "false";
	
	/**
	 * @return Standard JavaBuilders resource bundle
	 */
	public static ResourceBundle getResourceBundle() {
		ResourceBundle bundle = ResourceBundle.getBundle("org/javabuilders/Resources");
		return bundle;
	}
	
	/**
	 * Builds assuming the root object has already been instantiated
	 * (e.g. for loading from within the constructor of an object and creating
	 * it dynamically at that time)
	 * @param caller The caller
	 * @return Build result
	 * @throws IOException 
	 */
	public static BuildResult build(BuilderConfig config, Object caller, ResourceBundle...resourceBundles)  {
		return build(config, caller, EMPTY_PROPERTIES, resourceBundles);
	}
	
	/**
	 * Builds assuming the root object has already been instantiated
	 * (e.g. for loading from within the constructor of an object and creating
	 * it dynamically at that time)
	 * @param caller The caller
	 * @param customProperties Optional custom properties
	 * @return Build result
	 * @throws IOException 
	 */
	public static BuildResult build(BuilderConfig config,Object caller, Map<String, ?> customProperties, 
			ResourceBundle...resourceBundles)  {
		
		Class<?> type = caller.getClass();
		String fileName = null;

		//@BuildFile annotation overrides any convention over configuration
		if (type.isAnnotationPresent(BuildFile.class)) {
			fileName = type.getAnnotation(BuildFile.class).value();
		} else {
		
			Class<?> declaringType = type.getDeclaringClass();
			if (declaringType == null) {
				fileName = type.getSimpleName() + ".yaml";
			} else {
				//build a nested name from the class hierarchy
				StringBuilder bld = new StringBuilder(type.getSimpleName());
				while (declaringType != null) {
					bld.insert(0, declaringType.getSimpleName());
					bld.insert(declaringType.getSimpleName().length(),".");
					declaringType = declaringType.getDeclaringClass();
				}
				bld.append(".yaml");
				fileName = bld.toString();
			}
		}
		
		
		BuildResult result;
		result = build(config, caller, fileName, customProperties, resourceBundles);
		return result;
	}
	
	/**
	 * Builds assuming the root object has already been instantiated
	 * (e.g. for loading from within the constructor of an object and creating
	 * it dynamically at that time)
	 * @param caller The caller
	 * @return Build result
	 * @throws IOException 
	 * @throws BuildException 
	 */
	public static BuildResult build(BuilderConfig config,Object caller, String fileName, ResourceBundle...resourceBundles)  {
		return build(config, caller, fileName, null, resourceBundles);
	}
	
	/**
	 * Builds assuming the root object has already been instantiated
	 * (e.g. for loading from within the constructor of an object and creating
	 * it dynamically at that time)
	 * @param caller The caller
	 * @return Build result
	 * @throws IOException 
	 * @throws BuildException 
	 */
	public static BuildResult build(BuilderConfig config,Object caller, String fileName, 
			Map<String,?> customProperties, ResourceBundle...resourceBundles)  {
		
		if (caller == null) {
			throw new NullPointerException("Caller cannot be null or empty");
		}
		
		//handle case when during development time the YAML content is loaded from the IDEs /src folder
		//instead of the app's /bin folder (allows hot-deploy of YAML without restarting the whole app)
		//needs to be ignored for the builders' built-in dialogs (e.g. BackgroundDialog)
		InputStream input = null;
		if (BuilderConfig.getDevSourceFolder() == null ||
				caller.getClass().getPackage().getName().startsWith(Builder.class.getPackage().getName())) {
			input = caller.getClass().getResourceAsStream(fileName);
		} else {
			//during dev time overriden source of YAML files
			String yamlFileName = fileName;
			try {
				
				URI bin = caller.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
				URI src = bin.resolve(new URI(BuilderConfig.getDevSourceFolder() +
						"/" +
						caller.getClass().getPackage().getName().replace(".", "/") +
						"/" + fileName));
				
				yamlFileName = src.toString();
				input = new FileInputStream(new File(src));
			} catch (Exception e) {
				throw new BuildException(e,"Unable to process file {0}.\n{1}",yamlFileName, e);
			}
		}
		
		//check for missing file (Issue #20)
		if (input == null) {
			throw new BuildException("No YAML file found: {0}", fileName);
		}
		
		//read in string manually so that we can pre-validate it for invalid characters
		StringBuilder bld = new StringBuilder();
		try {
			BufferedReader rdr = new BufferedReader(new InputStreamReader(input));
			String line = rdr.readLine();
			while( line != null) {
				bld.append(line).append("\n");
				line = rdr.readLine();
			}
			rdr.close();
		} catch (IOException ex) {
			throw new BuildException(ex);
		}
		
		return buildFromString(config, caller, bld.toString(), customProperties, resourceBundles);
	}

	/**
	 * Builds assuming the root object has already been instantiated
	 * (e.g. for loading from within the constructor of an object and creating
	 * it dynamically at that time)
	 * @param caller The caller
	 * @return Build result
	 * @throws IOException 
	 * @throws BuildException 
	 */
	public static BuildResult buildFromString(BuilderConfig config,Object caller, String yamlContent, ResourceBundle...resourceBundles)  {
		return buildFromString(config,caller, yamlContent,null,resourceBundles);
	}
	/**
	 * Builds assuming the root object has already been instantiated
	 * (e.g. for loading from within the constructor of an object and creating
	 * it dynamically at that time)
	 * @param caller The caller
	 * @return Build result
	 * @throws IOException 
	 * @throws BuildException 
	 */
	@SuppressWarnings("unchecked")
	public static BuildResult buildFromString(BuilderConfig config,Object caller, String yamlContent, Map<String,?> customProperties, 
			ResourceBundle...resourceBundles)  {
		
		if (caller == null) {
			throw new NullPointerException("Caller cannot be null or empty");
		}
		
		BuildProcess process = new BuildProcess(config, caller, resourceBundles);
		//apply custom properties
		if (customProperties != null) {
			for(String key : customProperties.keySet()) {
				process.getBuildResult().getProperties().put(key, customProperties.get(key));
			}
		}
		
		BuilderUtils.validateYamlContent(yamlContent);
		Object document = YAML.load(yamlContent);
		executeBuild(document, config, process);
		return process.getBuildResult();
	}
	
	/**
	 * Actual building logic happens here
	 * @throws BuildException 
	 */
	@SuppressWarnings("unchecked")
	private static void executeBuild(Object document, BuilderConfig config, BuildProcess process) throws BuildException {

		//build started events
		BuildListener[] listeners = config.getBuildListeners();
		BuildEvent buildEvent = new BuildEvent(process.getCaller(),process.getBuildResult());
		if (listeners.length > 0) {
			for (BuildListener listener : listeners) {
				listener.buildStarted(buildEvent);
			}
		}
		
		//pre-processing
		document = BuilderPreProcessor.preprocess(config, process, document, null);
		process.setDocument(document);
		
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "Building from YAML document:\n%s",document);
		}
		
		if (document instanceof Map) {
			Map<String,Object> map = (Map<String,Object>)document;

			//system nodes need to be processed after the regular builder nodes
			Set<String> systemNodes = new LinkedHashSet<String>();
			systemNodes.add(BIND);
			systemNodes.add(VALIDATE);
			
			//control the order in which the keys get executed
			List<String> priorities = new LinkedList<String>();
			for(String key : map.keySet()) {
				//make sure the 'system' nodes get executed last
				if (!systemNodes.contains(key)) {
					priorities.add(key);
				}
			}
			for (String systemNode : systemNodes) {
				if (map.containsKey(systemNode)) {
					priorities.add(systemNode);
				}
			}
			
			for(String key : priorities) {
				
				Object docNode = map.get(key);
				if (logger.isLoggable(Level.FINE)) {
					logger.log(Level.FINE,"Processing root node: %s",key);
				}
				
				//handle the different potential formats of the root nodes
				if (docNode instanceof Map) {
					processDocumentNode(config,process, null, key,docNode);
				} else if (docNode instanceof List) {
					//root nodes that come in as collection automatically get moved to "content"
					Map<String,Object> content = new HashMap<String,Object>();
					content.put(Builder.CONTENT, docNode);
					docNode = content;
					processDocumentNode(config,process, null, key,docNode);
				} else if (docNode instanceof String) {
					//root nodes that come in as string automatically get moved to "value"
					Map<String,Object> value = new HashMap<String,Object>();
					value.put(Builder.VALUE, docNode);
					docNode = value;
					processDocumentNode(config,process, null, key,docNode);
				} else {
					throw new BuildException("Unable to handle the root node :" + key);
				}
				
				//handle the properties as named objects, once the regular builder node has run
				if (!systemNodes.contains(key)) {
					for(NamedObjectPropertyValue request : process.getPropertiesAsNamedObjects()) {
						request.setReference(process);
					}
					process.getPropertiesAsNamedObjects().clear(); //run it only once
				}
			}
			
		} else {
			processDocumentNode(config, process, null, null, document);
		}
		
		//finish up the build
		BuilderUtils.updateNamedObjectReferencesInCaller(process);
		
		//fire build ended events
		listeners = config.getBuildListeners();
		for(BuildListener listener: listeners) {
			listener.buildEnded(buildEvent);
		}
	}
	
	/**
	 * Processes a particular document node
	 * @param rawDocumentNode
	 * @throws BuildException 
	 */
	@SuppressWarnings("unchecked")
	private static void processDocumentNode(BuilderConfig config, BuildProcess process, Node parent, 
			String currentKey, Object rawDocumentNode) throws BuildException {
		if (rawDocumentNode instanceof Map) {

			if (logger.isLoggable(Level.FINE)) {
				logger.fine("Started creating object defined by alias: " + currentKey);
			}
			
			Map<String,Object> data = (Map<String,Object>)rawDocumentNode;
			Class<?> currentType = BuilderUtils.getClassFromAlias(process, currentKey, null);
			if (currentType == null) {
				throw new InvalidTypeException(currentKey);
			}
			
			handleType(config, process, parent, currentKey, data, currentType);
			
		} else if (rawDocumentNode instanceof List){
			//collection of objects or values
			List items = (List)rawDocumentNode;
			
			Class<?> type = BuilderUtils.getClassFromAlias(process, currentKey, null);
			//create child nodes only for types, not for properties that are lists (e.g. event listeners with multiple events)
			if (Builder.CONTENT.equals(currentKey) || type != null) {
				Node itemsNode = new Node(parent,currentKey);
				
				if (parent != null) {
					parent.addChildNode(itemsNode);
					//propagate parent properties to collection node - makes it faster for child nodes to get to parent's
					//most important attributes
					itemsNode.setMainObject(parent.getMainObject());
					itemsNode.setConsumedKeys(parent.getConsumedKeys());
				}
				
				boolean treatListAsPropertyValue = true;
				for(Object item : items) {
					if (item instanceof Map) {
						Map<String,Object> itemMap = (Map<String,Object>)item;
						for(String itemKey : itemMap.keySet()) {
							Object itemValue = itemMap.get(itemKey);
							processDocumentNode(config, process, itemsNode, itemKey, itemValue);
							treatListAsPropertyValue = false;
						}
					} 
				}
				if (treatListAsPropertyValue) {
					handleProperty(config, process, parent, currentKey);
				}
			}
			
		} else  {
			if (parent != null) {
				//PROPERTY VALUE
				handleProperty(config, process, parent, currentKey);
			} else {
				throw new InvalidFormatException("Unable to process document node : {0}", rawDocumentNode);
			}
		}
	}
	
	//handles creating types
	private static void handleType(BuilderConfig config, BuildProcess process, Node parent, String currentKey, Map<String,Object> data, 
			Class<?> classType) throws BuildException {
		
		Class<?> currentType = BuilderUtils.getClassFromAlias(process, currentKey, null);
		if (currentType == null) {
			throw new InvalidTypeException(currentKey);
		}

		//make sure any missing defaults are there
		handleDefaults(config, process, parent, currentKey, data, currentType);
		
		//validate the current entry - can throw exceptions in there...
		validate(config, process, parent, currentKey, data, currentType);
		
		ITypeHandler typeHandler = TypeDefinition.getTypeHandler(config, currentType);
		
		Node current = null;
		
		//handle passing a pre-instantiated object
		Object existingInstance = BuilderUtils.getExistingInstanceIfAvailable(process.getCaller(), currentType, config, data);
		if (existingInstance != null) {
			//existing instance handle
			current = typeHandler.useExistingInstance(config, process, parent, currentKey, data, existingInstance);
			
		} else if (parent == null && process.getCaller() != null && 
				currentType.isAssignableFrom(process.getCaller().getClass())) {
			//root entry in the file
			current = typeHandler.useExistingInstance(config, process, parent, currentKey, data, process.getCaller());
			
		} else {
			//new
			current = typeHandler.createNewInstance(config, process, parent, currentKey, data);
		}

		//handle result of what the type handler returned
		if (current == null) {
			//handler run...but has nothing to, we can abort any further processing for this node
			return;
		} else if (current.getMainObject() == null) {
			throw new BuildException("ITypeHandler for alias " + currentKey + " did not set Node.mainObject to a value");
		}
		
		//add all the keys that the type handler consumed
		for(String typeHandlerKey : typeHandler.getConsumedKeys()) {
			current.getConsumedKeys().add(typeHandlerKey);
		}
		//same thing for finish processor
		TypeDefinition def = config.getTypeDefinition(currentType);
		if (def != null) {
			ITypeHandlerFinishProcessor finishProcessor = config.getTypeDefinition(currentType).getFinishProcessor();
			if (finishProcessor != null && finishProcessor instanceof IKeyValueConsumer) {
				IKeyValueConsumer consumer = (IKeyValueConsumer) finishProcessor;
				for(String key : consumer.getConsumedKeys()) {
					current.getConsumedKeys().add(key);
				}
			}
		}
			
		
		
		Class<?> createdClassType = current.getMainObject().getClass();
		Set<TypeDefinition> typeDefinitions = config.getTypeDefinitions(createdClassType);
		Set<String> ignored = TypeDefinition.getIgnored(config, current.getMainObject().getClass());
		
		//after creation processors
		List<ITypeHandlerAfterCreationProcessor> afterCreationProcessors = TypeDefinition.getAfterCreationProcessors(config, createdClassType);
		for(ITypeHandlerAfterCreationProcessor processor : afterCreationProcessors) {
			processor.afterCreation(config, process, current, currentKey, data);
		}
		
		//is the type mapped to a method on the parent?
		if (parent != null) {
			Class<?> parentClass = parent.getMainObject().getClass();
			Method method = TypeDefinition.getTypeAsMethod(config, parentClass, createdClassType);
			if (method != null) {
				try {
					method.invoke(parent.getMainObject(), current.getMainObject());
				} catch (Exception e) {
					throw new BuildException("Unable to call {0}.{1} with type {2}",
							parentClass.getSimpleName(), method.getName(), createdClassType.getSimpleName(),e);
				}
			}
		}
		
		if (!(typeHandler instanceof ITypeChildrenHandler)) {
			Map<Integer,List<String>> delayedKeysByWeight = new TreeMap<Integer,List<String>>();

			//recursively process all the child nodes
			for(String childKey : data.keySet()) {
				Object childValue = data.get(childKey);

				//skip keys that have already been processed
				if (current.getConsumedKeys().contains(childKey)) {
					continue;
				}
				
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("Processing child key: " + childKey);
				}
				
				//handle internationalization if any resource bundles have been passed
				if ((config.getResourceBundles().size() > 0 || process.getResourceBundles().size() > 0) && childValue instanceof String) {
					if (TypeDefinition.isLocalizableProperty(childKey, typeDefinitions)) {
						data.put(childKey,process.getBuildResult().getResource(String.valueOf(childValue)));
					}
				}
				
				Class<?> childClass = BuilderUtils.getClassFromAlias(process, childKey, null);
				if (childClass != null) {
					//is a type, but may need to be delayed till the end
					ITypeHandler childTypeHandler = TypeDefinition.getTypeHandler(config, childClass);
					
					Integer delayedWeight = TypeDefinition.getDelayedWeight(childTypeHandler, config.getTypeDefinitions(createdClassType));
					if (childTypeHandler != null && delayedWeight > 0) {
						//this key needs to be processed after all the other children
						addToDelayedKeys(delayedKeysByWeight, delayedWeight, childKey);
					} else {
						processDocumentNode(config,process,current,childKey, childValue);
					}
					
				} else {
					//a property
					if (!ignored.contains(childKey)) {
					
						Integer delayedWeight = TypeDefinition.getDelayedWeight(typeHandler, childKey,
								config.getTypeDefinitions(createdClassType));
						if (delayedWeight > 0) {
							//this key needs to be processed after all the other children
							addToDelayedKeys(delayedKeysByWeight, delayedWeight, childKey);
						} else {
							processDocumentNode(config,process,current,childKey, childValue);
						}
					}
				}
			}
			
			//process the delayed children
			for(Integer delayWeight : delayedKeysByWeight.keySet()) { 
				List<String> keys = delayedKeysByWeight.get(delayWeight);
				for(String delayedKey : keys) {
					Object delayedValue = data.get(delayedKey);
					if (logger.isLoggable(Level.FINE)) {
						logger.fine("Processing delayed weight: " + delayWeight + " / child key: " + delayedKey);
					}
					processDocumentNode(config,process,current,delayedKey, delayedValue);
				}
			}
			
			//check cardinality
			ChildrenCardinalityUtils.checkChildrenCardinality(config,current);
		}
		
		//register main object as root if we are running this at the root level
		if (parent == null && current.getMainObject() != null) {
			process.getBuildResult().getRoots().add(current.getMainObject());
		}
		
		//we're done handling this type - do all the post processing stuff
		if (typeHandler instanceof ITypeHandlerFinishProcessor) {
			ITypeHandlerFinishProcessor postHandler = (ITypeHandlerFinishProcessor)typeHandler;
			postHandler.finish(config, process, current, currentKey, data);
		}
		
		List<ITypeHandlerFinishProcessor> postProcessors = TypeDefinition.getFinishProcessors(config, createdClassType);
		for(ITypeHandlerFinishProcessor postProcessor : postProcessors) {
			postProcessor.finish(config, process, current, currentKey, data);
		}
		
		//should it be added to the list of named objects?
		if (config.isNamedObject(current.getMainObject())) {
			String name = config.getObjectName(current.getMainObject());
			process.addNamedObject(name, current.getMainObject());
		}

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("Finished creating object defined by alias: " + currentKey);
		}

	}
	
	//common logic to populate the list of delayed keys
	private static void addToDelayedKeys(Map<Integer,List<String>> delayedKeysByWeight,
			Integer delayWeight, String key) {
		
		List<String> delayedKeys = delayedKeysByWeight.get(delayWeight);
		if (delayedKeys == null) {
			delayedKeys = new LinkedList<String>();
			delayedKeysByWeight.put(delayWeight, delayedKeys);
		}
		delayedKeys.add(key);
	}
	
	private static void handleProperty(BuilderConfig config, BuildProcess process, Node parent, String currentKey) throws BuildException {
		//PROPERTY VALUE
		if (!parent.getConsumedKeys().contains(currentKey)) { //each property should be processed only once by any handler
			
			//does this property point to a named object?
			if (parent.getProperty(currentKey) instanceof String) {
				String sValue =parent.getStringProperty(currentKey); 
				if (sValue.matches(Builder.NAMED_OBJECT_REGEX)) {
					NamedObjectPropertyValue nopValue = new NamedObjectPropertyValue(parent.getMainObject(),currentKey,
							sValue.substring(NAMED_OBJECT_PREFIX_LENGTH,sValue.length() - NAMED_OBJECT_SUFFIX_LENGTH));
					process.getPropertiesAsNamedObjects().add(nopValue);
					parent.getConsumedKeys().add(currentKey);
					return;
				}
			}
			
			//does this property point to a global variable?
			if (parent.getProperty(currentKey) instanceof String) {
				String sValue =parent.getStringProperty(currentKey); 
				if (sValue.matches(BuilderConfig.GLOBAL_VARIABLE_REGEX)) {
					GlobalVariablePropertyHandler.getInstance().handle(config, process, parent, currentKey);
					return;
				}
			}
			
			//regular property handling starts here...
			IPropertyHandler handler = TypeDefinition.getPropertyHandler(config, parent.getMainObject().getClass(),currentKey);

			//debug info
			if (logger.isLoggable(Level.FINE)) {
				if (handler.getConsumedKeys().size() == 0) {
					logger.fine(String.format("Handling property '%s' of value '%s' for type alias '%s'", currentKey, 
							parent.getProperties().get(currentKey), 
							parent.getKey()));
				} else {
					for(String consumedKey : handler.getConsumedKeys()) {
						if (parent.getProperties().containsKey(consumedKey)) {
							logger.fine(String.format("Handling property '%s' of value '%s' for type alias '%s'", 
									consumedKey, 
									parent.getProperties().get(consumedKey), 
									parent.getKey()));
						}
					}
				}
			}
			
			//handle properties flagged as lists
			if (handler instanceof IPropertyList) {
				IPropertyList propertyList = (IPropertyList)handler;
				for(String consumedKey : handler.getConsumedKeys()) {
					if (propertyList.isList(consumedKey)) {
						//once a property has been flagged as a list, handle to make sure the value gets converted into one
						Object value = parent.getProperties().get(consumedKey);
						if (value != null && !(value instanceof List)) {
							List<Object> valueList = new ArrayList<Object>();
							valueList.add(value);
							parent.getProperties().put(consumedKey, valueList);
							
							if (logger.isLoggable(Level.FINE)) {
								logger.fine("Converted single value " + value + " to a single item list for property " + consumedKey);
							}
						}
					}
				}
			}
			
			//handling regular properties that are types and have specialized "type as value" handlers
			try {
				if (handler instanceof ITypeAsValueSupport && PropertyUtils.getPropertyDescriptor(parent.getMainObject(),currentKey) != null) {
					
					Class<?> propertyType = PropertyUtils.getPropertyType(parent.getMainObject(),currentKey);
					
					ITypeAsValueHandler<? extends Object> asValueHandler = TypeDefinition.getTypeAsValueHandler(config, propertyType);
					if (asValueHandler != null) {
						
						String sValue = parent.getStringProperty(currentKey);
						if (sValue.matches(asValueHandler.getRegex())) {
							//replace the value from the file with the corresponding object instance
							parent.getProperties().put(currentKey, asValueHandler.getValue(process, parent, currentKey, 
									parent.getProperty(currentKey)));
						} else {
							throw new BuildException("Invalid {0} value \"{1}\" for {2}.{3}. Must in be in \"{4}\" format, e.g. \"{5}\"",
									propertyType.getSimpleName(), sValue, parent.getMainObject().getClass().getSimpleName(),
									currentKey, asValueHandler.getRegex(), asValueHandler.getInputValueSample());
						}
					}
				}
			} catch (Exception e) {
				throw new BuildException(e,"Unable to process property {0}.{1} : {2}",
						parent.getMainObject().getClass().getSimpleName(),currentKey, e.getMessage());
			} 
			
			validateProperty(handler,config, process, parent, currentKey);
			handler.handle(config, process, parent, currentKey);
			
			//tell the parent what keys have been handled
			parent.getConsumedKeys().add(currentKey);   //required for the default type handler to work, since it handles all undefined properties
			for(String key : handler.getConsumedKeys()) {
				parent.getConsumedKeys().add(key);
			}
		} 
	}
	
	//performs property value validation
	@SuppressWarnings("unchecked")
	private static void validateProperty(IPropertyHandler handler, BuilderConfig config, BuildProcess result, 
			Node parent, String currentKey) throws BuildException {
		
		//allowed values
		if (handler instanceof IAllowedValues) {
			String value = parent.getStringProperty(currentKey);
			IAllowedValues list = (IAllowedValues)handler;
			
			if (!list.getAllowedValues().contains(value)) {
				//display detailed error message
				throw new InvalidPropertyValueException(parent.getKey(),currentKey, value, list.getAllowedValues());
			}
		}
		
		//valid formats
		if (handler instanceof IAllowedPropertyFormat) {
			IAllowedPropertyFormat format = (IAllowedPropertyFormat)handler;
			
			//check all properties that the handler consumes
			for(String consumedKey : handler.getConsumedKeys()) {
				if (parent.containsProperty(consumedKey)) {
					String value = parent.getStringProperty(consumedKey);
					if (!value.matches(format.getRegexPattern(currentKey))) {
						throw new InvalidPropertyValueException(parent.getKey(),currentKey, value, 
								format.getRegexPattern(currentKey), format.getValidSample(currentKey));
					}
				}
			}
		}
		
		//valid combinations
		if (handler instanceof IAllowedPropertyCombinations) {
			IAllowedPropertyCombinations combination = (IAllowedPropertyCombinations)handler;
			
			if (!combination.getAllowedCombinations().isValid(parent.getProperties().keySet())) {
				throw new BuildException("Invalid combination of properties. Valid are: " + 
						combination.getAllowedCombinations());
			}
		}
		
		//a list?
		if (handler instanceof IPropertyList) {
			IPropertyList listHandler = (IPropertyList)handler;
			
			//we must match the list of values to the value list definitions
			//check all properties that the handler consumes
			for(String consumedKey : handler.getConsumedKeys()) {
				if (parent.containsProperty(consumedKey) && listHandler.isList(consumedKey)) {
					List<Object> values = (List<Object>)parent.getProperties().get(consumedKey);
					
					//match to a value list definition
					Values valueList = null;
					for (ValueListDefinition vlDef : listHandler.getValueListDefinitions(consumedKey)) {
						if (vlDef.isExactMatch(values)) {
							valueList = new Values(vlDef);
							vlDef.validateValues(values, result, parent,valueList);
							if (!valueList.isValid()) {
								throw new BuildException(valueList.getErrors());
							}
							break;
						}
					}
					
					if (valueList == null) {
						throw new BuildException(String.format("Values '%s' did not match to any defined value list definition for property '%s'",
								values, consumedKey));
					} else {
						//all good - replace the original value with the full
						//ValueList
						parent.getProperties().put(consumedKey, valueList);
					}
				}
				
			}
		}
	}
	
	/**
	 * Validates if the current data is correct. 
	 * @return Error message (empty means no errors, all good)
	 * @throws InvalidParentTypeException 
	 * @throws MissingRequiredPropertyException 
	 * @throws MissingRequiredTypeException 
	 */
	private static void validate(BuilderConfig config, BuildProcess process, Node parent, String currentKey, Map<String,Object> currentProperties, Class<?> classType) throws InvalidParentTypeException, MissingRequiredPropertyException, MissingRequiredTypeException {
		
		//check for allowed parent
		if (parent != null && !TypeDefinition.isParentAllowed(parent, config.getTypeDefinitions(classType))) {
			throw new InvalidParentTypeException(classType,parent.getMainObject().getClass(),
					TypeDefinition.getAllowedParents(config, classType));
		}
		
		//check for required properties
		for(String requiredKey : TypeDefinition.getRequiredKeys(config, classType)) {
			if (!currentProperties.containsKey(requiredKey)) {
				throw new MissingRequiredPropertyException(currentKey,
						requiredKey, currentProperties);
			}
		}
		
		//check for required types
		for(Class<?> requiredType : TypeDefinition.getRequiredTypes(config, classType)) {
			boolean found = false;
			keySearch:
			for(String key : currentProperties.keySet()) {
				if (currentProperties.get(key) instanceof Map) {
					//dealing with a type
					Class<?> type = BuilderUtils.getClassFromAlias(process, key, null);
					if (type != null && requiredType.isAssignableFrom(type)) {
						found = true;
						break keySearch;
					}
				}
			}
			if (!found) {
				throw new MissingRequiredTypeException(currentKey,requiredType,currentProperties);
			}
		}
	}
	
	/**
	 * Subroutine to handle adding of default values, in case they are missing
	 */
	private static void handleDefaults(BuilderConfig config, BuildProcess result, Node parent, String currentKey, Map<String,Object> currentProperties, Class<?> classType) {

		Map<String,Object> defaults = TypeDefinition.getDefaults(config, classType);
		for(String key : defaults.keySet()) {
			if (!currentProperties.containsKey(key)) {
				currentProperties.put(key, defaults.get(key));
			}
		}
	}
	
	
}
