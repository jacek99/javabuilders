package org.javabuilders;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a collection of valid property combinations
 * @author Jacek Furmankiewicz
 */
public class PropertyCombination {

	private List<String[]> combinations = new ArrayList<String[]>();

	/**
	 * @param properties List of valid property combinations
	 */
	public void add(String...properties) {
		combinations.add(properties);
	}
	
	/**
	 * Checks if a list of property names is valid as per the
	 * list of valid combinations
	 * @param properties Properties
	 * @return true or false
	 */
	public boolean isValid(Collection<String> properties) {
		boolean isValid = false;
		
		
		for(String[] allowed : combinations) {
			
			boolean found = false;
			boolean notFound = false;
			for(String property : allowed) {
				if (properties.contains(property)) {
					found = true;
				} else {
					notFound = true;
				}
			}
			
			if (found && !notFound) {
				isValid = true;
				break;
			}
		}
		
		return isValid;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(combinations.size() * 20);
		for(String[] properties : combinations) {
			if (builder.length() > 0) {
				builder.append(" | ");	
			}
			for(String property : properties) {
				builder.append(property).append(" ");
			}
		}
		return builder.toString();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return combinations.hashCode();
	}
}
