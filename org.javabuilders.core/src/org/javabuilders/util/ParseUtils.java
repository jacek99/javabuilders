package org.javabuilders.util;

import java.util.Map;

/**
 * Parsing utilities
 * @author Jacek Furmankiewicz
 *
 */
public class ParseUtils {

	
	/**
	 * Parses the type of nested expressions used in databinding for tables, e.g.
	 * this.people(firstName,lastName=("Last Name"),birthDate=("Birth Date",datetimepicker)))
	 * @param expression
	 * @return
	 */
	public static Map<String,String> parse(String expression) {
		return null;
	}
	
}
