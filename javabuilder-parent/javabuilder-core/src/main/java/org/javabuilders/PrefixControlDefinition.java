package org.javabuilders;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.javabuilders.util.JBStringUtils;

/**
 * Encapsulates all the definitions for controls
 * auto-generated via naming conventions, i.e. prefixes
 */
public class PrefixControlDefinition {
	
	/**
	 * Label from suffix, e.g. "btnSaveData" -> "Save Data"
	 * Integrates with resource key lookup
	 */
	public static final String SUFFIX_LABEL = "${CONTROL_SUFFIX_LABEL}";
	/**
	 * Suffix in Pascal case, e.g. "btnSaveData" -> "saveData"
	 */
	public static final String SUFFIX_PASCAL_CASE = "${CONTROL_SUFFIX_PASCAL_CASE}";
	
	private Class<?> clazz;
	private Map<String,String> defaults = new HashMap<String, String>();
	
	/**
	 * Gets the actual defaults
	 * @param controlName Control name, e.g. "btnSaveData"
	 * @param suffix The already parsed control suffix, e.g. "SaveData"
	 */
	public String getDefaultsAsYaml(BuildProcess process, String controlName, String suffix) {
		StringBuilder bld = new StringBuilder("{");
		
		if (defaults != null && defaults.size() > 0) {
			String suffixPascal = (suffix.length() > 0) ? suffix.substring(0,1).toLowerCase() + suffix.substring(1) : suffix;
			for(Entry<String,String> entry : defaults.entrySet()) {
	
				if (bld.length() > 1) {
					bld.append(",");
				}
				
				String value = entry.getValue();
				if (value.contains(SUFFIX_PASCAL_CASE)) {
					value = value.replace(SUFFIX_PASCAL_CASE, suffixPascal);
				} else if (value.equals(SUFFIX_LABEL)) {
					value = JBStringUtils.getDisplayLabel(process, clazz, suffixPascal);
				}
				bld.append(entry.getKey()).append(" : ").append(value);
			}
		}
		
		//add default entry for name, if not defined
		if (defaults == null || !defaults.containsKey(Builder.NAME)) {
			if (bld.length() > 1) {
				bld.append(",");
			}
			bld.append("name : ").append(controlName);
		}
		
		bld.append("}");
		
		return bld.toString();
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getType() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setType(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * @return the defaults
	 */
	public Map<String, String> getDefaults() {
		return defaults;
	}

	/**
	 * @param defaults the defaults to set
	 */
	public void setDefaults(Map<String, String> defaults) {
		this.defaults = defaults;
	}
}
