/**
 * 
 */
package org.javabuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Defines a list of items that can be passed to a property
 * @author Jacek Furmankiewicz
 *
 */
public class ValueListDefinition {

	/**
	 * @param relatedTypes Related classes (used mostly for event handlers)
	 * @return Common event definitions
	 */
	public static List<ValueListDefinition> getCommonEventDefinitions(Class<?>...relatedTypes) {
		List<ValueListDefinition> eventDefs = new ArrayList<ValueListDefinition>();
		
		//defines the standard event listener definitions
		ValueListDefinition vlDef = new ValueListDefinition();
		ValueDefinition def = new ValueDefinition("methodName",EventMethod.class);
		def.setVariableLength(true);
		def.setRelatedTypes(relatedTypes);
		vlDef.addValueDefinition(def);
		eventDefs.add(vlDef);
		
		return eventDefs;
	}
		
	private List<ValueDefinition> valueDefs = new LinkedList<ValueDefinition>();
	
	/**
	 * Constructor for a list that has unlimited elements of one type
	 * @param type Value definitions
	 */
	public ValueListDefinition(ValueDefinition...valueDefs) {
	
		this.valueDefs = new LinkedList<ValueDefinition>(Arrays.asList(valueDefs));
		validateValueDefinitions();
	}
	
	/**
	 * @param valueDef Value definition
	 * @return Same instance, for use in Builder pattern
	 */
	public ValueListDefinition addValueDefinition(ValueDefinition valueDef) {
		valueDefs.add(0,valueDef);
		validateValueDefinitions();
		return this;
	}
	
	//validates if the value defs are valid
	private void validateValueDefinitions() {
		boolean noMoreAllowed = false;
	 	
		//only the last value may be of variable length
		for(ValueDefinition def : valueDefs) {
	 		if (noMoreAllowed) {
	 			throw new BuildException("Only the last value definition may have variable length");
	 		} else if (def.isVariableLength()) {
	 			noMoreAllowed = true;
	 		}
	 	}
	}
	
	/**
	 * Checks if the values are a potential match
	 * @param values Values 
	 * @return true/false
	 */
	public boolean isPotentialMatch(List<Object> values) {
		boolean potential = false;
		
		for(@SuppressWarnings("unused")
		Object value : values) {
			//TODO
		}
		
		return potential;
	}
	
	/**
	 * Applies the defaults to a list of values if they are not defined (null/empty) or missing
	 * @param values Values
	 */
	public void applyDefaults(List<Object> values) {
		int i = 0;
		for(ValueDefinition valueDef : valueDefs) {
			if (values.size() > i) {
				//override a missing value with the default
				Object value = values.get(i);
				if (value == null || (String.valueOf(value).length() == 0)) {
					//apply default
					values.set(i, valueDef.getDefaultValue());
				}
			} else {
				//add the default to the list of values
				values.add(valueDef.getDefaultValue());
			}
			i++;
		}
	}
	
	/**
	 * @param values
	 * @return true/false
	 */
	public boolean isExactMatch(List<Object> values) {
		boolean exact = true;
		
		if (valueDefs.size() <= values.size()) {
			
			ValueDefinition variableDef = null;
			for(int i = 0; i < values.size(); i++) {
				
				//handle variable length value definitions
				Class<?> def = null;
				if (variableDef != null) {
					def = variableDef.getType();;
				} else {
					ValueDefinition valueDef = valueDefs.get(i);
					def = valueDef.getType();
					if (valueDef.isVariableLength()) {
						variableDef = valueDef; 
					}
				}
				
				Class<?> value = values.get(i).getClass();
				
				//do the data types match up?
				if (def.equals(NamedObject.class)) {
					if(!value.equals(String.class)) {
						//object names are always strings
						exact = false;
						break;
					}
				} else if (def.equals(EventMethod.class)) {
					if (!value.equals(String.class)) {
						//method names are always Strings
						exact = false;
						break;
					}
				} else if (!def.isAssignableFrom(value)){
					//data type must be compatible
					exact = false;
					break;
				}
			}
		} else {
			exact = false;
		}
		return exact;
	}
	
	/**
	 * Validates the values that were passed. Should be called only
	 * after isExactMatch() was successful
	 * @param values Values to validate
	 * @param result Build result
	 * @param node Node
	 * @param valueList Value list being built
	 * @throws BuildException Thrown if any exception occurs
	 */
	@SuppressWarnings("unchecked")
	public Values validateValues(List<Object> values, BuildProcess result, Node node,
			Values valueList) throws BuildException {
		
		Values validations = new Values(this);
		
		ValueDefinition variableDef = null;
		for(int i = 0; i < values.size();i++) {
			ValueDefinition def = null;
			if (variableDef != null) {
				def = variableDef;
			} else {
				def = valueDefs.get(i);
				if (def.isVariableLength()) {
					variableDef = def; 
				}
			}
			
			Object value = values.get(i);
			def.validateValue(result, node, value, valueList);
		}
		
		return validations;
	}

}
