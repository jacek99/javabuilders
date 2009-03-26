package org.javabuilders;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.javabuilders.handler.validation.BuilderValidators;
import org.javabuilders.handler.validation.IValidationMessageHandler;
import org.javabuilders.handler.validation.ValidationMessageList;

/**
 * The return of any build process. That's what both the YAML and Java side see and
 * can communicate with
 * @author Jacek Furmankiewicz
 *
 */
@SuppressWarnings("serial")
public class BuildResult extends HashMap<String, Object> {

	private static final Logger logger = Logger.getLogger(BuildResult.class.getSimpleName());
	
	private BuilderConfig config;
	private Object caller = null;
	
	private List<Object> roots = new ArrayList<Object>();
	private boolean isDirty = false;
	private BuilderValidators validators = new BuilderValidators(this);
	private PropertyChangeSupport support = new PropertyChangeSupport(this);
	private IValidationMessageHandler validationMessageHandler;
	
	private Set<ResourceBundle> resourceBundles = new HashSet<ResourceBundle>();
	private Map<String, Field> allFields = null;
	
	private Object bindingContext = null;
	private Map<String,?> properties = new HashMap<String, Object>();
	
	/**
	 * @param config Config
	 */
	BuildResult(BuilderConfig config, Object caller) {
		this.config = config;
		this.caller = caller;
		allFields = BuilderUtils.getAllFields(caller.getClass());
	}
	
	
	/* (non-Javadoc)
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object value) {
		//Additional validation to ensure duplicate names are not allowed (Issue #20)
		if (key == null) {
			throw new BuildException("Null keys are not allowed in BuildResult: {0}", value);
		} else if (keySet().contains(key)) {
			throw new BuildException("Duplicate name \"{0}\" specified in YAML file", key);
		} else {
			return super.put(key, value);
		}
	}
	
	/* (non-Javadoc)
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public Object get(Object key) {
		Object value = super.get(key);
		
		if (value == null && allFields.containsKey(key)) {
			//named object not created during build..look for a field or property with the same name
			Field field = allFields.get(key);
			try {
				value = field.get(caller);
			} catch (Exception e) {
				logger.severe(String.format("Unable to access field %s: %s", key, e.getMessage()));
			}
		}
		
		return value;
	}
	
	/**
	 * JavaBean support
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		support.addPropertyChangeListener(listener);
	}

	/**
	 * JavaBean support
	 * @param propertyName
	 * @param listener
	 */
	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.addPropertyChangeListener(propertyName,listener);
	}

	/**
	 * Flag used to track if any changes have been made to properties flagged for tracking
	 * @return the isDirty flag
	 */
	public boolean isDirty() {  
		return isDirty;
	}

	/**
	 * JavaBean support
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		support.removePropertyChangeListener(listener);
	}

	/**
	 * JavaBean support
	 * @param propertyName
	 * @param listener
	 */
	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		support.removePropertyChangeListener(propertyName,listener);
	}

	/**
	 * Used to reset the dirty flag
	 * @param isDirty the isDirty flag to set
	 */
	public void setDirty(boolean isDirty) {
		boolean oldValue = this.isDirty;
		this.isDirty = isDirty;
		support.firePropertyChange("dirty", oldValue, isDirty);
	}

	/**
	 * Syncs the marked variables between the YAML and Java side.
	 * Can be called mnaually at any time, builder calls it when required
	 * @throws BuildException
	 */
	public void sync() throws BuildException {
		//TODO: sync variables across both domains
	}

	/**
	 * Validates the YAML/Java values, depending on how validations where defined in the
	 * YAML domain
	 *  @return true if valid, false if not
	 */
	public boolean validate()  {
		return this.validate(true);
	}
	
	/**
	 * Validates the YAML/Java values, depending on how validations where defined in the
	 * YAML domain
	 * @param handleValidationMessages Flags if supposed to show validation messages or not
	 * @return true if valid, false if not
	 */
	public boolean validate(boolean handleValidationMessages)  {
		ValidationMessageList list = getValidationMessages();
		if (list.size() > 0) {
			if (handleValidationMessages) {
				validationMessageHandler.handleValidationMessages(list, this);
			}
			return false;
		} else {
			//all fine, no input
			return true;
		}
	}
	
	/**
	 * @return List of validation messages. If size == 0, means no validation issues exist
	 */
	public ValidationMessageList getValidationMessages() {
		return getValidators().getValidationMessages(validationMessageHandler);
	}

	/**
	 * @return The property validators defined in the YAML file
	 */
	public BuilderValidators getValidators() {
		return validators;
	}

	/**
	 * @return the validation message handler
	 */
	public IValidationMessageHandler getValidationMessageHandler() {
		return validationMessageHandler;
	}

	/**
	 * @param validationMessageHandler the validation message handler
	 */
	public void setValidationMessageHandler(
			IValidationMessageHandler validationMessageHandler) {
		this.validationMessageHandler = validationMessageHandler;
	}
	
	/**
	 * return Resource bundles
	 */
	public Set<ResourceBundle> getResourceBundles() {
		
		return this.resourceBundles;
	}
	
	/**
	 * @param bundles Resource bundles
	 */
	void setResourceBundles(Set<ResourceBundle> bundles) {
		this.resourceBundles = bundles;
	}
	
	/**
	 * @param key
	 * @return
	 */
	public String getResource(String key) {
		String resource = null;
		
		//look in the process bundles first
		for(ResourceBundle bundle : getResourceBundles()) {
			for(Enumeration<String> en = bundle.getKeys(); en.hasMoreElements();) {
				String bundleKey = en.nextElement();
				if (key.equals(bundleKey)) {
					resource = bundle.getString(key);
					break;
				}
			}
		}
		
		//look in the global bundles next
		if (resource == null) {
			for(ResourceBundle bundle : config.getResourceBundles()) {
				for(Enumeration<String> en = bundle.getKeys(); en.hasMoreElements();) {
					String bundleKey = en.nextElement();
					if (key.equals(bundleKey)) {
						resource = bundle.getString(key);
						break;
					}
				}
			}
		}

		//first, look in base resource  bundle last
		if (resource == null) {
			try {
				resource = Builder.getResourceBundle().getString(key);
			} catch (Exception ex) {}
		}

		
		//fallback strategy - different depending if internationalization is active or not
		if (resource == null) {
			if (getResourceBundles().size() > 0) {
				//unable to find key - pass the key as the value
				resource = getInvalidResource(key);
			} else {
				resource = key;
			}
		}
		
		return resource;
	}
	
	/**
	 * @param key Key
	 * @return Key formatted as as an invalid (i.e. not internationalized) value
	 */
	public String getInvalidResource(String key) {
		String resource = config.isMarkInvalidResourceBundleKeys() ? String.format("#%s#",key) : key;

		if (logger.isLoggable(Level.INFO)) {
			logger.info("Unable to find value in any resource bundle for key: " + key);
		}

		return resource;
	}

	/**
	 * @return the caller that initiated the build
	 */
	public Object getCaller() {
		return caller;
	}
	
	/**
	 * @return the caller that initiated the build
	 */
	public BuilderConfig getConfig() {
		return config;
	}
	
	/**
	 * Gets the first root object, if defined
	 * @return Root object
	 * @throws MultipleRootsException 
	 */
	public Object getRoot()   {
		if (roots.size() > 0) {
			return roots.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the root objects
	 * @return Root objects
	 */
	public List<Object> getRoots()  {
		return roots;
	}
	
	/**
	 * @return Domain-specific binding contenxt
	 */
	public Object getBindingContext() {
		return bindingContext;
	}

	/**
	 * @param bindingContext Domain specific binding context
	 */
	public void setBindingContext(Object bindingContext) {
		this.bindingContext = bindingContext;
	}

	/**
	 * @return Build-specific custom properties
	 */
	@SuppressWarnings("unchecked")
	public Map getProperties() {
		return properties;
	}
	
	/**
	 * Flag that indicates if the build process has internationalization logic activates
	 * @return
	 */
	public boolean isInternationalizationActive() {
		return getConfig().getResourceBundles().size() > 0 || getResourceBundles().size() > 0;
	}


}
