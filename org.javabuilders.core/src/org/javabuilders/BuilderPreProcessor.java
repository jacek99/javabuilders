/**
 * 
 */
package org.javabuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.handler.ITypeHandler;
import org.jvyaml.YAML;

/**
 * Pre-processor for YAML file to handle advanced functionality specific to Java Builders.
 * Namely, it handles virtual constructors embedded in the types, as well as exploding shortcut types (i.e. those entered as a single value or a list) into a proper map
 * @author Jacek Furmankiewicz
 */
public class BuilderPreProcessor {
	
	private static final Logger logger = Logger.getLogger(BuilderPreProcessor.class.getSimpleName());
	
	static {
		logger.setLevel(Level.ALL);
	}

	/**
	 * Pre-processes the YAML object to implement advanced/enhanced JB-YAML features
	 * @param config Config
	 * @param current Current
	 * @throws BuildException If anything goes wrong...
	 */
	public static Object preprocess(BuilderConfig config, BuildProcess process,  Object current, Object parent) throws BuildException {
		try {
			if (current instanceof Map) {
				current = handleMap(config, process, current, parent);
			} else if (current instanceof List) {
				current = handleList(config, process, current, parent);
			} else if (current instanceof String && parent == null) {
				//only handle String if it is the root in a file
				current = handleString(config, process, current, parent);
			}
			
			return current;
			
		} catch (BuildException be) {
			logger.severe(be.getMessage());
			throw be;
		} catch (Exception e) {
			logger.severe(e.getMessage());
			throw new BuildException(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private static Object handleMap(BuilderConfig config, BuildProcess process,  Object current, Object parent) throws BuildException {
		Map<String,Object> map = (Map<String,Object>)current;
		List<String> keysToRemove = new ArrayList<String>();
		Map<String,Object> propertiesToAdd = new HashMap<String, Object>();
		
		for(String key : map.keySet()) {
			Object value = map.get(key);
			String realKey = getRealKey(key);
			
			Class<?> typeClass = BuilderUtils.getClassFromAlias(process, realKey, null);
			if (typeClass != null) {
				//we're in business...dealing with a type here...
				ITypeHandler handler = config.getTypeHandler(typeClass);
				
				//handle types that are expressed as a list or single value
				explodeShortcutTypeToMap(config, process,  handler, key, map);
				//if exploded, we are now sure it is a proper type Map
				Map<String,Object> typeMap = (Map<String, Object>) map.get(key);
				if (typeMap == null) {
					//handle case where is root line in compact syntax with no children underneath
					typeMap = new HashMap<String, Object>();
				}
				
				if (!key.equals(realKey)) {
					//enhanced JB-YAML : embedded virtual constructor in the type 
					//explode the embedded property values into stand-alone entries in the document
					explodeVirtualConstructorProperties(handler, key, realKey, typeMap);
					propertiesToAdd.put(realKey, typeMap);
					keysToRemove.add(key);
				}
				
				//handle aliased properties
				handlePropertyAlias(config,typeClass,typeMap);
				
				handleMappedProperties(config, typeClass, typeMap);
				
				//handle mapped properties
				/*
				for(String typeMapKey : typeMap.keySet()) {
					Object typeMapValue = typeMap.get(typeMapKey);

					//mapped value?
					Object mappedValue  = TypeDefinition.getPropertyValue(config, typeClass, typeMapKey, typeMapValue);
					if (!mappedValue.equals(typeMapValue)) {
						typeMap.put(typeMapKey, mappedValue);
					}
				}
				*/
				
				//keep goin' down...
				if(!(handler instanceof ITypeChildrenHandler)) {
					preprocess(config, process, typeMap, current);
				}
			} else {
				//keep looking further down the rabbit hole...
				preprocess(config, process, value, current);
			}
		}
		
		//remove all the keys with virtual constructors
		for(String key : keysToRemove) {
			map.remove(key);
		}
		//add their properly exploded versions
		for(String key : propertiesToAdd.keySet()) {
			map.put(key, propertiesToAdd.get(key));
		}
		
		return current;

	}
	
	@SuppressWarnings("unchecked")
	private static Object handleList(BuilderConfig config, BuildProcess process,  Object current, Object parent) throws BuildException {
		List<Object> list = (List<Object>) current;
		for(int i = 0; i < list.size(); i++) {
			
			Object item = list.get(i);
			
			if (item instanceof String) {
				String stringItem = (String) item;
				String realKey = getRealKey(stringItem);
				if (!stringItem.equals(realKey)) {
					
					Class<?> typeClass = BuilderUtils.getClassFromAlias(process, realKey, null);
					if (typeClass != null) {
						//we're in business...dealing with a type here...
						ITypeHandler handler = config.getTypeHandler(typeClass);
					
						//dealing with a virtual constructor in a list
						Map<String,Object> rootMap = new HashMap<String, Object>();
						Map<String,Object> typeMap = new HashMap<String, Object>();
						rootMap.put(realKey, typeMap);
						
						explodeVirtualConstructorProperties(handler, stringItem, realKey, typeMap);
						list.remove(i);
						list.add(i, rootMap);
						
						//handle aliased properties
						handlePropertyAlias(config,typeClass,typeMap);
						handleMappedProperties(config, typeClass, typeMap);
						
					} else {
						throw new BuildException("{0} is not a recognized alias", realKey);
						//preprocess(config, item, current);
					}
					
				} else {
					preprocess(config, process, item, current);
				}
			} else {
				preprocess(config, process, item, current);
			}
		}
		
		return current;

	}
	
	private static Object handleString(BuilderConfig config, BuildProcess process,  Object current, Object parent) throws BuildException {
		//dealing with a single value - could be a virtual constructor though
		String value = (String)current;
		String realKey = getRealKey(value);
		if (!value.equals(realKey)) {
			
			Class<?> typeClass = BuilderUtils.getClassFromAlias(process, realKey, null);
			if (typeClass != null) {
				//we're in business...dealing with a type here...
				ITypeHandler handler = config.getTypeHandler(typeClass);
			
				//dealing with a virtual constructor in a list
				Map<String,Object> rootMap = new HashMap<String, Object>();
				Map<String,Object> typeMap = new HashMap<String, Object>();
				rootMap.put(realKey, typeMap);
				
				explodeVirtualConstructorProperties(handler, value, realKey, typeMap);
				current = rootMap;
				preprocess(config, process, rootMap, parent);
				
				//handle aliased properties
				handlePropertyAlias(config,typeClass,typeMap);
				handleMappedProperties(config, typeClass, typeMap);
				
				
			} else {
				throw new BuildException("{0} is not a recognized alias", realKey);
			}
		
		} else {
			//regular string property ...nothing to do for now
		}
		
		return current;

	}
	
	//handles child keys that may have a virtual constructor embedded in them
	private static String getRealKey(String key) {
		if (key.indexOf('(') > 0 && key.endsWith(")"))  {
			key = key.substring(0,key.indexOf('('));
		} 
		return key;
	}
	
	//explodes a type entered as a list or single value into a proper map
	private static void explodeShortcutTypeToMap(BuilderConfig config, BuildProcess process,  ITypeHandler handler, String key, Map<String,Object> current) throws BuildException {
		Object value = current.get(key);
		//handle special short-hand cases where it may be shown as a list
		//need to "move" the list to a pre-defined property name...used for short-hand formats of type entry
		if (value instanceof List) {
			Map<String,Object> childMap = new HashMap<String,Object>();
			childMap.put(handler.getCollectionPropertyName(), value);
			value = childMap;
			current.put(key, childMap);
		}
			
		//handle special short-hand case where it is just a String
		//need to move the String value to a pre-defined property name
		if (value instanceof String) {
			
			String sValue = (String)value;

			//handle the case where the value may be an embedded object using virtual constructor flow
			boolean isType = false;
			String realTypeKey = getRealKey(sValue);
			if (!realTypeKey.equals(value)) {
				Class<?> valueClass = BuilderUtils.getClassFromAlias(process, realTypeKey, null);
				if (valueClass != null) {
					ITypeHandler valueHandler = config.getTypeHandler(valueClass);
					Map<String,Object> rootMap = new HashMap<String,Object>();
					Map<String,Object> typeMap = new HashMap<String,Object>();
					current.put(key, rootMap);
					rootMap.put(realTypeKey, typeMap);
					explodeVirtualConstructorProperties(valueHandler, sValue, realTypeKey, typeMap);
					isType = true;
				}
			}
			
			if (!isType) {
				Map<String,Object> childMap = new HashMap<String,Object>();
				childMap.put(handler.getSimpleValuePropertyName(), value);
				value = childMap;
				current.put(key, childMap);
			}
		}			
	}
	
	//explodes the properties entered into a virtual constructor into separate child properties
	private static void explodeVirtualConstructorProperties(ITypeHandler handler, String key, String realKey, Map<String,Object> current) throws BuildException {
		
		StringBuilder nameValuePair = new StringBuilder(key.length());
		
		 //parse the virtual constructor values
		int start = key.indexOf("(");
		if (start > 0 && key.endsWith(")")) {
		
			String constructorText = key.substring(start + 1, key.length() - 1);
			List<String> keyValuePairs = new LinkedList<String>();

			int nestedParentheses = 0;
			boolean isEmbeddedInString = false;
			
			for(int i = 0; i < constructorText.length(); i++) {
				char currentChar = constructorText.charAt(i);
			
				if (currentChar == '"') {
					isEmbeddedInString = !isEmbeddedInString;
				}
				
				if (!isEmbeddedInString) {
					
					if (currentChar == '(') {
						nestedParentheses++;
					} else if (currentChar == ')') {
						nestedParentheses--;
					} else if (currentChar == ',' && nestedParentheses == 0) {
						//we have a real separator
						keyValuePairs.add(nameValuePair.toString());
						nameValuePair.setLength(0);
						continue;
					}
				}
				
				nameValuePair.append(currentChar);
			}
			//process last one
			keyValuePairs.add(nameValuePair.toString());
			
			StringBuilder temp = new StringBuilder();
			for(String keyValuePair : keyValuePairs) {
				 if (keyValuePair.length() > 0) {
					 String[] pair = keyValuePair.split("=",2);
					 if (pair.length == 2) {
						 pair[0] = pair[0].trim();
						 
						 temp.setLength(0);
						 temp.append(pair[1].trim());
						 
						 //if the argument is a Java-style collection () replace it wit the equivalent YAML []
						 //handle embedded values in a String (Issue #14)
						 boolean isEmbedded = false;
						 for (int i = 0; i < temp.length();i++) {
							 char c = temp.charAt(i);
							 if (c == '"') {
								 isEmbedded = !isEmbedded;
							 }
							 if (!isEmbedded) {
								 if (c == '(') {
									 temp.setCharAt(i, '[');
								 } else if (c == ')') {
									 temp.setCharAt(i, ']');
								 }
								 
							 }
						 }
						 
						 //put it into the map, but only if it does not exist there already
						 if (!current.containsKey(pair[0])) {
							 //parse the value with YAML to make sure it makes it into the correct default type
							 Object value = YAML.load(temp.toString());
							 current.put(pair[0],value);
						 }
					 } else  {
						 //could be an empty constructor, but if not...it's a format error
						 throw new BuildException("Key/value {0} from virtual constructor {1} not in valid format",keyValuePair,constructorText);
					 }
				 }
			 }
		 } else {
			 throw new BuildException("Unable to parse virtual constructor: {0}", key);
		 }
	}
	
	//handles property aliases
	private static void handlePropertyAlias(BuilderConfig config, Class<?> typeClass, Map<String,Object> typeMap) throws BuildException {
		Iterator<String> it = typeMap.keySet().iterator();
		Map<String,Object> aliasedPropertiesToAdd = new HashMap<String, Object>();
		while (it.hasNext()) {
			String typeMapKey = it.next();
			
			//aliased?
			String actual = TypeDefinition.getPropertyForAlias(config, typeClass, typeMapKey);
			if (actual != null) {
				//the current key is an alias for a "real" property and the real property is not defined (otherwise it overrides all the aliases)
				if (typeMap.containsKey(actual)) {
					throw new BuildException("Both \"{0}\" alias ({1}) and actual \"{2}\" property ({3}) have been specified. Only can of the two is allowed.",
							typeMapKey, typeMap.get(typeMapKey),actual, typeMap.get(actual));
				} else {
					//move value to actual property
					aliasedPropertiesToAdd.put(actual, typeMap.get(typeMapKey));
					it.remove();
				}
			}
		}
		for(String typeMapKey : aliasedPropertiesToAdd.keySet()) {
			typeMap.put(typeMapKey, aliasedPropertiesToAdd.get(typeMapKey));
		}

	}
	
	private static void handleMappedProperties(BuilderConfig config, Class<?> typeClass, Map<String, Object> typeMap) throws BuildException {
		//handle mapped properties
		for(String typeMapKey : typeMap.keySet()) {
			Object typeMapValue = typeMap.get(typeMapKey);

			//mapped value?
			if (typeMapValue != null) {
				Object mappedValue  = TypeDefinition.getPropertyValue(config, typeClass, typeMapKey, typeMapValue);
				if (!mappedValue.equals(typeMapValue)) {
					typeMap.put(typeMapKey, mappedValue);
				}
			}
		}
	}

	
}
