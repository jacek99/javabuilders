/**
 * 
 */
package org.javabuilders;

import static org.javabuilders.util.BuilderUtils.getRealKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.javabuilders.exception.UnrecognizedAliasException;
import org.javabuilders.handler.ITypeChildrenHandler;
import org.javabuilders.handler.ITypeHandler;
import org.javabuilders.util.BuilderUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Pre-processor for YAML file to handle advanced functionality specific to Java Builders.
 * Namely, it handles virtual constructors embedded in the types, as well as exploding shortcut types (i.e. those entered as a single value or a list) into a proper map
 * @author Jacek Furmankiewicz
 */
public class BuilderPreProcessor {
	
	public static final Logger logger = LoggerFactory.getLogger(BuilderPreProcessor.class);
	public static final Map<Character,Character> listIndicators = new HashMap<Character, Character>();
	
	static {
		listIndicators.put('(',')'); //obsolete-for backwards compatibility
		listIndicators.put('[',']');
		listIndicators.put(null,null);
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
			logger.error(be.getMessage(),be);
			throw be;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
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
				ITypeHandler handler = TypeDefinition.getTypeHandler(config, typeClass);
				
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
					BuilderUtils.uncompressYaml(key, typeMap);
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
					
						//dealing with a virtual constructor in a list
						Map<String,Object> rootMap = new HashMap<String, Object>();
						Map<String,Object> typeMap = new HashMap<String, Object>();
						rootMap.put(realKey, typeMap);
						
						BuilderUtils.uncompressYaml(stringItem, typeMap);
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
				//dealing with a virtual constructor in a list
				Map<String,Object> rootMap = new HashMap<String, Object>();
				Map<String,Object> typeMap = new HashMap<String, Object>();
				rootMap.put(realKey, typeMap);
				
				BuilderUtils.uncompressYaml(value, typeMap);
				current = rootMap;
				preprocess(config, process, rootMap, parent);
				
				//handle aliased properties
				handlePropertyAlias(config,typeClass,typeMap);
				handleMappedProperties(config, typeClass, typeMap);
				
				
			} else {
				throw new UnrecognizedAliasException("\"{0}\" is not a recognized alias", realKey);
			}
		
		} else {
			//regular string property ...nothing to do for now
		}
		
		return current;

	}

	//explodes a type entered as a list or single value into a proper map
	public static void explodeShortcutTypeToMap(BuilderConfig config, BuildProcess process, ITypeHandler handler, String key, Map<String,Object> current) throws BuildException {
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
					Map<String,Object> rootMap = new HashMap<String,Object>();
					Map<String,Object> typeMap = new HashMap<String,Object>();
					current.put(key, rootMap);
					rootMap.put(realTypeKey, typeMap);
					BuilderUtils.uncompressYaml(sValue, typeMap);
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
