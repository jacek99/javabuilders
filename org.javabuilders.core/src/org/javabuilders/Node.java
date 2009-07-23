package org.javabuilders;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents a node being currently processed
 * @author Jacek Furmankiewicz
 */
public class Node  {

	private Object mainObject = null;
	private Node parent = null;
	private String key = null;

	private Map<String,List<Object>> childValues = new HashMap<String,List<Object>>();
	private Set<Node> childNodes = new LinkedHashSet<Node>();
	
	private Map<String,Object> properties = new HashMap<String,Object>();
	private Set<String> consumedKeys = new HashSet<String>();
	
	private boolean usePreInstantiatedRoot = false;
	private Map<String,Object> customProperties = new HashMap<String, Object>();

	/**
	 * Constructor (for use by Builder only)
	 * @param mainObject Main object being processed
	 * @param properties The list of properties for this object
	 */
	Node(Node parent, String key) {
		this(parent,key,null);
	}
	
	/**
	 * Constructor
	 * @param mainObject Main object being processed
	 * @param properties The list of properties for this object
	 */
	public Node(String key, Map<String,Object> properties) {
		this(null,key, properties);
	}
	
	/**
	 * Constructor
	 * @param parent	Parent node (null is accepted if root)
	 * @param mainObject Main object being processed
	 */
	public Node(Node parent, String key, Map<String,Object> properties) {
		this(parent,key, properties, null);
	}
	
	/**
	 * Constructor
	 * @param parent	Parent node (null is accepted if root)
	 * @param mainObject Main object being processed
	 */
	public Node(Node parent, String key, Map<String,Object> properties, Object mainObject) {
		if (key == null) {
			throw new NullPointerException("key cannot be null");
		}
		this.key = key;
		
		if (properties != null) {
			this.properties = properties;
		}
		
		//automatically add to parent if there is one
		if (parent != null) {
			parent.addChildNode(this);
			this.parent = parent;
		}
		
		if (mainObject != null) {
			this.setMainObject(mainObject);
		}
	}

	
	/**
	 * Returns the optional main object that was created by this node
	 * @return Main object
	 */
	public Object getMainObject() {
		return mainObject;
	}
	
	/**
	 * Sets the optional main object that was created by this node
	 */
	public void setMainObject(Object object) {
		mainObject = object;
	}
	
	/**
	 * @return The parent node (null if root)
	 */
	public Node getParent() {
		return parent;
	}
	
	/**
	 * Attempts to find the first parent of a particular type
	 * @return The parent node (null if none found)
	 */
	public Object getParentObject(Class<?>...parentTypes) {
		
		Node parentNode = null;
		
		Class<?> type = getMainObject().getClass();
		Node currentNode = this;
		
		root:
		while (type != null) {
			for(Class<?> parentType : parentTypes) {
				if (parentType.isAssignableFrom(type)) {
					parentNode = currentNode;
					break root;
				}
			}
			
			//go up one level
			currentNode = currentNode.getParent();
			if (currentNode != null) {
				type = currentNode.getMainObject().getClass();
			} else {
				type = null;
			}
			
		}
		
		if (parentNode != null) {
			return parentNode.getMainObject();
		} else {
			return null;
		}
		
	}

	
	/**
	 * Returns the list of all the children nodes belonging to this parent
	 * @return
	 */
	public Map<String,List<Object>> getChildValues() {
		return childValues;
	}
	
	/**
	 * Returns the list of all the children for a specific key
	 * @return
	 */
	public List<Object> getChildValues(String key) {
		return childValues.get(key);
	}
	
	/**
	 * Returns the first child value for a specific key
	 * @return
	 */
	public Object getFirstChildValue(String key) {
		return childValues.get(key).get(0);
	}
	
	/**
	 * Registers the child with this parent, grouped under a common key
	 */
	public void addChildValue(String key,Object value) {
		List<Object> list = childValues.get(key);
		if (list == null) {
			list = new ArrayList<Object>();
			childValues.put(key, list);
		}
		list.add(value);
	}
	
	/**
	 * Returns all child nodes
	 * @return All child nodes
	 */
	public Set<Node> getChildNodes() {
		return childNodes;
	}
	
	/**
	 * Returns all child nodes, filtered by class type that was created
	 * @param classFilter Class filter
	 * @return All child nodes
	 */
	public Set<Node> getChildNodes(Class<?>... classFilter) {
		Set<Node> nodes = new LinkedHashSet<Node>();
		
		for(Node child : getChildNodes()) {
			for(Class<?> type : classFilter) {
				if (type.isAssignableFrom(child.getMainObject().getClass())) {
					nodes.add(child);
				}
			}
		}
		
		return nodes;
	}

	
	/**
	 * Helper method to quickly get helper node
	 * @return 
	 */
	public Node getContentNode() {
		return getChildNode(Builder.CONTENT);
	}

	/**
	 * Helper method to quickly get children in the content node
	 * @return List of content nodes (or null if none)
	 */
	public Set<Node> getContentNodes() {
		Node content = getContentNode();
		if (content != null) {
			return content.getChildNodes();
		} else {
			return null;
		}
	}

	
	/**
	 * Helper method to quickly get children in the content node of a particular
	 * type
	 * @param classFilter Class type to filter on
	 * @return List of relevant child nodes
	 */
	public Set<Node> getContentNodes(Class<?>... classFilter) {
		Node content = getContentNode();
		if (content != null) {
			return content.getChildNodes(classFilter);
		} else {
			return new HashSet<Node>();
		}
	}
	
	/**
	 * Helper method to quickly get children in the content node of a particular
	 * type
	 * @param classFilter Class type to filter on
	 * @return List of relevant child objects
	 */
	@SuppressWarnings("unchecked")
	public <C> Set<C> getContentObjects(Class<? extends C> classFilter) {
		Set<Node> contents = getContentNodes(classFilter);
		Set<C> objects = new LinkedHashSet<C>();
		for(Node node : contents) {
			objects.add((C) node.getMainObject());
		}
		return objects;
	}
	
	/**
	 * Helper method to quickly get child objects of a particular type
	 * @param <C>
	 * @param classFilter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <C> Set<C> getChildObjects(Class<? extends C> classFilter) {
		Set<Node> contents = getChildNodes(classFilter);
		Set<C> objects = new LinkedHashSet<C>();
		for(Node node : contents) {
			objects.add((C) node.getMainObject());
		}
		return objects;
	}
	
	/**
	 * Returns sibling objects of a particular type
	 * @param <C>
	 * @param classFilter
	 * @return
	 */
	public <C> Set<C> getSiblingObjects(Class<? extends C> classFilter) {
		return getParent().getChildObjects(classFilter);
	}
	
	/**
	 * Looks at the raw YAML data, regardless of whether it's been turned into a
	 * node yet or not
	 * @param <C> Class
	 * @param classFilter Class filter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <C> List<Map<String,Object>> getContentData(Class<? extends C> classFilter) {
		List<Map<String,Object>> data = new LinkedList<Map<String,Object>>();
		String name = classFilter.getSimpleName();
		
		Object ct = getProperty(Builder.CONTENT);
		if (ct instanceof List) {
			List list = (List) ct;
			for (Object entry : list) {
				if (entry instanceof Map) {
					Map<String,Map<String,Object>> row = (Map<String, Map<String, Object>>) entry;
					for(String key : row.keySet()) {
						if (name.equals(key)) {
							data.add(row.get(key));
						}
					}
				}
			}
		}
		
		return data;
	}
	
	/**
	 * Returns the child node identified by a particular key
	 * @param key Key
	 * @return Child node (or null if none found)
	 */
	public Node getChildNode(String key) {
		for(Node node: childNodes) {
			if (node.getKey().equals(key)) {
				return node;
			}
		}
		return null;
	}
	
	/**
	 * Adds a child node
	 * @param node Node
	 */
	public void addChildNode(Node node) {
		childNodes.add(node);
	}

	/**
	 * @return The properties
	 */
	public Map<String, Object> getProperties() {
		return properties;
	}
	
	/**
	 * Shortcut to getProperties().containsKey() for simpler API
	 * @param key 
	 * @return True if contains property, false if not
	 */
	public boolean containsProperty(String key) {
		return getProperties().containsKey(key);
	}
	
	/**
	 * Shortcut to quickly get a string representation of a property value
	 * @param property A list of property aliases
	 * @throws Thrown if the same aliased property is defined multiple times
	 * @return String property value (null if not found)
	 */
	public String getStringProperty(String...propertyAliases) throws BuildException {
		String value = null;
		for(String alias : propertyAliases) {
			if (getProperties().containsKey(alias)) {
				//if the same property's aliases are defined multiple times, we need to throw exception
				if (value == null) {
					Object temp = getProperties().get(alias);
					value = (temp instanceof String) ? (String)temp : String.valueOf(temp);
				} else {
					throw new BuildException("Found multiple alias values for the same property: " + propertyAliases);
				}
			}
		}
		
		return value;
	}
	
	/**
	 * Shortcut to quickly get a Long representation of a property value
	 * @param property A list of property aliases
	 * @throws Thrown if the same aliased property is defined multiple times
	 * @return String property value (null if not found)
	 */
	public Long getLongProperty(String...propertyAliases) throws BuildException {
		Long value = null;
		for(String alias : propertyAliases) {
			if (getProperties().containsKey(alias)) {
				//if the same property's aliases are defined multiple times, we need to throw exception
				if (value == null) {
					value = (Long)getProperties().get(alias);
				} else {
					throw new BuildException("Found multiple alias values for the same property: " + propertyAliases);
				}
			}
		}
		return value;
	}
	
	/**
	 * Shortcut to quickly get an Object representation of a property value
	 * @param property A list of property aliases
	 * @throws Thrown if the same aliased property is defined multiple times
	 * @return String property value (null if not found)
	 */
	public Object getProperty(String...propertyAliases) throws BuildException {
		Object value = null;
		for(String alias : propertyAliases) {
			if (getProperties().containsKey(alias)) {
				//if the same property's aliases are defined multiple times, we need to throw exception
				if (value == null) {
					value = getProperties().get(alias);
				} else {
					throw new BuildException("Found multiple alias values for the same property: " + propertyAliases);
				}
			}
		}
		return value;
	}
	
	/**
	 * Returns if the list of created objects has at least one instance of the
	 * requested class (or its subclasses)
	 * @param type Class type
	 * @return true if found, false if not
	 */
	public boolean containsType(Class<?> type) {
		boolean contains = false;
		for(Node child : getChildNodes()) {
			if (child.getMainObject() != null &&
					type.isAssignableFrom(child.getMainObject().getClass())) {
				contains = true;
				break;
			}
		}
		return contains;
	}
	
	/**
	 * Return the key that this node corresponds to
	 * @return Key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return The list of keys/properties of the node that have been consumed by the various property handlers up till now
	 */
	public Set<String> getConsumedKeys() {
		return consumedKeys;
	}
	
	/**
	 * For internal Builder use (when processing collection nodes)
	 * @param consumedKeys
	 */
	void setConsumedKeys(Set<String> consumedKeys) {
		this.consumedKeys = consumedKeys;
	}

	/**
	 * If true, the node should use the preinstantiated root object instead
	 * of creating a new instance
	 * @return the usePreInstantiatedRoot
	 */
	public boolean isUsePreInstantiatedRoot() {
		return usePreInstantiatedRoot;
	}

	/**
	 * Sets a flag telling the node to use the preinstantiated root from
	 * the BuildResult instead of creating a new instance
	 * @param usePreInstantiatedRoot the usePreInstantiatedRoot to set
	 */
	public void setUsePreInstantiatedRoot(boolean usePreInstantiatedRoot) {
		this.usePreInstantiatedRoot = usePreInstantiatedRoot;
	}

	/**
	 * @return Domain-specific custom properties that can be set on a node
	 */
	public Map<String, Object> getCustomProperties() {
		return customProperties;
	}
	
	/**
	 * @param key Key
	 * @return Custom property value
	 */
	public Object getCustomProperty(String key) {
		return customProperties.get(key);
	}

	
	/**
	 * Checks if a custom property is equal to a particular value
	 * @param key Key
	 * @param value Value to compare
	 * @return True if equal, false if not
	 */
	public boolean isCustomPropertyEqualTo(String key,Object value) {
		Object pValue =customProperties.get(key);
		if (pValue == null) {
			return false;
		} else {
			return pValue.equals(value);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return MessageFormat.format("{0} node: {1}",getKey(), properties); 
	}
}
