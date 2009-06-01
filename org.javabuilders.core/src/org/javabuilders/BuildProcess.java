/**
 * 
 */
package org.javabuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Logger;

/**
 * The output of a build process
 * @author Jacek Furmankiewicz
 */
public class BuildProcess {
	
	@SuppressWarnings("unused")
	private static final Logger logger = Logger.getLogger(BuildProcess.class.getSimpleName());

	//private Object document = null;
	private BuildResult result;
	
	private Map<String,Object> instances = new HashMap<String, Object>();
	
	private List<NamedObjectPropertyValue> propertiesAsNamedObjects = new ArrayList<NamedObjectPropertyValue>();
	
	private BuilderConfig config = null;
	
	private Object document = null;
	

	/**
	 * Constructor
	 * @param bundles Optional list of arrays
	 * @throws BuildException 
	 */
	public BuildProcess(BuilderConfig config, Object caller, ResourceBundle...bundles) throws BuildException {
		if (caller == null) {
			throw new BuildException("Caller cannot be null");
		}
		if (config == null) {
			throw new BuildException("config cannot be null");
		}
		
		this.config = config;
		result = new BuildResult(config,caller);
		//set up the default validation message handler
		this.getBuildResult().setValidationMessageHandler(config.getValidationMessageHandler());
		try {
			addNamedObject(Builder.THIS, caller);
		} catch (BuildException e) {
			//can never happen
		}
		this.result.getResourceBundles().addAll(Arrays.asList(bundles));
	}
	
	
	/**
	 * Returns an object from the build, as identified by the requested Id.
	 * Logic to map an object to id is builder-specific.
	 * @param name Id
	 * @return Mapped object or null if none found
	 */
	public Object getByName(String name) {
		return result.get(name);
	}
	
	/**
	 * @return The result of the build. The object that provides the link between the Java and YAML domain
	 */
	public BuildResult getBuildResult() {
		return result;
	}
	
	
	/**
	 * Adds an object identified by an id to the list of objects that can
	 * be retrieved
	 * @param name Object name
	 * @param object Object
	 */
	void addNamedObject(String name, Object object) throws BuildException {
		if (Builder.THIS.equals(name) && result.containsKey(Builder.THIS)) {
			throw new BuildException("A named object canot be called 'this', as it is a reserved keyword");
		} else {
			if (name != null) {
				try {
					result.put(name, object);
				} catch(BuildException ex) {
					//provide extra info as to which file causes the error (Isse #20)
					throw new BuildException(ex, "{0} : {1}", this.getCaller(), ex.getMessage());
				}
			}
		}
	}
	
	/**
	 * Gets the caller that initiated the build
	 * @return Caller or null
	 */
	public Object getCaller() {
		return getBuildResult().getCaller();
	}
	
	/**
	 * @return the resourceBundles
	 */
	public Set<ResourceBundle> getResourceBundles() {
		return result.getResourceBundles();
	}

	/**
	 * @return The instances (i.e. objects that were already instantiated before the build. Mapped by name to the ones in the YAML file)
	 */
	public Map<String, Object> getInstances() {
		//TODO: use these...in the future...
		return instances;
	}


	/**
	 * @return Internal collection of objects whose properties need to be set to references
	 * to named objects
	 */
	public List<NamedObjectPropertyValue> getPropertiesAsNamedObjects() {
		return propertiesAsNamedObjects;
	}


	/**
	 * @return Current builder config
	 */
	public BuilderConfig getConfig() {
		return config;
	}


	/**
	 * @return the fully parsed and pre-processed YAML document
	 */
	public Object getDocument() {
		return document;
	}


	/**
	 * @param document the fully parsed and pre-processed YAML document
	 */
	public void setDocument(Object document) {
		this.document = document;
	}
	

}
